package com.android.socialalert.map;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class GPS_Tracker implements LocationListener {
    public static LocationManager locationManager;
    public static Location location = null;
    private static String provider;
    private static Context context;

    /**
     * Constructor to initialize context
     * 
     * @param mContext
     *            - Context of the calling activity
     */

    public GPS_Tracker(Context mContext) {
        context = mContext;
        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        getGPS();

    }

    /**
     * Retrieves GPS coordinates of the current position
     * 
     * @return Returns array of double with gps latitude and longitude
     */
    public double[] getGPS() {

        Criteria gpsProvider = new Criteria();
        gpsProvider.setAltitudeRequired(false);
        gpsProvider.setPowerRequirement(Criteria.POWER_LOW);
        // provider = locationManager.getBestProvider(gpsProvider, true);
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        provider = locationManager.getBestProvider(gpsProvider, true);
        // Getting the Current Location

        /*
         * if (gps_enabled) { location = locationManager
         * .getLastKnownLocation(LocationManager.GPS_PROVIDER);
         * Log.i("Current Location from gps", "gps"); }
         */
        /*
         * if (provider != null) {
         * locationManager.requestLocationUpdates(provider, 0, 0, this);
         * location = locationManager.getLastKnownLocation(provider); }
         */

        /*
         * if (location != null) { double gpsLocation[] = new double[2];
         * gpsLocation[0] = location.getLatitude(); gpsLocation[1] =
         * location.getLongitude();
         * 
         * return gpsLocation; }
         */
        if (isNetworkEnabled) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        } else if (gps_enabled) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }
        return null;
    }

    public static double[] getGPSStatic(Context context) {

        // provider = locationManager.getBestProvider(gpsProvider, true);
        if (locationManager == null) {
            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        }
        boolean gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (gps_enabled && location != null) {

            double gpsLocation[] = new double[2];
            gpsLocation[0] = location.getLatitude();
            gpsLocation[1] = location.getLongitude();
            return gpsLocation;
        } else if (location == null && gps_enabled) {

        }
        return null;
    }

    /** Stops listening for gps updates */
    public void stopGPS() {
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location newlocation) {
        if (newlocation != null) {

            Log.i("Location: ", newlocation.getLatitude() + ", " + newlocation.getLongitude());
            location = newlocation;
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public boolean checkGPSStatus() {

        boolean isGPS = false;
        try {
            // get location manager to check gps availability
            isGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        } catch (Exception ex) {

        }
        return isGPS;

    }
}
