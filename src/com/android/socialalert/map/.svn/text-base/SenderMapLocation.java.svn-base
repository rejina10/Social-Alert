package com.android.socialalert.map;

import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.ResourceProxy;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.android.socialalert.R;
import com.android.socialalert.settings.ApplicationSettings;

public class SenderMapLocation extends SherlockFragmentActivity {

    private MapView myOpenMapView;
    private MapController myMapController;

    LocationManager locationManager;

    MyItemizedOverlay myItemizedOverlay;

    private void initActionBar(String titlebarText) {

        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.custom_titlebar, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.titlebarText);
        mTitleTextView.setText(titlebarText);
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_location);
        initActionBar(ApplicationSettings.translationSettings.GetTranslation("and_lbl_currentLocation",
                "Current location"));
        myOpenMapView = (MapView) findViewById(R.id.openmapview);
        myOpenMapView.setBuiltInZoomControls(true);
        myOpenMapView.setMultiTouchControls(true);
        myMapController = myOpenMapView.getController();
        myMapController.setZoom(18);
        myOpenMapView.setTileSource(TileSourceFactory.MAPQUESTOSM);

        Intent intent = getIntent();
        String latitude = intent.getStringExtra("LATITUDE");
        String longitude = intent.getStringExtra("LONGITUDE");
        // Toast.makeText(getApplicationContext(),latitude
        // +":"+longitude,Toast.LENGTH_SHORT).show();
        // setAlertSenderLocation("51.9461937","4.5722348");
        if (latitude.length() != 0) {
            setAlertSenderLocation(latitude, longitude);
        } else {
            setCurrentLocation();
        }

    }

    private void setCurrentLocation() {
        double[] gps_positions;

        // GPS_Tracker positionHolder = new GPS_Tracker(SenderMapLocation.this);
        gps_positions = GPS_Tracker.getGPSStatic(SenderMapLocation.this);
        // gps_positions = positionHolder.getGPS();
        // gps_positions= new double[]{27.673136,85.422302};
        // positionHolder = null;
        if (gps_positions != null && gps_positions.length == 2) {
            GeoPoint locGeoPoint = new GeoPoint(gps_positions[0], gps_positions[1]);
            Log.i("Gps lat and Long", gps_positions[0] + ":" + gps_positions[1]);

            myMapController.setCenter(locGeoPoint);

            Drawable marker = getResources().getDrawable(R.drawable.location);
            int markerWidth = marker.getIntrinsicWidth();
            int markerHeight = marker.getIntrinsicHeight();
            marker.setBounds(0, markerHeight, markerWidth, 0);

            ResourceProxy resourceProxy = new DefaultResourceProxyImpl(getApplicationContext());

            myItemizedOverlay = new MyItemizedOverlay(marker, resourceProxy);
            myItemizedOverlay.addItem(locGeoPoint, "mylocation", "myPoint1");
            myOpenMapView.getOverlays().add(myItemizedOverlay);

        } else {
            Log.i("Current Location:", "couldnt find the current location");
        }

    }

    public void setAlertSenderLocation(String latitude, String longitude) {
        GeoPoint locGeoPoint = new GeoPoint(Double.parseDouble(latitude), Double.parseDouble(longitude));
        Log.i("Gps lat and Long", latitude + ":" + longitude);

        myMapController.setCenter(locGeoPoint);

        Drawable marker = getResources().getDrawable(R.drawable.location);
        int markerWidth = marker.getIntrinsicWidth();
        int markerHeight = marker.getIntrinsicHeight();
        marker.setBounds(0, markerHeight, markerWidth, 0);

        ResourceProxy resourceProxy = new DefaultResourceProxyImpl(getApplicationContext());
        // myOpenMapView.setBuiltInZoomControls(true);
        // myOpenMapView.setTileSource(TileSourceFactory.MAPQUESTOSM);
        myItemizedOverlay = new MyItemizedOverlay(marker, resourceProxy);
        myOpenMapView.getOverlays().add(myItemizedOverlay);
        myItemizedOverlay.addItem(locGeoPoint, "mylocation", "myPoint1");
    }

}
