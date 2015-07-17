package com.android.socialalert;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockDialogFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.android.socialalert.logger.PMWF_Log;
import com.android.socialalert.map.GPS_Tracker;
import com.android.socialalert.service.AlertService;
import com.android.socialalert.service.Shake_Listener;
import com.android.socialalert.settings.ApplicationSettings;
import com.android.socialalert.utilities.HideKeyboard;

public class HomePageActivity extends SherlockFragmentActivity {

    private static final int INTENT_AELRT = 1002;
    // private static final int INTENT_GPS_SETTING = 1003;
    public static final int INTENT_ALERT_SENT = 1004;
    public static final int INTENT_ALERT_RECEIVED = 1005;
    private boolean showReceivedAlertFragment = false;

    public static boolean isActivityShowing = true;

    // Declare Variables
    DrawerLayout mDrawerLayout;
    public static ListView mDrawerList;
    ActionBarDrawerToggle mDrawerToggle;
    public static MenuListAdapter mMenuAdapter;
    String[] title;
    public static String[] subtitle;
    public static int[] icon;

    Fragment fragmentAlertButton = new FragmentMainAlert();
    Fragment fragmentSendAlertList = new FragmentSendAlertList();
    Fragment fragmentRecievedAlertList = new FragmentRecievedAlertList();
    Fragment fragmentSettings = new FragmentSettings();
    Fragment fragmentContactList = new ContactListActivity();
    // Fragment fragmentPasswordChange = new PasswordChangeActivity();
    // SherlockDialogFragment fragmentSelectLanguage = new
    // FragmentSelectLanguage();
    SherlockDialogFragment fragmentLogout = new Logout();

    private CharSequence mDrawerTitle;

    // private String mAuthTokenType;
    public static HomePageActivity context;

    public static Intent serviceIntent = null;

    public static CharSequence titlelbl;
    TextView sherlockTitle;
    // ImageView imgRefresh;
    public boolean checkNoContact = true;

    // ImageView imgShowMenu;

    private Toast toast;
    private long lastBackPressTime = 0;

    public static ArrayList<String> titleMenu;

    boolean isGPSAvailable = false;
    private boolean isShakeAlertSend = false;
    private boolean isShakeEvent = false;

    private void initActionBar(String titlebarText) {

        try {
            ActionBar mActionBar = getSupportActionBar();
            mActionBar.setDisplayShowHomeEnabled(false);
            mActionBar.setDisplayShowTitleEnabled(false);
            LayoutInflater mInflater = LayoutInflater.from(this);
            View mCustomView = mInflater.inflate(R.layout.custom_titlebar, null);
            TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.titlebarText);
            mTitleTextView.setText(titlebarText);
            mActionBar.setCustomView(mCustomView);
            mActionBar.setDisplayShowCustomEnabled(true);

            HideKeyboard.setupUI(mCustomView, HomePageActivity.this);
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "HomePageActivity: initActionBar()", PMWF_Log.getStringFromStackTrace(ex));
            finish();
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            // Get the view from drawer_main.xml
            setContentView(R.layout.drawer_main);

            initActionBar("");

            ImageView imgClientLogo = (ImageView) findViewById(R.id.title_clientlogo);
            imgClientLogo.setVisibility(View.VISIBLE);

            final ImageView titleIconExtra = (ImageView) findViewById(R.id.title_addcontact);
            titleIconExtra.setVisibility(View.GONE);

            // Calling GPS_Tracker for location updates
            GPS_Tracker positionHolder = new GPS_Tracker(HomePageActivity.this);
            // positionHolder.getGPS();
            // try {
            // int countContact =
            // ApplicationSettings.dbAccessor.getAllContactCount();
            // } catch (Exception ex) {
            // PMWF_Log.fnlog(PMWF_Log.ERROR, "SamenSafe()",
            // PMWF_Log.getStringFromStackTrace(ex));
            // }
            context = this;

            // Get the Title
            mDrawerTitle = getTitle();

            // Generate title
            // title = new String[] { "Alert", "Sent alerts", "Received alerts",
            // "Contacts", "Change password", "Logout" };

            titleMenu = ApplicationSettings.translationSettings.GetMenuDrawerList();

            // Generate icon
            icon = new int[] { R.drawable.alert, R.drawable.sent_alert, R.drawable.receive_alert, R.drawable.contacts,
                    R.drawable.settings, R.drawable.logout };

            // Locate DrawerLayout in drawer_main.xml
            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

            // Locate ListView in drawer_main.xml
            mDrawerList = (ListView) findViewById(R.id.listview_drawer);

            // Set a custom shadow that overlays the main content when the
            // drawer
            // opens
            mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

            // Pass string arrays to MenuListAdapter

