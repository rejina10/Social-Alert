package com.android.socialalert;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.android.socialalert.common.AlertMessage;
import com.android.socialalert.database.UsersInfo;
import com.android.socialalert.json.JSONHelper;
import com.android.socialalert.logger.PMWF_Log;
import com.android.socialalert.settings.ApplicationSettings;
import com.android.socialalert.settings.ApplicationSettingsFilePath;
import com.android.socialalert.settings.WebServiceWrapper;

public class ContactDetails extends SherlockActivity {

    public static final int INTENT_EDIT_CONTACT = 17;

    ArrayList<UsersInfo> arrContactInfo = new ArrayList<UsersInfo>();
    CheckBox[] alertType = new CheckBox[4];
    ImageView delete;
    ImageView edit, clientLogo;
    TextView contactFirstName;
    TextView contactLastName;
    TextView contactEmailAddressName;
    Button btnCancel;
    String contactStatus;

    private void initActionBar(String titlebarText) {

        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.custom_titlebar, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.titlebarText);
        mTitleTextView.setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_contactDetails",
                titlebarText));
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.contact_details);
            initActionBar(ApplicationSettings.translationSettings.GetTranslation("and_lbl_contactDetails",
                    "Contact Details"));

            setUpView();

            setUpViewListener();

            Intent intent = getIntent();
            String emailAddress = intent.getStringExtra("EmailAddress");
            contactStatus = intent.getStringExtra("status");
            if (contactStatus.equalsIgnoreCase("Sender")) {
                delete.setVisibility(View.GONE);
                edit.setVisibility(View.GONE);
            }
            // ArrayList<String> alertInfo =
            // ApplicationSettings.dbAccessor.getEditAlertInfo(emailAddress);
            ArrayList<String> alertInfo = ApplicationSettings.dbAccessor.getEditAlertInfo(emailAddress, contactStatus);
            UsersInfo tempUser = ApplicationSettings.dbAccessor.getContactInfo(emailAddress, alertInfo); //
            if (tempUser != null) {
                arrContactInfo.add(tempUser);
                String contactTempInfo = convertArrayToJsonString(arrContactInfo, true);
                loadTemporaryContactInfo(contactTempInfo);
            } else {
                Toast.makeText(
                        ContactDetails.this,
                        ApplicationSettings.translationSettings.GetTranslation("and_msg_errRetrieveContact",
                                "Could not get contact details"), Toast.LENGTH_SHORT).show();
                finish();
            }
        } catch (Exception ex) {
            Toast.makeText(
                    ContactDetails.this,
                    ApplicationSettings.translationSettings.GetTranslation("and_msg_errRetrieveContact",
                            "Could not get contact details"), Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void setUpViewListener() {
        btnCancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                setResult(RESULT_OK, intent);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        edit.setOnClickListener(new OnClickListener() { // edit

            @Override
            public void onClick(View v) {
                Intent contactList = new Intent(ContactDetails.this, ContactInformation.class);

                contactList.putExtra("EmailAddress", contactEmailAddressName.getText().toString().trim());

                contactList.putExtra("EDITCONTACT", true);
                contactList.putExtra("ADD_CONTACT", false);
                contactList.putExtra("TEMP_CONTACT_INFO", "");
                contactList.putExtra("status", contactStatus);
                startActivityForResult(contactList, INTENT_EDIT_CONTACT);
            }
        });

        delete.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                String title = ApplicationSettings.translationSettings.GetTranslation("and_lbl_deleteContact",
                        "Delete contact");
                new AlertShow(ContactDetails.this)
                        .setTitle(AlertMessage.setAlertHeaderColor(title, getApplicationContext()))
                        .setMessage(
                                ApplicationSettings.translationSettings.GetTranslation("and_msg_deleteContact",
                                        "Are you sure to delete contact?"))
                        .setNegativeButton(
                                ApplicationSettings.translationSettings.GetTranslation("and_lbl_cancel", "Cancel"),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //Intent intent = getIntent();
                                        //setResult(RESULT_OK, intent);
                                        //finish();
                                        //overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                                        dialog.dismiss();
                                    }
                                })
                        .setPositiveButton(ApplicationSettings.translationSettings.GetTranslation("and_lbl_ok", "OK"),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        try {
                                            dialog.dismiss();
                                            DeleteContact deleteContact = new DeleteContact();
                                            if (deleteContact.getStatus() == AsyncTask.Status.FINISHED) {
                                                deleteContact = new DeleteContact();
                                            }

                                            if (deleteContact.getStatus() != AsyncTask.Status.RUNNING) {
                                                deleteContact.execute();
                                            }

                                            // deletAsyncTask.execute();
                                        } catch (Exception ex) {
                                            PMWF_Log.fnlog(PMWF_Log.ERROR, "ContactDetails: deleteContact(1)",
                                                    PMWF_Log.getStringFromStackTrace(ex));
                                        }
                                    }
                                }).setCancelable(true).show();

            }

        });

    }

    private void setUpView() {

        delete = (ImageView) findViewById(R.id.title_delete);
        edit = (ImageView) findViewById(R.id.title_addcontact);
        clientLogo = (ImageView) findViewById(R.id.title_clientlogo);

        clientLogo.setVisibility(View.GONE);

        edit.setVisibility(View.VISIBLE);
        edit.setImageResource(R.drawable.edit);

        delete.setVisibility(View.VISIBLE);
        delete.setImageResource(R.drawable.delete);

        contactFirstName = (TextView) findViewById(R.id.contactFirstName);
        contactLastName = (TextView) findViewById(R.id.contactLastName);
        contactEmailAddressName = (TextView) findViewById(R.id.contactEmailAddress);

        alertType[0] = (CheckBox) findViewById(R.id.chkAlertType1);
        alertType[0].setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_alertype_burglary",
                "Burglary")); // Inbraak

        alertType[1] = (CheckBox) findViewById(R.id.chkAlertType2);
        alertType[1].setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_alertype_accident",
                "Accident")); // Ongeval

        alertType[2] = (CheckBox) findViewById(R.id.chkAlertType3);
        alertType[2].setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_alertype_fire", "Fire"));// Brand

        alertType[3] = (CheckBox) findViewById(R.id.chkAlertType4);
        alertType[3].setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_alertype_aggression",
                "Aggression")); // Aggressie

        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_back", "Back"));
    }

    protected String convertArrayToJsonString(ArrayList<UsersInfo> tempContactInfo, boolean isEditContact) {
        try {
            JSONArray usersInfoArray = JSONHelper.contactListTOJsonArray(tempContactInfo, isEditContact);
            JSONObject result = new JSONObject();
            result.put("Contacts", usersInfoArray);
            return result.toString();

        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "ContactDetails: convertArrayToJsonString()",
                    PMWF_Log.getStringFromStackTrace(ex));
        }
        return null;
    }

    class DeleteContact extends AsyncTask<String, String, String> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(ContactDetails.this);
            String message = ApplicationSettings.translationSettings.GetTranslation("and_msg_deletingContact",
                    "Deleting contact....");
            dialog.setMessage(AlertMessage.setAlertHeaderColor(message, getApplicationContext()));
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String guid = ApplicationSettings.dbAccessor.getGuidFromEmail(contactEmailAddressName.getText()
                        .toString().trim());
                ApplicationSettings.isWeberviceReachable = WebServiceWrapper.Ping();
                if (ApplicationSettings.isWeberviceReachable) {
                    String deleteResponse = WebServiceWrapper.DeleteContact(
                            ApplicationSettingsFilePath.AuthenticationKey, guid);
                    if (deleteResponse != null) {

                        if (JSONHelper.getJsonObjectResult("DeleteContactResult", "IsSuccessfull", deleteResponse)) {

                            ApplicationSettings.dbAccessor.deleteUserConnection(contactEmailAddressName.getText()
                                    .toString().trim());
                            ApplicationSettings.dbAccessor.deleteAlerts(contactEmailAddressName.getText().toString()
                                    .trim());
                            int count = ApplicationSettings.dbAccessor
                                    .getUserConnectionsCountRcv(contactEmailAddressName.getText().toString().trim());

                            if (count <= 0) {
                                ApplicationSettings.dbAccessor.deleteContactStatus(contactEmailAddressName.getText()
                                        .toString().trim());
                                ApplicationSettings.dbAccessor.deleteContact(contactEmailAddressName.getText()
                                        .toString().trim());

                            }
                            return "SUCCESS";
                        }
                    } else {
                        String msg = JSONHelper.getMessage("Message", deleteResponse); // login
                        // exist.
                        return msg;
                    }
                } else {
                    return ApplicationSettings.translationSettings.GetTranslation("and_msg_noInternetAccess",
                            "No internet access.");
                }
                return ApplicationSettings.translationSettings.GetTranslation("and_msg_noInternetAccess",
                        "No internet access.");
            } catch (Exception ex) {
                PMWF_Log.fnlog(PMWF_Log.ERROR, "ContactDetails: Delete doInBackground()",
                        PMWF_Log.getStringFromStackTrace(ex));
            }
            return "FAILED";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                if (result != null && result.length() > 0) {

                    if (result.equals("SUCCESS")) {
                        Intent intent = getIntent();
                        setResult(RESULT_OK, intent);
                        finish();

                    } else {
                        AlertMessage.Alert(result, ContactDetails.this);
                    }
                }
                dialog.dismiss();
            } catch (Exception ex) {
                PMWF_Log.fnlog(PMWF_Log.ERROR, "ContactDetails: DeleteContact onPostExecute()",
                        PMWF_Log.getStringFromStackTrace(ex));
                finish();
            }
        }

    };

    private void loadTemporaryContactInfo(String tempContactInfo) throws Exception {

        try {
            JSONObject contactjson = new JSONObject(tempContactInfo);

            JSONArray contactJsonObj = contactjson.getJSONArray("Contacts");

            String fName = "";
            String lName = "";
            String emailAddress = "";

            JSONObject uniObject = contactJsonObj.getJSONObject(0);
            fName = uniObject.getString("FirstName");
            lName = uniObject.getString("LastName");
            emailAddress = uniObject.getString("EmailAddress");

            // loop array
            JSONArray alertTypeArray = (JSONArray) uniObject.get("AlertTypes");
            for (int k = 0; k < alertTypeArray.length(); k++) {
                if (alertTypeArray.get(k).toString().equalsIgnoreCase("5")) {
                    alertType[0].setChecked(true);
                }
                if (alertTypeArray.get(k).toString().equalsIgnoreCase("4")) {
                    alertType[1].setChecked(true);
                }

                if (alertTypeArray.get(k).toString().equalsIgnoreCase("2")) {
                    alertType[2].setChecked(true);
                }

                if (alertTypeArray.get(k).toString().equalsIgnoreCase("3")) {
                    alertType[3].setChecked(true);
                }
            }

            contactFirstName.setText(fName + " " + lName);
            // contactLastName.setText(lName);
            contactEmailAddressName.setText(emailAddress);

            alertType[0].setEnabled(false);
            alertType[1].setEnabled(false);
            alertType[2].setEnabled(false);
            alertType[3].setEnabled(false);

        } catch (Exception e) {
            PMWF_Log.fnlog(PMWF_Log.INFO, "ContactDetails::loadTemporaryContactInfo()",
                    PMWF_Log.getStringFromStackTrace(e));
            throw e;
        }

    }

    @Override
    public void onBackPressed() {
        Intent intent = getIntent();
        setResult(RESULT_OK, intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == INTENT_EDIT_CONTACT) {
            if (resultCode == RESULT_OK) {
                Intent intent = getIntent();
                // intent.putExtra("EXIT", true);
                setResult(RESULT_OK, intent);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        }
    }
}
