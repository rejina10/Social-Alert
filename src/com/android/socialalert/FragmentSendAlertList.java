package com.android.socialalert;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.android.socialalert.common.NetworkService;
import com.android.socialalert.logger.PMWF_Log;
import com.android.socialalert.settings.ApplicationSettings;

public class FragmentSendAlertList extends SherlockFragment {
    ListView lv;
    TextView label;
    ImageView imgRefresh;
    TextView txtTitlebar;

    private Timer timer;
    private TimerTask refreshList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.listview, container, false);
        label = (TextView) rootView.findViewById(R.id.alertMessage);
        lv = (ListView) rootView.findViewById(R.id.AlertList);

        setHasOptionsMenu(true);
        imgRefresh = (ImageView) getActivity().findViewById(R.id.title_addcontact);
        imgRefresh.setVisibility(View.VISIBLE);

        ImageView titleImageViewClientLogo = (ImageView) getActivity().findViewById(R.id.title_clientlogo);
        titleImageViewClientLogo.setVisibility(View.GONE);

        // imgRefresh.setBackgroundResource(R.drawable.refresh);
        imgRefresh.setImageResource(R.drawable.refresh);
        imgRefresh.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new LoadSendAlertList().execute();
            }
        });
        txtTitlebar = (TextView) getActivity().findViewById(R.id.titlebarText);
        txtTitlebar
                .setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_sentAlerts", "Sent Alerts"));

        populateSendAlertList();
        return rootView;
    }

    private void populateSendAlertList() {
        try {

            ArrayList<Person> m_data = ApplicationSettings.dbAccessor.getAlertList();

            if (m_data != null) {
                label.setText("");
                label.setVisibility(View.GONE);
                CustomBaseAdapter adapter = new CustomBaseAdapter(FragmentSendAlertList.this.getActivity(), m_data);
                lv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            } else {
                label.setText(ApplicationSettings.translationSettings.GetTranslation("and_msg_noSentAlert",
                        "There are no sent alerts to display."));
            }
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "FragmentSendAlertList::populateSendAlertList()",
                    PMWF_Log.getStringFromStackTrace(ex));
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        startTimer();
        // populateSendAlertList();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopTimer();
    }

    private class LoadSendAlertList extends AsyncTask<Void, Void, ArrayList<Person>> {

        ProgressDialog refreshDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String message = ApplicationSettings.translationSettings.GetTranslation("and_msg_refreshing",
                    "Refreshing....");
            SpannableString ss1 = new SpannableString(message);
            ss1.setSpan(new RelativeSizeSpan(1f), 0, ss1.length(), 0);
            ss1.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.lauren)), 0, ss1.length(), 0);
            refreshDialog = new ProgressDialog(FragmentSendAlertList.this.getActivity());
            refreshDialog.setMessage(ss1);
            refreshDialog.show();
        }

        @Override
        protected ArrayList<Person> doInBackground(Void... params) {

            ArrayList<Person> m_data = ApplicationSettings.dbAccessor.getAlertList();
            return m_data;
        }

        @Override
        protected void onPostExecute(ArrayList<Person> result) {
            super.onPostExecute(result);
            try {
                if (result != null) {
                    /* label.setText(""); */
                    Log.e("sent alert list not null", "OK");
                    try {
                        CustomBaseAdapter adapter = new CustomBaseAdapter(FragmentSendAlertList.this.getActivity(),
                                result);
                        lv.setAdapter(adapter);
                    } catch (Exception e) {
                        PMWF_Log.fnlog(PMWF_Log.ERROR, "Error during refreshing sent alert list",
                                PMWF_Log.getStringFromStackTrace(e));
                    }

                } else {
                    label.setText(ApplicationSettings.translationSettings.GetTranslation("and_msg_noSentAlert",
                            "There are no sent alerts to display."));
                }
                refreshDialog.dismiss();
            } catch (Exception ex) {
                PMWF_Log.fnlog(PMWF_Log.ERROR, "FragmentSendAlertList: LoadSendAlertList()",
                        PMWF_Log.getStringFromStackTrace(ex));
            }
        }
    }

    public void startTimer() {

        final Handler handler = new Handler();
        timer = new Timer();
        refreshList = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            if (!NetworkService.hasInternetConnection(FragmentSendAlertList.this.getActivity())) {

                                boolean isPendingAlertPresent = ApplicationSettings.dbAccessor
                                        .getAlertListWithStatus("Pending");

                                if (isPendingAlertPresent) {
                                    Toast.makeText(
                                            getActivity(),
                                            ApplicationSettings.translationSettings
                                                    .GetTranslation("and_msg_alertPendingMsg",
                                                            "Your alert has been queued due to network unavailability. It will be sent after connection resumes!"),
                                            100 * 1000).show();
                                }

                            }
                            populateSendAlertList();
                        } catch (Exception ex) {
                            PMWF_Log.fnlog(PMWF_Log.ERROR, "FragmentSendAlertList:startTimer()",
                                    PMWF_Log.getStringFromStackTrace(ex));
                        }
                    }
                });
            }
        };
        timer.schedule(refreshList, 0, 6 * 1000);
    }

    private void stopTimer() {
        try {
            if (timer != null) {
                timer.cancel();
                timer.purge();
            }

            if (refreshList != null) {
                refreshList.cancel();
            }
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "FragmentSendAlertList: stopTimer()", PMWF_Log.getStringFromStackTrace(ex));
        }
    }
}
