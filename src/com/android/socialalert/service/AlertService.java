package com.android.socialalert.service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.android.socialalert.AlertInfo;
import com.android.socialalert.AlertPopUpActivity;
import com.android.socialalert.AlertPopUpDialog;
import com.android.socialalert.AutoUpdateActivity;
import com.android.socialalert.HomePageActivity;
import com.android.socialalert.R;
import com.android.socialalert.json.JSONHelper;
import com.android.socialalert.logger.PMWF_Log;
import com.android.socialalert.settings.ApplicationSettings;
import com.android.socialalert.settings.ApplicationSettingsFilePath;
import com.android.socialalert.settings.WebServiceWrapper;

public class AlertService extends Service {

    public static final int SCREEN_OFF_RECEIVER_DELAY = 500;

    private SensorManager sensorMgr;
    private Sensor sensor, orientation_sensor;

    Shake_Listener shake_event;
    private boolean accelSupported = false;
    private boolean orientSupported = false;

    public static ArrayList<Integer> notificationIds = new ArrayList<Integer>();

    public ArrayList<AlertInfo> alertReceivedList = new ArrayList<AlertInfo>();

    private volatile boolean stop = false;
    Thread pollThread;

    @Override
    public void onCreate() {
        try {
            super.onCreate();

            Log.i("onDestroy()", "Service onCreate...");
            if (!Shake_Listener.showNoContact) {
                Shake_Listener.showNoContact = true;
            }
            initializeShakeListener();
        } catch (Exception e) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "AlertService onCreate()", PMWF_Log.getStringFromStackTrace(e));
        }
    }

    public void initializeShakeListener() {

        try {
            /*
             * try{ sensorMgr.unregisterListener(shake_event); }catch(Exception
             * e){}
             */

            sensorMgr = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            List<Sensor> sensors = sensorMgr.getSensorList(Sensor.TYPE_ACCELEROMETER);
            List<Sensor> orient_sensors = sensorMgr.getSensorList(Sensor.TYPE_ORIENTATION);

            if (sensors.size() > 0 && orient_sensors.size() > 0) {
                sensor = sensors.get(0);
                orientation_sensor = orient_sensors.get(0);
                // shake_event = new Shake_Listener(HomePageActivity.context,
                // this);
                shake_event = new Shake_Listener(AlertService.this);
                accelSupported = sensorMgr.registerListener(shake_event, sensor, SensorManager.SENSOR_DELAY_FASTEST);
                orientSupported = sensorMgr.registerListener(shake_event, orientation_sensor,
                        SensorManager.SENSOR_DELAY_FASTEST);

            }
            if (!accelSupported && !orientSupported) {
                sensorMgr.unregisterListener(shake_event);
            }
            // Handler handler=new Handler();
            new Thread(pingThread).start();

            // handler.postDelayed(r, 6000);
            // ++++++++++++++++++++++++++++
            pollThread = new Thread(null, pollAlerts, "AlertThread");
            pollThread.start();
        } catch (Exception e) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "AlertService initializeShakeListener()",
                    PMWF_Log.getStringFromStackTrace(e));
        }
    }

    Runnable pingThread = new Runnable() {
        public void run() {
            while (true) {
                if (!stop) {
                    try {
                        ApplicationSettings.isWeberviceReachable = WebServiceWrapper.Ping();
                        Thread.sleep(6000);
                    } catch (Exception e) {
                        PMWF_Log.fnlog(PMWF_Log.ERROR, "AlertService .PingThread()", PMWF_Log.getStringFromStackTrace(e));

                    }
                }
            }
        }
    };

    private void GenerateForeGround() {

        try {
            Notification notification = new Notification(R.drawable.ic_launcher, "SamenSafe",
                    System.currentTimeMillis());

            final Intent notificationIntent = new Intent(this, AutoUpdateActivity.class);
            notificationIntent.setAction(Intent.ACTION_MAIN);
            notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            // notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            // notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            if (HomePageActivity.isActivityShowing) {
                notificationIntent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
            }

            PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

            notification.setLatestEventInfo(this, "SamenSafe", "SamenSafe is running.", contentIntent);

            notification.flags |= Notification.FLAG_FOREGROUND_SERVICE;
            startForeground(4711, notification);
        } catch (Exception e) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "AlertService: GenerateForeGround()", PMWF_Log.getStringFromStackTrace(e));
        }
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("onDestroy()", "Service onStartCommand...");
        // super.onStartCommand(intent, flags, startId);

        GenerateForeGround();
        if (!Shake_Listener.showNoContact) {
            Shake_Listener.showNoContact = true;
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        try {
            super.onDestroy();
            Log.i("onDestroy()", "Service onDestroy...");
            accelSupported = false;
            orientSupported = false;
            stop = true;
            if (sensorMgr != null && shake_event != null) {
                sensorMgr.unregisterListener(shake_event, sensor);
                Log.i("Unregister sensors()", "System Service sensor unregistered");
            }

            Log.i("onDestroy()", "Service onDestroy...");
            accelSupported = false;
            orientSupported = false;
            if (sensorMgr != null && shake_event != null) {
                sensorMgr.unregisterListener(shake_event, sensor);
                Log.i("Unregister sensors()", "System Service sensor unregistered");
            }

            stopForeground(true);
        } catch (Exception e) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "AlertService: onDestroy()", PMWF_Log.getStringFromStackTrace(e));
        }

    }

    @Override
    public boolean stopService(Intent name) {
        super.stopService(name);
        Log.i("onDestroy()", "Service onDestroy...");
        accelSupported = false;
        orientSupported = false;
        stop = true;
        try {
            pollThread.interrupt();
        } catch (Exception e) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "AlertService: stopService()", PMWF_Log.getStringFromStackTrace(e));
        }
        if (sensorMgr != null && shake_event != null) {
            sensorMgr.unregisterListener(shake_event, sensor);
            Log.i("Unregister sensors()", "System Service sensor unregistered");
            Toast.makeText(getApplicationContext(), "Service Stopped", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(getApplicationContext(), "Service Stopped", Toast.LENGTH_SHORT).show();

        // return true;
        return super.stopService(name);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private Runnable pollAlerts = new Runnable() {

        @Override
        public void run() {
            while (true) {
                try {
                    if (!stop) {
                        Log.i("Poll service thread", "Poll thread loop started....");

                        ApplicationSettings.isWeberviceReachable = WebServiceWrapper.Ping();
                        if (ApplicationSettings.isWeberviceReachable) {

                            // pull contact from
                            // server
                            String pullContacts = WebServiceWrapper.Pull(ApplicationSettingsFilePath.AuthenticationKey,
                                    ApplicationSettingsFilePath.Type_Contact);
                            if (pullContacts != null) {
                                if (JSONHelper.getJsonObjectResult("PullResult", "IsSuccessfull", pullContacts)) {
                                    String result = JSONHelper.getResult("Result", pullContacts);

                                    if (result != null && !result.equalsIgnoreCase("null")) {

                                        JSONArray contactListArray = new JSONArray(result);
                                        for (int i = 0; i < contactListArray.length(); i++) {
                                            JSONObject contact = contactListArray.getJSONObject(i);                                           
                                            boolean isDeleted = contact.getBoolean("IsDeletedByOtherUser");
                                            String emailAddress = contact.getString("EmailAddress");
                                            if (isDeleted) {

                                                ApplicationSettings.dbAccessor
                                                        .deletedByOthersUserConnection(emailAddress);
                                                ApplicationSettings.dbAccessor.deletedByOthersAlerts(emailAddress);
                                                int count = ApplicationSettings.dbAccessor
                                                        .getDeletedByOthersUserConnectionsCount(emailAddress);

                                                if (count <= 0) {
                                                    ApplicationSettings.dbAccessor.deleteContactStatus(emailAddress);
                                                    ApplicationSettings.dbAccessor.deleteContact(emailAddress);
                                                }
                                            } else {
                                                ApplicationSettings.dbAccessor.insertUpdateContact(contact.toString());
                                                ApplicationSettings.dbAccessor
                                                        .insertUserConnections(contact.toString());
                                            }
                                        }
                                    }

                                }
                            }
                            /*********************************** FOR USER STATUS ***************************/
                            String userStatus = WebServiceWrapper.Pull(ApplicationSettingsFilePath.AuthenticationKey,
                                    ApplicationSettingsFilePath.UserStatus);
                            if (userStatus != null) {
                                ApplicationSettings.dbAccessor.insertUserStatus(userStatus);

                            }

                            /********************************** Getting Pending Status for Push *****************/
                            String sendAlerts = ApplicationSettings.dbAccessor.getAlertInfo("Pending");
                            if (sendAlerts != null) {
                                sendAlerts = sendAlerts.replace("Peding", "Pending");
                                String sendAlertResponse = WebServiceWrapper.Push(
                                        ApplicationSettingsFilePath.AuthenticationKey,
                                        ApplicationSettingsFilePath.Type_Alert, sendAlerts);
                                if (sendAlertResponse != null) {
                                    ApplicationSettings.dbAccessor.getSendAlertPushResponse(sendAlertResponse);
                                }
                            }

                            /****************************** Getting Accepted and Rejected Alerts for Sync ****************/
                            String acceptedRejectedAlerts = ApplicationSettings.dbAccessor.getAcceptedRejectedAlerts();
                            if (acceptedRejectedAlerts != null) {

                                String acceptedRejectedResponse = WebServiceWrapper.Push(
                                        ApplicationSettingsFilePath.AuthenticationKey,
                                        ApplicationSettingsFilePath.Type_Alert, acceptedRejectedAlerts);

                                if (acceptedRejectedResponse != null) {
                                    // Log.i("Success","Ok");
                                    /******
                                     * Update flag to 1 for stop syncing same
                                     * status
                                     **********/
                                    ApplicationSettings.dbAccessor.UpdateRecieverInfo(acceptedRejectedResponse);
                                }
                            }

                            /********** Alert Recieved from Other Users *********************/
                            String pullAlertsResponse = WebServiceWrapper.Pull(
                                    ApplicationSettingsFilePath.AuthenticationKey,
                                    ApplicationSettingsFilePath.Type_Alert);
                            if (pullAlertsResponse != null) {

                                if (JSONHelper.getJsonObjectResult("PullResult", "IsSuccessfull", pullAlertsResponse)) {
                                    String result = JSONHelper.getResult("Result", pullAlertsResponse);
                                    if (result != null) {
                                        JSONArray resultObj = new JSONArray(result);
                                        alertReceivedList.clear();
                                        for (int i = 0; i < resultObj.length(); i++) {

                                            JSONObject uniObject = resultObj.getJSONObject(i);
                                            String alertStatus = uniObject.getString("AlertStatus");

                                            if (alertStatus.equalsIgnoreCase("Sent")) {
                                                int alertType = uniObject.getInt("AlertType");
                                                String fromUserGuid = uniObject.getString("FromUserGuid");
                                                String toUserGuid = uniObject.getString("ToUserGuid");
                                                String Guid = uniObject.getString("Guid");
                                                String latitude = uniObject.getString("Latitude");
                                                String longitude = uniObject.getString("Longitude");

                                                ApplicationSettings.dbAccessor.insertRecieverInfo(uniObject.toString());
                                                showAlertReceivedNotification(i + 1);

                                                AlertInfo alertReceived = new AlertInfo();
                                                alertReceived.setAlertType(alertType);
                                                alertReceived.setFromUserGuid(fromUserGuid);
                                                alertReceived.setToUserGuid(toUserGuid);
                                                alertReceived.setGuid(Guid);
                                                alertReceived.setLatitude(latitude);
                                                alertReceived.setLongitude(longitude);
                                                alertReceived.setNotificationId(i + 1);
                                                alertReceivedList.add(alertReceived);

                                            }

                                            if (alertStatus.equalsIgnoreCase("Seen")) {
                                                ApplicationSettings.dbAccessor.getSeenAlertPullResponse(uniObject
                                                        .toString());
                                            }
                                            if (alertStatus.equalsIgnoreCase("Accepted")
                                                    || alertStatus.equalsIgnoreCase("Rejected")) {

                                                try {
                                                    // Update
                                                    // Accepeted
                                                    // or
                                                    // Rejected
                                                    // Alerts
                                                    ApplicationSettings.dbAccessor
                                                            .updateAcceptedRejectedAlerts(uniObject.toString());
                                                } catch (Exception e) {
                                                    PMWF_Log.fnlog(PMWF_Log.ERROR,
                                                            "AlertService: updateAcceptedRejectAlerts()",
                                                            PMWF_Log.getStringFromStackTrace(e));
                                                }
                                            }

                                        }
                                        if (alertReceivedList.size() > 0) {

                                            Intent receivedAlert = new Intent(getApplicationContext(),
                                                    AlertPopUpDialog.class);
                                            receivedAlert.putExtra("ReceivedJson", alertReceivedList);

                                            receivedAlert.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                                    | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            getApplicationContext().startActivity(receivedAlert);
                                        }

                                    }
                                }
                            }

                        }
                    }
                } catch (Exception ex) {
                    PMWF_Log.fnlog(PMWF_Log.ERROR, "AlertService: pollAlerts()", PMWF_Log.getStringFromStackTrace(ex));

                } finally {
                    try {
                        Thread.sleep(1 * 1000);
                    } catch (InterruptedException e) {
                        PMWF_Log.fnlog(PMWF_Log.ERROR, "AlertService: pollAlerts(1)",
                                PMWF_Log.getStringFromStackTrace(e));
                    }
                }

            }

        }
    };

    protected void showAlertReceivedNotification(int notificationId) {

        try {
            notificationIds.add(notificationId);
            String notificationTitle = "SamenSafe";
            String notificationText = "Alert Recieved";
            Intent myIntent = null;

            // notification = new
            // Notification.Builder(AutoUpdateActivity.this).setContentTitle(notificationTitle)
            // .setContentText(notificationText).setSmallIcon(R.drawable.icon).build();

            final Notification notification = new Notification(R.drawable.ic_launcher, notificationTitle,
                    System.currentTimeMillis());
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            // if(alertStatus){
            myIntent = new Intent(getApplicationContext(), AlertPopUpActivity.class);
            /*
             * myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
             * Intent.FLAG_ACTIVITY_SINGLE_TOP |
             * Intent.FLAG_ACTIVITY_CLEAR_TOP);
             */
            myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            /*
             * }else{ myIntent = new Intent(AlertService.this,
             * SendAlertListView.class);
             * myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); }
             */

            PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), notificationId, myIntent,
                    PendingIntent.FLAG_CANCEL_CURRENT);

            Uri alertTone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            if (alertTone == null) {
                // alert is null, using backup
                alertTone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                if (alertTone == null) { // I can't see this ever being null (as
                                         // always
                    // have a default notification) but just
                    // incase
                    // alert backup is null, using 2nd backup
                    alertTone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);

                }
            }

            if (alertTone != null) {

                notification.sound = alertTone;
                notification.audioStreamType = AudioManager.STREAM_ALARM;
                AudioManager mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
                int maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM);
                mAudioManager.setStreamVolume(AudioManager.STREAM_ALARM, maxVolume, AudioManager.FLAG_PLAY_SOUND);

                /*
                 * long[] vibrate = { 0, 100, 200, 300, 1000, 2000, 3000, 5000
                 * }; notification.vibrate = vibrate;
                 */

                long[] vibrate = { 1000, 1000, 1000, 1000, 1000 };
                notification.vibrate = vibrate;

                // notification.defaults |= Notification.DEFAULT_VIBRATE;
                notification.flags |= Notification.FLAG_AUTO_CANCEL;
                // notification.contentIntent = pendingIntent;
                notification.setLatestEventInfo(this, notificationTitle, notificationText, pendingIntent);
                notificationManager.notify(notificationId, notification);
                /*
                 * final Handler handler = new Handler();
                 * handler.postDelayed(new Runnable() {
                 * 
                 * @Override public void run() { if (notification.sound != null)
                 * { notification.sound = null; } if(notification.vibrate!=null)
                 * { notification.vibrate = null; } } }, 10000);
                 */

            }
        } catch (Exception e) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "AlertService: showAlertReceivedNotification()",
                    PMWF_Log.getStringFromStackTrace(e));
        }
    }
}
