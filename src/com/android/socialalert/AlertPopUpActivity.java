package com.android.socialalert;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.android.socialalert.logger.PMWF_Log;
import com.android.socialalert.map.SenderMapLocation;
import com.android.socialalert.settings.ApplicationSettings;

public class AlertPopUpActivity extends SherlockFragmentActivity {

    public static final int INTENT_RCVALERT_POPUP = 18;

    TextView alertSenderName;
    TextView alertType;
    TextView alertRecievedDateTime;
    ImageView viewLocation;
    String latitude;
    String longitude;

    private void initActionBar(String titlebarText) {

        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.custom_titlebar, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.titlebarText);
        mTitleTextView.setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_alertReceived",
                titlebarText));
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.alert_recieved_popup);

            initActionBar("Alert Received");

            setUpView();
            setUpViewListener();
            loadData();
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "AlertPopUpActivity: onCreate()", PMWF_Log.getStringFromStackTrace(ex));
            finish();
        }
    }

    private void setUpView() {
        alertSenderName = (TextView) findViewById(R.id.alertSenderName);
        alertType = (TextView) findViewById(R.id.alertTypeValue);
        alertRecievedDateTime = (TextView) findViewById(R.id.alertRecievedDateTime);
        viewLocation = (ImageView) findViewById(R.id.ImgMap);
        // viewLocation.setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_showLocation",
        // "Show location"));
    }

    private void setUpViewListener() {
        viewLocation.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Toast.makeText(getApplicationContext(),
                // "view location clicked::"+latitude+"&&"+longitude,
                // Toast.LENGTH_LONG).show();
                Intent intent = new Intent(AlertPopUpActivity.this, SenderMapLocation.class);
                intent.putExtra("LATITUDE", latitude);
                intent.putExtra("LONGITUDE", longitude);
                startActivityForResult(intent, INTENT_RCVALERT_POPUP);
            }

        });

    }

    private void loadData() {

        try {
            ArrayList<Person> m_data = ApplicationSettings.dbAccessor.getAlertRecieved();

            if (m_data != null && m_data.size() > 0) {
                if (m_data.get(0).getContactFirstName().length() > 0) {
                    alertSenderName.setText(m_data.get(0).getContactLastName() + "("
                            + m_data.get(0).getContactFirstName() + ")");
                } else {
                    alertSenderName.setText(m_data.get(0).getContactLastName());
                }

                alertType.setText(m_data.get(0).getContactAlertType());
                alertRecievedDateTime.setText(m_data.get(0).getAlertSendDateTime());

                latitude = m_data.get(0).getLatitude();
                longitude = m_data.get(0).getLongitude();
                // Checking location null or not ************
                if (latitude.length() == 0 && longitude.length() == 0) {
                    viewLocation.setVisibility(View.GONE);
                }
            } else {
                PMWF_Log.fnlog(PMWF_Log.INFO, "AlertPopUpActivity: loadData()", "No data present");
            }
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "send()", PMWF_Log.getStringFromStackTrace(ex));
        }
    }

    /*
     * @Override public void onBackPressed() { super.onBackPressed();
     * 
     * if(HomePageActivity.isActivityShowing){
     * 
     * }else{ Intent intent = new
     * Intent(AlertPopUpActivity.this,HomePageActivity.class);
     * startActivity(intent); }
     * 
     * }
     */
}
