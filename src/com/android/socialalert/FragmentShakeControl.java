package com.android.socialalert;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.android.socialalert.service.Shake_Listener;
import com.android.socialalert.settings.ApplicationSettings;

public class FragmentShakeControl extends SherlockFragment {

    private SeekBar shakeControl = null;
    TextView txtTitlebar;
    TextView lblShakeControl;
    TextView low, medium, high;
    Button btnOk;
    Button btnCancel;

    float seekBarMax;
    float threshold;

    float CurrentLevel;

    boolean isSeekbarChanged = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_shakecontrol, container, false);

        shakeControl = (SeekBar) rootView.findViewById(R.id.volume_bar);
        seekBarMax = shakeControl.getMax();
        low = (TextView) rootView.findViewById(R.id.low);
        low.setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_low", "low"));

        medium = (TextView) rootView.findViewById(R.id.medium);
        medium.setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_medium", "medium"));

        high = (TextView) rootView.findViewById(R.id.high);
        high.setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_high", "high"));

        txtTitlebar = (TextView) getActivity().findViewById(R.id.titlebarText);
        txtTitlebar.setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_shakeControl",
                "Shake control"));

        lblShakeControl = (TextView) rootView.findViewById(R.id.lblShakeControl);
        lblShakeControl.setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_shakeControl",
                "Shake control"));

        Shake_Listener.threshold = ApplicationSettings.dbAccessor.getShakeThresholdValue();

        float sh = (Shake_Listener.threshold * 100) / 10;

        float progress = ((100 - sh) * 14) / 100;

        shakeControl.setProgress((int) (Math.round(progress)));
        /*
         * if(Shake_Listener.threshold == 10.0f){ //low
         * shakeControl.setProgress(0); }
         * 
         * if(Shake_Listener.threshold == 5.0f){ //medium
         * shakeControl.setProgress(1); }
         * 
         * if(Shake_Listener.threshold == 2.0f){ // high
         * shakeControl.setProgress(2); }
         */

        /*
         * shakeControl.setOnTouchListener(new OnTouchListener() {
         * 
         * @Override public boolean onTouch(View v, MotionEvent event) { if
         * (event.getAction() == MotionEvent.ACTION_UP) { int CurrentLevel =
         * shakeControl.getProgress();
         * 
         * 
         * if(CurrentLevel==0){ Shake_Listener.threshold = 2.0f;
         * Toast.makeText(ShakeControl.this,"seek bar progress:" +
         * Shake_Listener.threshold,Toast.LENGTH_SHORT).show(); }else
         * if(CurrentLevel==1){ Shake_Listener.threshold =5.0f;
         * Toast.makeText(ShakeControl.this,"seek bar progress:" +
         * Shake_Listener.threshold,Toast.LENGTH_SHORT).show(); }else{
         * Shake_Listener.threshold = 8.0f;
         * Toast.makeText(ShakeControl.this,"seek bar progress:" +
         * Shake_Listener.threshold,Toast.LENGTH_SHORT).show(); } }
         * 
         * return false; }
         * 
         * });
         */

        shakeControl.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                CurrentLevel = progress;

                /*
                 * if(CurrentLevel==0){ Shake_Listener.threshold = 10.0f; }else
                 * if(CurrentLevel==1){ Shake_Listener.threshold = 5.0f; }else{
                 * Shake_Listener.threshold = 2.0f; }
                 */

                /*
                 * if(CurrentLevel>0 && CurrentLevel < 50){
                 * shakeControl.setProgress(CurrentLevel);
                 * Shake_Listener.threshold = 10.0f;
                 * //Toast.makeText(getActivity(),"seek bar progress:" +
                 * CurrentLevel+"",Toast.LENGTH_SHORT).show(); }else
                 * if(CurrentLevel>50 && CurrentLevel < 99){
                 * shakeControl.setProgress(CurrentLevel);
                 * Shake_Listener.threshold = 5.0f;
                 * //Toast.makeText(getActivity(),"seek bar progress:" +
                 * CurrentLevel+"",Toast.LENGTH_SHORT).show(); }else{
                 * shakeControl.setProgress(CurrentLevel);
                 * Shake_Listener.threshold = 2.0f;
                 * //Toast.makeText(getActivity(),"seek bar progress:" +
                 * CurrentLevel+"",Toast.LENGTH_SHORT).show(); }
                 */
            }
        });

        /*
         * low.setOnClickListener(new OnClickListener() {
         * 
         * @Override public void onClick(View v) { shakeControl.setProgress(0);
         * 
         * } });
         * 
         * medium.setOnClickListener(new OnClickListener() {
         * 
         * @Override public void onClick(View v) { shakeControl.setProgress(1);
         * 
         * } });
         * 
         * high.setOnClickListener(new OnClickListener() {
         * 
         * @Override public void onClick(View v) { shakeControl.setProgress(2);
         * 
         * } });
         */

        btnOk = (Button) rootView.findViewById(R.id.btnOk);
        btnOk.setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_save", "Save"));
        btnOk.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                CurrentLevel = shakeControl.getProgress();
                threshold = (CurrentLevel / 14) * 100;
                Shake_Listener.threshold = ((100 - threshold) * 10) / 100;

                ApplicationSettings.dbAccessor.saveShakeThresholdValue(Shake_Listener.threshold);

                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, new FragmentSettings());
                ft.commit();

            }
        });

        btnCancel = (Button) rootView.findViewById(R.id.btnCancel);
        btnCancel.setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_cancel", "Cancel"));
        btnCancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, new FragmentSettings());
                ft.commit();
            }
        });

        return rootView;
    }
}