            mMenuAdapter = new MenuListAdapter(HomePageActivity.this, titleMenu, subtitle, icon);
            // Create Actionbar Tabs
            getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
            // Set the MenuListAdapter to the ListView
            mDrawerList.setAdapter(mMenuAdapter);

            // Capture listview menu item click
            mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

            // ActionBarDrawerToggle ties together the the proper interactions
            // between the sliding drawer and the action bar app icon
            mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer28x28,
                    R.string.drawer_open, R.string.drawer_close) {

                public void onDrawerClosed(View view) {
                    // imgRefresh.setEnabled(true);//Sabir check me URGENT
                    titleIconExtra.setEnabled(true);
                    super.onDrawerClosed(view);
                }

                public void onDrawerOpened(View drawerView) {

                    // Set the title on the action when drawer open
                    getSupportActionBar().setTitle(mDrawerTitle);
                    // imgRefresh.setEnabled(false);//Sabir check me URGENT
                    titleIconExtra.setEnabled(false);
                    super.onDrawerOpened(drawerView);
                }
            };

            mDrawerLayout.setDrawerListener(mDrawerToggle);

            if (savedInstanceState == null) {
                selectItem(0);
            }

            serviceIntent = new Intent(getApplicationContext(), AlertService.class);
            startService(serviceIntent);

            /*
             * Intent sendAlert = getIntent(); boolean isSendAlertTrue =
             * sendAlert.getBooleanExtra("SendAlert", false); if
             * (isSendAlertTrue) { FragmentTransaction ft =
             * getSupportFragmentManager().beginTransaction();
             * ft.replace(R.id.content_frame, fragmentSendAlertList).commit(); }
             */

            Intent i = getIntent();
            isShakeEvent = i != null ? i.getBooleanExtra("Shake", false) : false;

            if (i != null && i.hasExtra("alertReceived")) {
                showReceivedAlertFragment = i.getBooleanExtra("alertReceived", false);
            }

            isShakeAlertSend = i != null ? i.getBooleanExtra("ShakeAlertSend", false) : false;

            RelativeLayout imgShowMenu = (RelativeLayout) findViewById(R.id.iconWrapper);

            ImageView drawerIcon = (ImageView) imgShowMenu.findViewById(R.id.toggleMenu);
            drawerIcon.setVisibility(View.VISIBLE);
            imgShowMenu.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
                        mDrawerLayout.closeDrawer(mDrawerList);
                    } else {
                        mDrawerLayout.openDrawer(mDrawerList);
                    }

                }
            });
            /*
             * GPS_Tracker gpsStatus = new GPS_Tracker(HomePageActivity.this);
             * isGPSAvailable = gpsStatus.checkGPSStatus();
             * 
             * if(!isGPSAvailable){ checkGps(); }
             */
        } catch (Exception ex2) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "HomePageActivity: onCreate()", PMWF_Log.getStringFromStackTrace(ex2));
            finish();
        }
    }

    private void handleShakeAlert() {
        if (checkNoContact) {
            int contactCount = ApplicationSettings.dbAccessor.getAllContactCount();
            if (contactCount == 0) {
                isShakeEvent=false;
                checkNoContact = false;
                SpannableString ss1 = new SpannableString(ApplicationSettings.translationSettings.GetTranslation(
                        "and_lbl_alert", "Alert"));
                ss1.setSpan(new RelativeSizeSpan(1f), 0, ss1.length(), 0);
                ss1.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.lauren)), 0, ss1.length(),
                        0);
                AlertShow builder = new AlertShow(context);
                builder.setCancelable(false);
                builder.setTitle(ss1);
                builder.setMessage(ApplicationSettings.translationSettings
                        .GetTranslation(
                                "and_no_contacts",
                                "There are currently no contacts linked to alerttype, please go to the menu at the top left of the screen to add new contacts"));
                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        checkNoContact = true;
                        dialog.dismiss();
                        return;
                    }
                });
                builder.show();
                // return;
            } else {
                isShakeEvent = false;
                Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(2000);
                Intent alertIntent = new Intent(this, AlertActivity.class);
                alertIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                alertIntent.putExtra("ShakeFromOutside", isShakeEvent);
                startActivityForResult(alertIntent, INTENT_ALERT_SENT);
            }

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        try {
            if (item.getItemId() == android.R.id.home) {

                if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
                    mDrawerLayout.closeDrawer(mDrawerList);
                } else {
                    mDrawerLayout.openDrawer(mDrawerList);
                }
            }

            // if (item.getItemId() == R.id.action_refresh) {
            // new ListRefresh().execute();
            // /*
            // * FragmentTransaction fragTransaction =
            // * getSupportFragmentManager().beginTransaction();
            // * fragTransaction.detach(fragment1);
            // * fragTransaction.attach(fragment1); fragTransaction.commit();
            // */
            // }

            return super.onOptionsItemSelected(item);
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "HomePageActivity: onOptionsItemSelected()",
                    PMWF_Log.getStringFromStackTrace(ex));
        }
        return false;
    }

    // ListView click listener in the navigation drawer
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            try {
                selectItem(position);
            } catch (Exception ex) {
                PMWF_Log.fnlog(PMWF_Log.ERROR, "HomePageActivity: DrawerItemClickListener()",
                        PMWF_Log.getStringFromStackTrace(ex));
            }
        }
    }

    private void selectItem(final int position) {
        new Handler().post(new Runnable() {
            public void run() {
                try {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    // Locate Position
                    switch (position) {
                    case 0:
                        ft.replace(R.id.content_frame, fragmentAlertButton, "FRAGMENT_MAIN_ALERT");// fragment1

                        break;
                    case 1:
                        ft.replace(R.id.content_frame, fragmentSendAlertList);
                        break;
                    case 2:
                        ft.replace(R.id.content_frame, fragmentRecievedAlertList);
                        break;
                    case 3:
                        ft.replace(R.id.content_frame, fragmentContactList);
                        break;
                    /*
                     * case 4: ft.replace(R.id.content_frame,
                     * fragmentPasswordChange); break; case 5:
                     * fragmentSelectLanguage.show(getSupportFragmentManager(),
                     * "dialog"); Bundle args = new Bundle();
                     * args.putBoolean("INSIDE_APP", true);
                     * fragmentSelectLanguage.setArguments(args); break;
                     */
                    case 4:
                        ft.replace(R.id.content_frame, fragmentSettings, "FRAGMENT_SETTINGS");

                        break;
                    case 5:
                        fragmentLogout.show(getSupportFragmentManager(), "dialog");
                        break;

                    }
                    ft.commit();
                    mDrawerList.setItemChecked(position, true);

                    // Get the title followed by the position
                    if (titleMenu.get(position).equalsIgnoreCase("Logout")) {

                    } else {
                        setTitle(titleMenu.get(position));
                        titlelbl = titleMenu.get(position);
                    }

                    mDrawerLayout.closeDrawer(mDrawerList);
                } catch (Exception ex) {
                    PMWF_Log.fnlog(PMWF_Log.ERROR, "HomePageActivity: selectItem()",
                            PMWF_Log.getStringFromStackTrace(ex));
                }
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        try {
            super.onPostCreate(savedInstanceState);
            // Sync the toggle state after onRestoreInstanceState has occurred.
            mDrawerToggle.syncState();
        } catch (Exception e) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "HomePageActivity onPostCreate()", PMWF_Log.getStringFromStackTrace(e));
        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        try {
            super.onConfigurationChanged(newConfig);
            // Pass any configuration change to the drawer toggles
            mDrawerToggle.onConfigurationChanged(newConfig);
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "HomePageActivity: onConfigurationChanged()",
                    PMWF_Log.getStringFromStackTrace(ex));
        }
    }

    @Override
    public void onBackPressed() {

        try {
            FragmentMainAlert fragmentMainAlert = (FragmentMainAlert) getSupportFragmentManager().findFragmentByTag(
                    "FRAGMENT_MAIN_ALERT");

            if (fragmentMainAlert != null) {

                if (this.lastBackPressTime < System.currentTimeMillis() - 4000) {

                    toast = Toast.makeText(this, ApplicationSettings.translationSettings.GetTranslation(
                            "and_msg_pressBackToExit", "Press back again to exit"), 4000);
                    toast.show();
                    this.lastBackPressTime = System.currentTimeMillis();

                } else {
                    if (toast != null) {
                        toast.cancel();
                        isActivityShowing = false;
                        if (!Shake_Listener.showNoContact) {
                            Shake_Listener.showNoContact = true;
                        }
                    }

                    setResult(RESULT_CANCELED);
                    finish();
                }

            } else {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, new FragmentMainAlert(), "FRAGMENT_MAIN_ALERT");
                ft.commit();
            }
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "HomePageActivity: onBackPressed()", PMWF_Log.getStringFromStackTrace(ex));
        }

    }

    public static HomePageActivity context() {
        return (HomePageActivity) context;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.e("RequestCode", requestCode + "");
        Log.e("ResultCode", resultCode + "");

        try {
            if (requestCode == INTENT_ALERT_SENT) {
                // added to avoid acitivity finish on receiving alert
                if (resultCode == RESULT_OK) {
                    selectItem(1);
                }
            } else if (requestCode == INTENT_AELRT && resultCode == RESULT_OK) {
                setResult(RESULT_OK);
                // finish();
            } else if (resultCode == RESULT_CANCELED) {
                // Want to clear the activity stack so I should just go away now
                setResult(RESULT_OK); // Propagate result to the previous
                                      // activity
                finish();
            }
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "HomePageActivity: onActivityResult()", PMWF_Log.getStringFromStackTrace(ex));
        }
    }

    @Override
    protected void onDestroy() {
        try {
            super.onDestroy();

            // android.os.Process.killProcess(android.os.Process.myPid());
            // System.exit(0);
            // context = null;
            isActivityShowing = false;
            if (!Shake_Listener.showNoContact) {
                Shake_Listener.showNoContact = true;
            }
            setResult(RESULT_OK);
            finish();
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "SamenSafe: onDestroy()", PMWF_Log.getStringFromStackTrace(ex));
        }

    }

    /*
     * private class ListRefresh extends AsyncTask<Void, Void, Void> {
     * 
     * ProgressDialog refreshDialog;
     * 
     * @Override protected void onPreExecute() { super.onPreExecute();
     * 
     * refreshDialog = new ProgressDialog(SendAlertListView.this);
     * refreshDialog.setMessage("Refreshing...."); refreshDialog.show(); }
     * 
     * @Override protected Void doInBackground(Void... params) {
     * FragmentTransaction fragTransaction =
     * getSupportFragmentManager().beginTransaction();
     * fragTransaction.detach(fragmentSendAlertList);
     * fragTransaction.attach(fragmentSendAlertList); fragTransaction.commit();
     * return null; }
     * 
     * @Override protected void onPostExecute(Void result) {
     * super.onPostExecute(result); refreshDialog.dismiss(); }
     * 
     * }
     */

    @Override
    protected void onStop() {
        super.onStop();
        isActivityShowing = false;

    }

    @Override
    protected void onRestart() {
        super.onRestart();

        // Toast.makeText(getApplicationContext(),
        // "OnRestart Value ShowNoContact"+Shake_Listener.showNoContact,
        // Toast.LENGTH_SHORT).show();

        isActivityShowing = false;
        if (!Shake_Listener.showNoContact) {
            Shake_Listener.showNoContact = true;
        }
    }

    @Override
    public void onNewIntent(Intent newIntent) {
        super.onNewIntent(newIntent);
        if (newIntent != null) {
            isShakeEvent = newIntent.getBooleanExtra("Shake", false);
            showReceivedAlertFragment = newIntent.getBooleanExtra("alertReceived", false);
            isShakeAlertSend = newIntent.getBooleanExtra("ShakeAlertSend", false);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        isActivityShowing = true;
        // checkNoContact = true;
        if (!Shake_Listener.showNoContact) {
            Shake_Listener.showNoContact = true;
        }
        try {
            if (isShakeEvent) {
                handleShakeAlert();
            }
            // Intent receivedAlert = getIntent();
            if (isShakeAlertSend) {
                selectItem(1);
                return;
            }

            if (showReceivedAlertFragment) {
                showReceivedAlertFragment = false;
                selectItem(2);
                /*
                 * FragmentTransaction ft =
                 * getSupportFragmentManager().beginTransaction();
                 * ft.replace(R.id.content_frame, new
                 * FragmentRecievedAlertList(), "FRAGMENT_MAIN_ALERT");
                 * ft.commit();
                 */
                // FragmentTransaction ft =
                // getSupportFragmentManager().beginTransaction();
                // ft.replace(R.id.content_frame,
                // fragmentRecievedAlertList).commit();
            }
        } catch (Exception e) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "HomePageActivity: onResume()", PMWF_Log.getStringFromStackTrace(e));
        }
    }

    public void checkGps() {

        try {
            final Dialog dialog = new Dialog(HomePageActivity.this);
            dialog.setContentView(R.layout.gps);

            TextView txtGPSMsg = (TextView) dialog.findViewById(R.id.txtEnableGps);
            txtGPSMsg.setText(ApplicationSettings.translationSettings.GetTranslation("and_msg_gpssettings",
                    "Please enable GPS to use application"));

            /*
             * dialog.setTitle(AlertMessage.setAlertHeaderColor("GPS",
             * SendAlertListView.this));
             */

            Button gpsSettings = (Button) dialog.findViewById(R.id.gps_sett_button);
            gpsSettings.setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_gpssettings",
                    "GPS settings"));
            dialog.setCancelable(false);
            dialog.show();

            gpsSettings.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    try {
                        dialog.dismiss();
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    } catch (Exception e) {
                        PMWF_Log.fnlog(PMWF_Log.ERROR, "HomePageActivity: checkGps()",
                                PMWF_Log.getStringFromStackTrace(e));
                    }
                }
            });

            /*
             * Button gpsCancel = (Button)
             * dialog.findViewById(R.id.gps_cancel_button);
             * gpsCancel.setOnClickListener(new OnClickListener() {
             * 
             * @Override public void onClick(View v) { dialog.dismiss();
             * //finish(); } });
             */
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "HomePageActivity: checkGps(1)", PMWF_Log.getStringFromStackTrace(ex));
        }
    }

}
