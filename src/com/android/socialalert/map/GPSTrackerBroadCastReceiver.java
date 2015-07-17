package com.android.socialalert.map;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class GPSTrackerBroadCastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        if (intent.getAction().matches("android.location.PROVIDERS_CHANGED")) {
            // Toast.makeText(context, "in android.location.PROVIDERS_CHANGED",
            // Toast.LENGTH_SHORT).show();
            try {
                GPS_Tracker tracker = new GPS_Tracker(context);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
