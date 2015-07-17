package com.android.socialalert;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

//import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.android.socialalert.common.AlertMessage;
import com.android.socialalert.logger.PMWF_Log;
import com.android.socialalert.map.GPS_Tracker;
import com.android.socialalert.service.Shake_Listener;
import com.android.socialalert.settings.ApplicationSettings;
import com.android.socialalert.settings.ApplicationSettingsFilePath;
import com.android.socialalert.utilities.DateTimeProcessor;

public class AlertActivity extends SherlockFragmentActivity {
    public static boolean isAlertActivityShowing = false;

    ListView alertList;

    SingleListAdapter arrayAdapter;

    public static String finishApp;

    // ArrayAdapter arrayAdapter;
    Button send, cancel;
    TextView timer;
    int count;
    int contactCount;

    Timer t;
    int time = 20;
    TimerTask task;

    String selectedAlertType = null;

    private boolean shouldOpenHomeActivity = false;

    // int countEmg = 0;

    private void initActionBar(String titlebarText) {

        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.custom_titlebar, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.titlebarText);
        mTitleTextView.setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_alert", titlebarText));
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.alert);

            // Shake_Listener.isShake = false;

            // Shake_Listener.alertService.initializeShakeListener();

            initActionBar("Alert");

            Intent receivedIntent = getIntent();
            if (receivedIntent != null) {
                shouldOpenHomeActivity = receivedIntent.getBooleanExtra("ShakeAlert", false);
            }

            isAlertActivityShowing = true;

            alertList = (ListView) findViewById(R.id.list);
            timer = (TextView) findViewById(R.id.timer);

            send = (Button) findViewById(R.id.btnSend);
            send.setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_send", "Send"));

            cancel = (Button) findViewById(R.id.btnCancel);
            cancel.setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_cancel", "Cancel"));

            int fromUserId = ApplicationSettings.dbAccessor.getUserId(ApplicationSettingsFilePath.AccountName);
            // ApplicationSettings.initDBAccessor(getApplicationContext(),ApplicationSettings.APP_DATABASE_PATH);
            // load alert type item
            ArrayList<String> alertType = new ArrayList<String>();
            ArrayList<Integer> alertTypeId = new ArrayList<Integer>();

            alertTypeId = ApplicationSettings.dbAccessor.loadAcceptedAlertItems(fromUserId);
            // add emergency alert type
            // alertType = ApplicationSettings.dbAccessor.loadAlertItems();
            alertType.add(ApplicationSettings.translationSettings.GetTranslation("and_lbl_alertype_emergency",
                    "Emergency"));// for default emergency
            for (Integer alertId : alertTypeId) {
                String alertName = ApplicationSettings.dbAccessor.getAlertDescription(alertId);
                if (alertName != null) {
                    alertType.add(alertName);
                }
            }
            // Collections.sort(alertType.subList(1, alertType.size()));
            arrayAdapter = new SingleListAdapter(this, alertType);

            alertList.setAdapter(arrayAdapter);
            alertList.setItemChecked(0, true);

            startTimer();

            // GPS_Tracker positionHolder = new GPS_Tracker(AlertActivity.this);
            // gps_positions = positionHolder.getGPS();
            final double[] gps_positions = GPS_Tracker.getGPSStatic(AlertActivity.this);
            // positionHolder.stopGPS();

            alertList.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView parent, View view, int position, long itemId) {
                    selectedAlertType = (String) (parent.getItemAtPosition(position));

                }
            });

            send.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    try {

                        stopAlertTimer();
                        String randNum = randIdGenerator(99, 9999999);
                        String randomSentAlertsKey = randNum != null ? randNum : "";

                        if (selectedAlertType == null
                                || selectedAlertType.equalsIgnoreCase(ApplicationSettings.translationSettings
                                        .GetTranslation("and_lbl_alertype_emergency", "Emergency"))) {
                            ArrayList<Integer> connectedUserIds = ApplicationSettings.dbAccessor
                                    .getConnectedContactsForLoginUser();

                            int countEmerg = connectedUserIds != null ? connectedUserIds.size() : 0;
                            if (countEmerg <= 0) {
                                showAlertDialog();
                                return;
                            }
                            String selectedAlertType = (String) (arrayAdapter.getItem(0));

                            ApplicationSettings.dbAccessor.insertAlerts(selectedAlertType, randomSentAlertsKey,
                                    connectedUserIds, "Pending", gps_positions);

                        } else {

                            ArrayList<Integer> connectedUserIds = ApplicationSettings.getContactCount(
                                    ApplicationSettingsFilePath.AccountName, selectedAlertType, new String[] { "", "",
                                            "" });
                            int count = connectedUserIds != null ? connectedUserIds.size() : 0;
                            if (count <= 0) {
                                AlertMessage.AlertAndReturnToMain(ApplicationSettings.translationSettings
                                        .GetTranslation("and_msg_noContactForAlertType",
                                                "Contacts not added for selected alerttype"), AlertActivity.this);
                                return;
                            }
                            ApplicationSettings.dbAccessor.insertAlerts(selectedAlertType, randomSentAlertsKey,
                                    connectedUserIds, "Pending", gps_positions);

                        }
                        // Toast.makeText(getApplicationContext(),
                        // "Alert is sent Successfully.",
                        // Toast.LENGTH_SHORT).show();
                        startActivitySocialAlertList();
                    } catch (Exception ex) {
                        Toast.makeText(
                                AlertActivity.this,
                                ApplicationSettings.translationSettings.GetTranslation("and_msg_errorSendingAlert",
                                        "Problem on sending alert"), Toast.LENGTH_SHORT).show();
                        PMWF_Log.fnlog(PMWF_Log.ERROR, "AlertActivity: send()", PMWF_Log.getStringFromStackTrace(ex));
                    }
                }
            });

            cancel.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    stopAlertTimer();
                    finish();
                    // cancel the alerts
                    //startActivitySocialAlertList();
                }
            });
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "AlertActivity: onCreate()", PMWF_Log.getStringFromStackTrace(ex));
        }
    }

    public void startTimer() {
        t = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        timer.setText(time + "");
                        if (time > 0) {
                            time -= 1;
                        } else {

                            stopAlertTimer();
                            ArrayList<Integer> connectedUserIds = ApplicationSettings.dbAccessor
                                    .getConnectedContactsForLoginUser();

                            int countEmerg = connectedUserIds != null ? connectedUserIds.size() : 0;
                            if (countEmerg <= 0) {
                                showAlertDialog();
                                return;
                            } else {
                                // Toast.makeText(getApplicationContext(),
                                // "Alert is sent Successfully.",
                                // Toast.LENGTH_SHORT).show();
                                double[] gps_positions = GPS_Tracker.getGPSStatic(AlertActivity.this);

                                // send emergency alerts
                                String selectedAlertType = (String) (arrayAdapter.getItem(0));
                                String randomSentAlertsKey = randIdGenerator(99, 9999999);
                                ApplicationSettings.dbAccessor.insertAlerts(selectedAlertType, randomSentAlertsKey,
                                        connectedUserIds, "Pending", gps_positions);
                                startActivitySocialAlertList();
                            }
                        }
                    }
                });
            }
        };
        t.schedule(task, 0, 1000);
    }

    @Override
    public void onBackPressed() {
        // DO nothing on back pressed
        // super.onBackPressed();
        // startActivitySocialAlertList();

    }

    private void launchHomeActivity() {
        Intent intent = new Intent(AlertActivity.this, HomePageActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("ShakeAlertSend", true);
        startActivity(intent);
    }

    public void startActivitySocialAlertList() {
        stopAlertTimer();
        if (shouldOpenHomeActivity) {
            launchHomeActivity();
            finish();
        } else {
            HomePageActivity.isActivityShowing = true;
            super.setResult(RESULT_OK);
            super.finish();
        }

    }

    public void showAlertDialog() {
        String title = ApplicationSettings.translationSettings.GetTranslation("and_lbl_noContact", "No contact");
        SpannableString ss = new SpannableString(title);
        ss.setSpan(new RelativeSizeSpan(1f), 0, ss.length(), 0);
        ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.lightBlue)), 0, ss.length(), 0);
        new AlertShow(AlertActivity.this)
                .setTitle(ss)
                .setMessage(
                        ApplicationSettings.translationSettings
                                .GetTranslation("and_msg_noContactOrNotRegistered",
                                        "You either have no contact added or your contact have not registered with Social Alert."))
                .setPositiveButton("OK",

                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            startActivitySocialAlertList();
                        } catch (Exception ex) {
                            PMWF_Log.fnlog(PMWF_Log.ERROR, "AlertActivity: showAlertDialog()",
                                    PMWF_Log.getStringFromStackTrace(ex));
                        }
                    }
                }).setCancelable(false).show();
    }

    private void stopAlertTimer() {
        try {
            if (t != null) {
                t.cancel();
                t.purge();
                isAlertActivityShowing = false;
            }

            if (task != null) {
                task.cancel();
            }
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "AlertActivity: stopAlertTimer()", PMWF_Log.getStringFromStackTrace(ex));
        }

    }

    /*
     * @Override protected void onActivityResult(int requestCode, int
     * resultCode, Intent data) {
     * 
     * if (resultCode == RESULT_CANCELED) { // Want to clear the activity stack
     * so I should just go away now setResult(RESULT_CANCELED); // Propagate
     * result to the previous activity finish(); } }
     */

    public String randIdGenerator(int min, int max) {

        // Usually this can be a field rather than a method variable
        String RandomId = null;
        try {
            Random rand = new Random();
            // nextInt is normally exclusive of the top value,
            // so add 1 to make it inclusive
            int fromUserId = ApplicationSettings.dbAccessor.getUserId(ApplicationSettingsFilePath.AccountName);
            int randomNum = rand.nextInt((max - min) + 1) + min;
            RandomId = DateTimeProcessor.GetCurrentFormattedDateTime("yyyyMMdd_HHmmss") + "_" + randomNum + "_"
                    + fromUserId;
        } catch (Exception e) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "AlertActivity: randIdGenerator()", PMWF_Log.getStringFromStackTrace(e));

        }
        return RandomId;
    }

}
