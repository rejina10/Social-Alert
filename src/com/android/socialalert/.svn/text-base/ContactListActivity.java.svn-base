package com.android.socialalert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.android.socialalert.logger.PMWF_Log;
import com.android.socialalert.settings.ApplicationSettings;
import com.android.socialalert.settings.ApplicationSettingsFilePath;

public class ContactListActivity extends SherlockFragment {

    public static final int INTENT_ADD_CONTACT = 16;
    public static final int INTENT_EDIT_CONTACT = 17;

    static ListView contactListView;
    TextView txtContactMsg;
    ImageView imgAddContact;
    TextView txtTitlebar;
    Button btnAddContact;
    Button btnBack;
    static ContactListAdapter contactAdapter;
    // LinearLayout line;

    private Timer timer;
    private TimerTask refreshList;
    static ArrayList<ContactInfo> contactData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.contact_list, container, false);
        contactListView = (ListView) rootView.findViewById(R.id.contactlist);
        txtContactMsg = (TextView) rootView.findViewById(R.id.contactMessage);

        ImageView titleImageViewClientLogo = (ImageView) getActivity().findViewById(R.id.title_clientlogo);
        titleImageViewClientLogo.setVisibility(View.VISIBLE);

        ImageView titleIconExtra = (ImageView) getActivity().findViewById(R.id.title_addcontact);
        titleIconExtra.setVisibility(View.GONE);

        txtTitlebar = (TextView) getActivity().findViewById(R.id.titlebarText);
        txtTitlebar.setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_contacts", "Contacts"));

        contactAdapter = null;
        contactData = null;
        
        // line = (LinearLayout) rootView.findViewById(R.id.lineLinear);

        // Loading contact
        loadContactList();

        titleImageViewClientLogo.setImageResource(R.drawable.client_logo);
        // addContatactImageView.setVisibility(View.GONE);

        /*
         * addContatactImageView.setOnClickListener(new OnClickListener() {
         * 
         * @Override public void onClick(View arg0) { try { Intent contactIntent
         * = new Intent(getActivity(), ContactInformation.class);
         * contactIntent.putExtra("ADD_CONTACT", true);
         * startActivityForResult(contactIntent, INTENT_ADD_CONTACT); } catch
         * (Exception e) { Log.e("Error while Refreshing receive alerts",
         * e.toString()); } }
         * 
         * });
         */
        btnBack = (Button) rootView.findViewById(R.id.btnBack);
        btnBack.setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_back", "Back"));
        btnBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, new FragmentMainAlert());
                ft.commit();
            }
        });

        btnAddContact = (Button) rootView.findViewById(R.id.btnAddContact);
        btnAddContact.setText(ApplicationSettings.translationSettings.GetTranslation("lbl_add_newContact",
                "Add New Contact"));
        btnAddContact.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                try {
                    Intent contactIntent = new Intent(getActivity(), ContactInformation.class);
                    contactIntent.putExtra("ADD_CONTACT", true);
                    startActivityForResult(contactIntent, INTENT_ADD_CONTACT);
                } catch (Exception e) {
                    PMWF_Log.fnlog(PMWF_Log.ERROR, "Error while Refreshing receive alerts", e.toString());
                }
            }

        });

        contactListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> contactAdapter, View arg1, int position, long arg3) {

                ContactInfo temp = (ContactInfo) contactAdapter.getItemAtPosition(position);
                Intent contactList = new Intent(getActivity(), ContactDetails.class);

                contactList.putExtra("EmailAddress", temp.getContactEmailAddressName());
                contactList.putExtra("status", temp.getContactType());
                startActivityForResult(contactList, INTENT_EDIT_CONTACT);
            }
        });
        return rootView;

    }

    private void loadContactList() {// load all contact
        try {
            int fromUserId = ApplicationSettings.dbAccessor.getUserId(ApplicationSettingsFilePath.AccountName);
            ArrayList<ContactInfo> tempContactData = ApplicationSettings.dbAccessor.getContactsList(fromUserId);
            if (contactData == null) {
                contactData = tempContactData;
            } else {
                contactData.clear();
                for (ContactInfo ci : tempContactData) {
                    contactData.add(ci);
                }
            }

            if (contactData != null && tempContactData.size() > 0) {

                // for (ContactInfo info : contactData) {
                // Log.i("contact list data", info.contactEmailAddress);
                // }
                Collections.sort(contactData, new Comparator<ContactInfo>() {
                    public int compare(ContactInfo s1, ContactInfo s2) {
                        return s1.getContactLastName().compareToIgnoreCase(s2.getContactLastName());
                    }
                });

                txtContactMsg.setVisibility(View.GONE);

                if (contactAdapter == null) {
                    contactAdapter = new ContactListAdapter(ContactListActivity.this.getActivity(),
                            R.layout.contact_list, contactData);
                    contactListView.setAdapter(contactAdapter);
                } else {
                    contactAdapter.notifyDataSetChanged();
                }

            } else {
                contactData.clear();
                txtContactMsg.setVisibility(View.VISIBLE);
                txtContactMsg.setText(ApplicationSettings.translationSettings.GetTranslation("and_msg_contacts",
                        "Either no contacts are added or the contacts you have added are pending for approval."));
                // line.setVisibility(View.GONE);

                if (contactAdapter != null) {
                    contactAdapter.clear();
                    contactAdapter.notifyDataSetChanged();
                }

            }
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "ContactListActivity::loadContactList()",
                    PMWF_Log.getStringFromStackTrace(ex));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // loadContactList();
        contactAdapter = null;
        contactData = null;
        contactListView.setAdapter(null);
        startTimer();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopTimer();
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
            PMWF_Log.fnlog(PMWF_Log.ERROR, "ContactListActivity: stopTimer()", PMWF_Log.getStringFromStackTrace(ex));
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
                            loadContactList();
                        } catch (Exception e) {
                            PMWF_Log.fnlog(PMWF_Log.ERROR, "ContactListActivity:startTimer()",
                                    PMWF_Log.getStringFromStackTrace(e));
                        }
                    }
                });
            }
        };
        timer.schedule(refreshList, 0, 5 * 1000);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(resultCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == INTENT_ADD_CONTACT || requestCode == INTENT_EDIT_CONTACT) {
                // loadContactList();
                Toast.makeText(getActivity(), "Contact has been updated", Toast.LENGTH_SHORT).show();
                // getActivity().overridePendingTransition(R.anim.slide_in_left,
                // R.anim.slide_out_right);

            }
        }
    }

}
