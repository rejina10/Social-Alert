package com.android.socialalert.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.Vibrator;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;

import com.android.socialalert.AlertActivity;
import com.android.socialalert.AlertShow;
import com.android.socialalert.AutoUpdateActivity;
import com.android.socialalert.HomePageActivity;
import com.android.socialalert.R;
import com.android.socialalert.logger.PMWF_Log;
import com.android.socialalert.settings.ApplicationSettings;

public class Shake_Listener implements SensorEventListener {

    Context mContext;

    public static float threshold = 10.0f;
    private long currentTime = 0;
    private static final int DURATION = 5000;
    // private boolean showNoContactShake = true;
    public static boolean showNoContact = true;

    private int contactCount;
    // public static boolean isShake = false;

    private final int DATA_X = SensorManager.DATA_X;
    private final int DATA_Y = SensorManager.DATA_Y;
    private final int DATA_Z = SensorManager.DATA_Z;

    private Vibrator vibrator;
    List<Float> x_values = new ArrayList<Float>();
    List<Float> y_values = new ArrayList<Float>();
    List<Float> z_values = new ArrayList<Float>();

    public static List<Float> x_fall_values = new ArrayList<Float>();
    public static List<Float> y_fall_values = new ArrayList<Float>();
    public static List<Float> z_fall_values = new ArrayList<Float>();

    private long lastFallDataCollected = 0;

    private boolean isfreefalldetected = false;

    private int opt_use_shake = 1;
    private int opt_use_mandown = 1;

    public Shake_Listener(Context context) {
        this.mContext = context;
        showNoContact = true;

    }

