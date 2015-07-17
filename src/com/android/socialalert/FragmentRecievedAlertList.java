package com.android.socialalert;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.android.socialalert.logger.PMWF_Log;
import com.android.socialalert.settings.ApplicationSettings;

public class FragmentRecievedAlertList extends SherlockFragment {
    ListView lv;
    TextView label;
    TextView txtTitlebar;
    ImageView imgRefresh;

    Timer timer;
    TimerTask refreshList;

    private void populateRecievedAlertList() {
        try {
            // new LoadSendAlertList().execute("");
            ArrayList<Person> m_data = ApplicationSettings.dbAccessor.getAllAlertRecievedList();

            if (m_data != null) {
                label.setText("");
                label.setVisibility(View.GONE);

                RecievedAlertAdapter adapter = new RecievedAlertAdapter(FragmentRecievedAlertList.this.getActivity(),
                        m_data);
                lv.setAdapter(adapter);
            } else {
                label.setText(ApplicationSettings.translationSettings.GetTranslation("and_msg_noReceivedAlerts",
                        "There are no received alerts to display."));
            }
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "FragmentRecievedAlertList::populateRecievedAlertList()",
                    PMWF_Log.getStringFromStackTrace(ex));
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.listview, container, false);
        label = (TextView) rootView.findViewById(R.id.alertMessage);
        lv = (ListView) rootView.findViewById(R.id.AlertList);
        /* lv.setBackgroundColor(Color.BLACK); */
        setHasOptionsMenu(true);
        imgRefresh = (ImageView) getActivity().findViewById(R.id.title_addcontact);
        imgRefresh.setVisibility(View.VISIBLE);
        imgRefresh.setImageResource(R.drawable.refresh);

        ImageView titleImageViewClientLogo = (ImageView) getActivity().findViewById(R.id.title_clientlogo);
        titleImageViewClientLogo.setVisibility(View.GONE);

        imgRefresh.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new ReceivedListRefresh().execute();

            }
        });

        txtTitlebar = (TextView) getActivity().findViewById(R.id.titlebarText);
        txtTitlebar.setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_receivedAlerts",
                "Received Alerts"));

        populateRecievedAlertList();

        // getView().setOnKeyListener(new OnKeyListener() {
        // @Override
        // public boolean onKey(View v, int keyCode, KeyEvent event) {
        // if (keyCode == KeyEvent.KEYCODE_BACK) {
        // FragmentManager fm = getFragmentManager();
        // if (fm.getBackStackEntryCount() > 0) {
        // fm.popBackStack();
        // }
        // }
        // return false;
        // }
        // });

        return rootView;
    }

    // @Override
    // public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    // // TODO Auto-generated method stub
    // super.onCreateOptionsMenu(menu, inflater);
    // inflater.inflate(R.menu.actionbar_optionmenu, menu);
    // }

    @Override
    public void onResume() {
        super.onResume();
        startTimer();
        // populateRecievedAlertList();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopTimer();
    }

    private class ReceivedListRefresh extends AsyncTask<Void, Void, Void> {

        ProgressDialog refreshDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String message = ApplicationSettings.translationSettings.GetTranslation("and_msg_refreshing",
                    "Refreshing....");
            SpannableString ss1 = new SpannableString(message);
            ss1.setSpan(new RelativeSizeSpan(1f), 0, ss1.length(), 0);
            ss1.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.lauren)), 0, ss1.length(), 0);

            refreshDialog = new ProgressDialog(FragmentRecievedAlertList.this.getActivity());
            refreshDialog.setMessage(ss1);
            refreshDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            FragmentTransaction fragTransaction = FragmentRecievedAlertList.this.getActivity()
                    .getSupportFragmentManager().beginTransaction();
            fragTransaction.detach(new FragmentRecievedAlertList());
            fragTransaction.attach(new FragmentRecievedAlertList());
            fragTransaction.commit();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            try {
                refreshDialog.dismiss();
            } catch (Exception ex) {
                PMWF_Log.fnlog(PMWF_Log.ERROR, "FragmentReceivedAlertList: ReceiveListRefresh onPostExecute()",
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
                            populateRecievedAlertList();
                        } catch (Exception ex) {
                            PMWF_Log.fnlog(PMWF_Log.ERROR, "FragmentReceivedAlert: startTimer()",
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
                timer = null;
            }

            if (refreshList != null) {
                refreshList.cancel();
            }
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "FragmentReceivedAlertList: stopTimer()",
                    PMWF_Log.getStringFromStackTrace(ex));
        }
    }

}
