package com.android.socialalert;

import java.util.ArrayList;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.android.socialalert.common.AlertMessage;
import com.android.socialalert.logger.PMWF_Log;
import com.android.socialalert.settings.ApplicationSettings;

public class AlertPopUpDialog extends SherlockActivity {
    JSONObject uniObject;
    int notifyId;
    boolean checkAlert = false;
    Button accept, reject;
    static ArrayList<AlertInfo> alertReceivedList;
    ArrayList<AlertInfo> alertList;
    AlertInfoAdapter adapter;
    String alertType;

    private void initActionBar(String titlebarText) {

        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.custom_titlebar, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.titlebarText);
        mTitleTextView.setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_receivedAlerts",
                "Received Alert"));
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActionBar("");
        try {
            Intent alertReceived = getIntent();
            // String receivedAlerts =
            // alertReceived.getStringExtra("ReceivedJson");
            // ArrayList<AlertInfo> alertList = new ArrayList<AlertInfo>();
            alertList = getReceivedList(alertReceived);

            if (alertReceivedList == null || alertReceivedList.size() <= 0) {
                alertReceivedList = alertList;
            } else {
                alertReceivedList.addAll(alertList);

            }
            /*
             * try { // uniObject = new JSONObject(receivedAlerts); } catch
             * (Exception e) { // TODO Auto-generated catch block
             * e.printStackTrace(); }
             */
            notifyId = alertReceived.getIntExtra("NotifyId", 0);
            if (!checkAlert && alertReceivedList.size() == 1) {
                checkAlert = true;

                AlertInfo notifyAlert = alertReceivedList.get(0);
                notifyId = notifyAlert.getNotificationId();
                int alertId = notifyAlert.getAlertType();
                alertType = ApplicationSettings.dbAccessor.getAlertDescription(alertId);
                String userID = notifyAlert.getFromUserGuid();

                int fromUserId = ApplicationSettings.dbAccessor.getId(userID);
                ArrayList<String> alertDetails = ApplicationSettings.dbAccessor.getUserName(fromUserId + "");

                AlertShow builder = new AlertShow(this);
                builder.setTitle(AlertMessage.setAlertHeaderColor(
                        alertType
                                + " "
                                + " "
                                + ApplicationSettings.translationSettings.GetTranslation("and_lbl_alertReceived",
                                        "Alert Received"), getApplicationContext()));
                // builder.setTitle("Received Alert");
                builder.setMessage(alertDetails.size() > 0 ? alertDetails.get(2) + "(" + alertDetails.get(0) + " "
                        + alertDetails.get(1) + ")" : "");
                builder.setCancelable(false);
                builder.setPositiveButton(
                        ApplicationSettings.translationSettings.GetTranslation("and_lbl_accept", "Accept"),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // Do something
                                try {
                                    ApplicationSettings.dbAccessor.UpdateReceiverStatus(alertReceivedList, "Accepted");
                                    CancelNotification(getApplicationContext(), notifyId);
                                    // AlertService.alertReceivedList.clear();
                                    alertReceivedList.clear();
                                    alertList.clear();
                                    checkAlert = false;
                                    dialog.dismiss();

                                    Intent received = new Intent(getApplicationContext(), HomePageActivity.class);
                                    received.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// |
                                                                                      // Intent.FLAG_ACTIVITY_SINGLE_TOP
                                    received.putExtra("alertReceived", true);
                                    startActivity(received);
                                } catch (Exception e) {
                                    PMWF_Log.fnlog(PMWF_Log.ERROR, "AlertPopUpDialog: Accept single alert()",
                                            PMWF_Log.getStringFromStackTrace(e));
                                }
                                finish();
                            }
                        });
                builder.setNegativeButton(
                        ApplicationSettings.translationSettings.GetTranslation("and_lbl_reject", "Reject"),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton) {
                                try {
                                    ApplicationSettings.dbAccessor.UpdateReceiverStatus(alertReceivedList, "Rejected");
                                    CancelNotification(getApplicationContext(), notifyId);
                                    // AlertService.alertReceivedList.clear();
                                    alertReceivedList.clear();
                                    alertList.clear();
                                    checkAlert = false;
                                    dialog.dismiss();

                                    Intent received = new Intent(getApplicationContext(), HomePageActivity.class);
                                    received.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// Intent.FLAG_ACTIVITY_SINGLE_TOP
                                                                                      // |
                                    received.putExtra("alertReceived", true);
                                    startActivity(received);
                                } catch (Exception e) {
                                    PMWF_Log.fnlog(PMWF_Log.ERROR, "AlertPopUpDialog: Reject single alert()",
                                            PMWF_Log.getStringFromStackTrace(e));
                                }
                                finish();
                            }
                        });
                builder.show();
                /*
                 * AlertDialog alert = builder.
                 * builder.getWindow().setType(WindowManager
                 * .LayoutParams.TYPE_SYSTEM_ALERT); alert.show();
                 */
            } else if (alertReceivedList.size() > 1) {

                try {

                    final Dialog dialogg = new Dialog(this);
                    dialogg.setContentView(R.layout.multiple_alert_received);
                    ListView lv = (ListView) dialogg.findViewById(R.id.list);
                    accept = (Button) dialogg.findViewById(R.id.btnAccept);
                    accept.setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_accept", "Accept"));
                    reject = (Button) dialogg.findViewById(R.id.btnReject);
                    reject.setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_reject", "Reject"));
                    dialogg.setCancelable(false);
                    dialogg.setTitle(AlertMessage.setAlertHeaderColor(ApplicationSettings.translationSettings
                            .GetTranslation("and_lbl_receivedAlerts", "Received Alert"), getApplicationContext()));
                    dialogg.show();

                    adapter = new AlertInfoAdapter(getApplicationContext(), alertReceivedList);
                    lv.setAdapter(adapter);

                    accept.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            try {
                                for (AlertInfo eachAlertInfo : alertReceivedList) {
                                    ArrayList<AlertInfo> alertInfo = new ArrayList<AlertInfo>();
                                    alertInfo.add(eachAlertInfo);

                                    if (eachAlertInfo.isAlertcheck()) {
                                        ApplicationSettings.dbAccessor.UpdateReceiverStatus(alertInfo, "Accepted");
                                    } else {

                                        ApplicationSettings.dbAccessor.UpdateReceiverStatus(alertInfo, "Rejected");
                                    }
                                    CancelNotification(getApplicationContext(), eachAlertInfo.getNotificationId());
                                }
                                // AlertService.alertReceivedList.clear();
                                alertReceivedList.clear();
                                alertList.clear();

                                Intent received = new Intent(getApplicationContext(), HomePageActivity.class);
                                received.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// |
                                                                                  // Intent.FLAG_ACTIVITY_SINGLE_TOP
                                received.putExtra("alertReceived", true);
                                startActivity(received);
                            } catch (Exception ex) {
                                PMWF_Log.fnlog(PMWF_Log.ERROR, "AlertPopUpDialog: Multiple alert(0)",
                                        PMWF_Log.getStringFromStackTrace(ex));
                            }
                            finish();
                            dialogg.dismiss();
                        }
                    });

                    reject.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            try {

                                for (AlertInfo eachAlertInfo : alertReceivedList) {
                                    ArrayList<AlertInfo> insertAlert = new ArrayList<AlertInfo>();
                                    insertAlert.add(eachAlertInfo);
                                    if (eachAlertInfo.isAlertcheck()) {
                                        ApplicationSettings.dbAccessor.UpdateReceiverStatus(insertAlert, "Rejected");
                                    } else {
                                        ApplicationSettings.dbAccessor.UpdateReceiverStatus(insertAlert, "Accepted");
                                    }
                                    CancelNotification(getApplicationContext(), eachAlertInfo.getNotificationId());
                                }
                                // AlertService.alertReceivedList.clear();
                                alertReceivedList.clear();
                                alertList.clear();

                                Intent received = new Intent(getApplicationContext(), HomePageActivity.class);
                                received.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// |
                                                                                  // Intent.FLAG_ACTIVITY_SINGLE_TOP
                                received.putExtra("alertReceived", true);
                                startActivity(received);
                            } catch (Exception e) {
                                PMWF_Log.fnlog(PMWF_Log.ERROR, "AlertPopUpDialog: Multiple alert(1)",
                                        PMWF_Log.getStringFromStackTrace(e));
                            }
                            finish();
                            dialogg.dismiss();
                        }
                    });
                } catch (Exception e) {
                    PMWF_Log.fnlog(PMWF_Log.ERROR, "AlertPopUpDialog: Multiple alert",
                            PMWF_Log.getStringFromStackTrace(e));
                    finish();
                }
            }

        } catch (Exception e) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "AlertPopUpDialog: onCreate()", PMWF_Log.getStringFromStackTrace(e));
            finish();
        }
    }

    private ArrayList<AlertInfo> getReceivedList(Intent alertReceived) {
        return (ArrayList<AlertInfo>) alertReceived.getSerializableExtra("ReceivedJson");
    }

    public void CancelNotification(Context ctx, int notifyId) {
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager nMgr = (NotificationManager) ctx.getSystemService(ns);
        nMgr.cancel(notifyId);
    }

}