    public void onAccuracyChanged(Sensor arg0, int arg1) {
        // TODO Auto-generated method stub
    }

    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            x_values.add(event.values[DATA_X]);
            y_values.add(event.values[DATA_Y]);
            z_values.add(event.values[DATA_Z]);
            x_fall_values.add(event.values[DATA_X]);
            y_fall_values.add(event.values[DATA_Y]);
            z_fall_values.add(event.values[DATA_Z]);
        }

        long now = System.currentTimeMillis();
        if (currentTime == 0) {
            currentTime = now;
        }

        if (lastFallDataCollected == 0) {
            lastFallDataCollected = now;
        }

        long diffTime = (now - currentTime);

        // Log.i("diffTime",diffTime+"");
        float dx = 0;
        float dy = 0;
        float dz = 0;

        /*
         * if(diffTime<=DURATION) return;
         */
        // HomePageActivity mContext = HomePageActivity.context;
        if (diffTime >= DURATION && !AlertActivity.isAlertActivityShowing) {
            // Log.e("Time Gap", diffTime + "");

            if (x_values.size() > 2 && y_values.size() > 2 && z_values.size() > 2) {
                float md_x = computeMedian(x_values);
                float md_y = computeMedian(y_values);
                float md_z = computeMedian(z_values);

                dx = calculate_deviation(x_values, x_values.size(), md_x);
                dy = calculate_deviation(y_values, y_values.size(), md_y);
                dz = calculate_deviation(z_values, z_values.size(), md_z);
            }

            if (dx > threshold || dy > threshold || dz > threshold) {
                if (opt_use_shake == 1 && !isCallActive(mContext)) {
                    vibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);

                    currentTime = now;

                    // if (HomePageActivity.isActivityShowing && showNoContact)
                    // {
                    //
                    // contactCount =
                    // ApplicationSettings.dbAccessor.getAllContactCount();
                    // if (contactCount == 0) {
                    // showNoContact = false;
                    // SpannableString ss1 = new SpannableString(
                    // ApplicationSettings.translationSettings.GetTranslation("and_lbl_alert",
                    // "Alert"));
                    // ss1.setSpan(new RelativeSizeSpan(1f), 0, ss1.length(),
                    // 0);
                    // ss1.setSpan(new
                    // ForegroundColorSpan(mContext.getResources().getColor(R.color.lauren)),
                    // 0,
                    // ss1.length(), 0);
                    // AlertShow builder = new AlertShow(mContext);
                    // builder.setCancelable(false);
                    // builder.setTitle(ss1);
                    // builder.setMessage(ApplicationSettings.translationSettings
                    // .GetTranslation(
                    // "and_no_contacts",
                    // "There are currently no contacts linked to alerttype, please go to the menu at the top left of the screen to add new contacts"));
                    // builder.setNegativeButton("OK", new
                    // DialogInterface.OnClickListener() {
                    // public void onClick(DialogInterface dialog, int
                    // whichButton) {
                    //
                    // showNoContact = true;
                    // dialog.dismiss();
                    // return;
                    // }
                    // });
                    // builder.show();
                    // return;
                    //
                    // }
                    //
                    // vibrator.vibrate(2000);
                    // Log.i("onSensorChanged(1)+isActivityShowing",
                    // "Shake detected...");
                    // // SendAlertListView.isActivityShowing = false;
                    // Intent intent = new Intent(mContext,
                    // AlertActivity.class);
                    // // intent.putExtra("Shake", true);
                    // /*
                    // * intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                    // * Intent.FLAG_ACTIVITY_SINGLE_TOP |
                    // * Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    // */
                    // intent.putExtra("ShakeAlert", true);
                    // mContext.startActivity(intent);
                    //
                    // } else {
                    // // vibrator.vibrate(2000);
                    // // isShake = true;
                    // Log.i("onSensorChanged()", "Shake detected...");
                    // // Toast.makeText(mContext,
                    // // SendAlertListView.isActivityShowing+"",
                    // // Toast.LENGTH_SHORT).show();
                    // Intent intent = new Intent(mContext,
                    // AutoUpdateActivity.class);
                    // intent.putExtra("Shake", true);
                    // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                    // Intent.FLAG_ACTIVITY_SINGLE_TOP
                    // | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    // mContext.startActivity(intent);
                    // }
                    
                    Intent intent = new Intent(mContext, AutoUpdateActivity.class);
                    intent.putExtra("Shake", true);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mContext.startActivity(intent);
                }
            }
            x_values.clear();
            y_values.clear();
            z_values.clear();
            if (x_values.size() == 0 && y_values.size() == 0 && z_values.size() == 0) {
                currentTime = 0;
            }

        }

        long fallDuration = now - lastFallDataCollected;

        if (fallDuration >= 200 && !AlertActivity.isAlertActivityShowing) {

            if (DetectFreeFall()) {
                Log.i("onSensorChanged()", "Free fall detected...");
                isfreefalldetected = true;
            }
            x_fall_values.clear();
            y_fall_values.clear();
            z_fall_values.clear();
            lastFallDataCollected = 0;
        }

        if (isfreefalldetected && !AlertActivity.isAlertActivityShowing) {
            vibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);

            currentTime = now;

            if (opt_use_mandown == 1 && !isCallActive(mContext)) {
                // TODO start activity
                Log.i("onSensorChanged()", "Mandown detected...");
                try {

//                    if (HomePageActivity.isActivityShowing && showNoContact) {
//                        contactCount = ApplicationSettings.dbAccessor.getAllContactCount();
//
//                        if (contactCount == 0) {
//                            showNoContact = false;
//                            String title = "Alert";
//                            SpannableString ss1 = new SpannableString(
//                                    ApplicationSettings.translationSettings.GetTranslation("and_lbl_alert", title));
//                            ss1.setSpan(new RelativeSizeSpan(1f), 0, ss1.length(), 0);
//                            ss1.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.lauren)), 0,
//                                    ss1.length(), 0);
//                            AlertShow builder = new AlertShow(mContext);
//                            builder.setCancelable(false);
//                            builder.setTitle(ss1);
//                            builder.setMessage(ApplicationSettings.translationSettings
//                                    .GetTranslation(
//                                            "and_no_contacts",
//                                            "There are currently no contacts linked to alerttype, please go to the menu at the top left of the screen to add new contacts"));
//                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int whichButton) {
//
//                                    showNoContact = true;
//                                    dialog.dismiss();
//                                    return;
//                                }
//                            });
//                            builder.show();
//                            return;
//                        } else {
//                            vibrator.vibrate(2000);
//                            Log.i("onSensorChanged(2)" + HomePageActivity.isActivityShowing, "Shake detected...");
//                            Intent intent = new Intent(mContext, AlertActivity.class);
//                            /*
//                             * intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
//                             * Intent.FLAG_ACTIVITY_SINGLE_TOP |
//                             * Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                             */
//                            intent.putExtra("ShakeAlert", true);
//                            mContext.startActivity(intent);
//                        }
//
//                    } else if (!HomePageActivity.isActivityShowing) {
//
//                        // vibrator.vibrate(2000);
//                        // isShake = true;
//                        Log.i("ShakeListener::", HomePageActivity.isActivityShowing + "");
//                        Intent intent = new Intent(mContext, AutoUpdateActivity.class);
//                        intent.putExtra("Shake", true);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        mContext.startActivity(intent);
//
//                    }
                    Intent intent = new Intent(mContext, AutoUpdateActivity.class);
                    intent.putExtra("Shake", true);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mContext.startActivity(intent);
                } catch (Exception e) {
                    PMWF_Log.fnlog(PMWF_Log.ERROR, "Shake_Listener: onSensorChanged()",
                            PMWF_Log.getStringFromStackTrace(e));
                }
            }

            isfreefalldetected = false;
            // checkShake=false;

        }

    }

    private boolean DetectFreeFall() {
        try {
            Double acceleration_total = 0.0;
            for (int i = 0; i < x_fall_values.size(); i++) {

                double acceleration = getAccleration(x_fall_values.get(i), y_fall_values.get(i), z_fall_values.get(i));
                acceleration_total += acceleration;
            }

            Double accleration_mean = acceleration_total / x_fall_values.size();
            if (accleration_mean <= 3) {
                Log.d("ACCLERATION TEST", "Value = " + accleration_mean);
                return true;
            }
        } catch (Exception e) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "Shake_Listener: DetectFreeFall()", PMWF_Log.getStringFromStackTrace(e));
        }
        return false;
    }

    private double getAccleration(float x, float y, float z) {
        try {
            return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
        } catch (Exception e) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "Shake_Listener: getAccleration()", PMWF_Log.getStringFromStackTrace(e));
        }

        return 0;
    }

    private float computeMedian(List<Float> values) {

        if (values == null || values.size() == 0) {
            return 0;
        }

        if (values.size() == 1) {
            return values.get(0);
        }

        Collections.sort(values);

        int middle = values.size() / 2;
        if (values.size() % 2 == 1) {

            return values.get(middle);
        } else {
            return (values.get(middle - 1) + values.get(middle)) / 2;
        }

    }

    private float calculate_deviation(List<Float> values, int size, float md) {
        float dx;
        if (values == null || size == 0) {
            return 0;
        }
        float sum = 0;
        for (int i = 0; i < size; i++) {
            float item = values.get(i);
            sum += Math.abs(item - md);
        }
        dx = sum / size;
        return dx;
    }

    private boolean isCallActive(Context context) {
        try {
            AudioManager manager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            if (manager.getMode() == AudioManager.MODE_RINGTONE || manager.getMode() == AudioManager.MODE_IN_CALL) {
                return true;
            }
        } catch (Exception e) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "Shake_Listener: isCallActive()", PMWF_Log.getStringFromStackTrace(e));
        }

        return false;
    }

}
