package com.android.socialalert;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.android.socialalert.common.AlertMessage;
import com.android.socialalert.common.NetworkService;
import com.android.socialalert.common.Validate;
import com.android.socialalert.database.UsersInfo;
import com.android.socialalert.json.JSONHelper;
import com.android.socialalert.logger.PMWF_Log;
import com.android.socialalert.settings.ApplicationSettings;
import com.android.socialalert.settings.ApplicationSettingsFilePath;
import com.android.socialalert.settings.WebServiceWrapper;
import com.android.socialalert.utilities.HideKeyboard;

public class ContactInformation extends SherlockFragmentActivity implements TextWatcher {

    EditText contactFirstName;
    EditText contactLastName;
    EditText contactEmailAddressName;

    TableRow errorMsg;
    TextView txtErrorMsg;

    CheckBox[] alertType = new CheckBox[4];

    Button btnBack;
    Button txtExtraContact;
    RelativeLayout llExtraContact;
    Button btnFinish;
    ImageView imgDelete;
    ImageView imgAddContact;

    ArrayList<UsersInfo> arrContactInfo = new ArrayList<UsersInfo>();
    LinearLayout contactFieldWrapper;
    private boolean isAddContact;
    private boolean isEditContact;

    LinearLayout line;

    private void initActionBar(String titlebarText) {

        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.custom_titlebar, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.titlebarText);
        mTitleTextView.setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_contactInformation",
                titlebarText));
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);

        HideKeyboard.setupUI(mCustomView.findViewById(R.id.custom_action_bar), ContactInformation.this); // set
                                                                                                         // view
                                                                                                         // to
                                                                                                         // hide
                                                                                                         // keyboard
                                                                                                         // on
                                                                                                         // its
                                                                                                         // touch

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.contact_information);
        HideKeyboard.setupUI(findViewById(R.id.ContactMainLayout), ContactInformation.this);
        initActionBar("Contact Information");

        setUpView();

        /*
         * final View activityRootView = findViewById(R.id.ContactMainLayout);
         * activityRootView.getViewTreeObserver().addOnGlobalLayoutListener( new
         * OnGlobalLayoutListener() {
         * 
         * @Override public void onGlobalLayout() { Rect r = new Rect(); // r
         * will be populated with the coordinates of your view // that area
         * still visible. activityRootView.getWindowVisibleDisplayFrame(r);
         * 
         * int heightDiff = activityRootView.getRootView() .getHeight() -
         * (r.bottom - r.top); if (heightDiff > 100) { // if more than 100
         * pixels, its // probably a keyboard... //
         * Toast.makeText(getApplicationContext(), // "keyboard might be",
         * Toast.LENGTH_SHORT).show();
         * //findViewById(R.id.linearExtra).setVisibility(View.GONE);
         * findViewById(R.id.bottomMenus).setVisibility( View.GONE); } else {
         * //findViewById(R.id.linearExtra).setVisibility(View.VISIBLE);
         * findViewById(R.id.bottomMenus).setVisibility( View.VISIBLE); } } });
         */
        // setUpTitle();
        setBtnListener();

        Intent intent = getIntent();
        String tempContactInfo = intent.getStringExtra("TEMP_CONTACT_INFO");

        isAddContact = intent.getBooleanExtra("ADD_CONTACT", false);

        isEditContact = intent.getBooleanExtra("EDITCONTACT", false);
        String emailAddress = intent.getStringExtra("EmailAddress");
        String contactStatus = intent.getStringExtra("status");

        if (tempContactInfo != null && tempContactInfo.length() > 0) {
            loadTemporaryContactInfo(tempContactInfo);// TODO when this gets
                                                      // called
        } else {
            if (isEditContact) {

                arrContactInfo.clear();
                ArrayList<String> alertInfo = ApplicationSettings.dbAccessor.getEditAlertInfo(emailAddress,
                        contactStatus);
                UsersInfo tempUser = ApplicationSettings.dbAccessor.getContactInfo(emailAddress, alertInfo); //
                if (tempUser != null) {
                    arrContactInfo.add(tempUser);
                    String contactTempInfo = convertArrayToJsonString(arrContactInfo, true);
                    if (contactTempInfo != null) {
                        loadTemporaryContactInfo(contactTempInfo);
                    } else {
                        Toast.makeText(
                                ContactInformation.this,
                                ApplicationSettings.translationSettings.GetTranslation("and_msg_errLoadingContact",
                                        "Could not load selected contact"), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }

                // txtExtraContact.setVisibility(View.GONE);

                // findViewById(R.id.lblAsterikOne).setVisibility(View.GONE);
                // findViewById(R.id.lblAsterikTwo).setVisibility(View.GONE);
                // findViewById(R.id.lblAsterikThree).setVisibility(View.GONE);

                LinearLayout tableLayout = (LinearLayout) contactFieldWrapper.getChildAt(contactFieldWrapper
                        .getChildCount() - 1);
                /*
                 * Button imgDelete = (Button)
                 * tableLayout.findViewById(R.id.delete);
                 * imgDelete.setVisibility(View.GONE);
                 */

                RelativeLayout rlDelete = (RelativeLayout) tableLayout.findViewById(R.id.layout_delete);

                rlDelete.setVisibility(View.GONE);

                llExtraContact.setVisibility(View.INVISIBLE);
                line.setVisibility(View.INVISIBLE);

                btnFinish.setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_save", "Save"));
                contactEmailAddressName.setEnabled(false);
                contactEmailAddressName.setTextColor(getResources().getColor(R.color.lauren));

                arrContactInfo.clear();
            } else {
                addView();
            }

        }

    }

    private void loadTemporaryContactInfo(String tempContactInfo) {

        try {
            JSONObject contactjson = new JSONObject(tempContactInfo);

            JSONArray contactJsonObj = contactjson.getJSONArray("Contacts");
            int size = contactJsonObj.length();
            for (int i = 0; i < size; i++) {
                addView();
            }
            for (int i = 0; i < contactFieldWrapper.getChildCount(); ++i) {
                fetchView(i);
                // for (int j = i; j <= i; j++) {
                JSONObject uniObject = contactJsonObj.getJSONObject(i);
                String fName = uniObject.getString("FirstName");
                String lName = uniObject.getString("LastName");
                String emailAddress = uniObject.getString("EmailAddress");

                // loop array
                JSONArray alertTypeArray = (JSONArray) uniObject.get("AlertTypes");
                for (int k = 0; k < alertTypeArray.length(); k++) {
                    if (alertTypeArray.get(k).toString().equalsIgnoreCase("5")) { // Inbraak
                        alertType[0].setChecked(true);

                    }
                    if (alertTypeArray.get(k).toString().equalsIgnoreCase("4")) { // Ongeval/EHBO
                        alertType[1].setChecked(true);
                    }

                    if (alertTypeArray.get(k).toString().equalsIgnoreCase("2")) { // Brand
                        alertType[2].setChecked(true);
                    }
                    if (alertTypeArray.get(k).toString().equalsIgnoreCase("3")) { // Aggressie
                        alertType[3].setChecked(true);
                    }
                }

                // }
                contactFirstName.setText(fName);
                contactLastName.setText(lName);
                contactEmailAddressName.setText(emailAddress);

            }

        } catch (Exception e) {
            PMWF_Log.fnlog(PMWF_Log.INFO, "ContactInformation::loadTemporaryContactInfo()",
                    PMWF_Log.getStringFromStackTrace(e));
        }

    }

    private void setUpView() {
        // txtExtraContact = (Button) findViewById(R.id.txtExtraContact);
        // txtExtraContact.setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_extraContact",
        // "Extra contact"));

        llExtraContact = (RelativeLayout) findViewById(R.id.txtExtraContact);

        TextView lblExtraContact = (TextView) findViewById(R.id.lblExtraContact);
        lblExtraContact.setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_extraContact",
                "Extra contact"));

        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_back", "Back"));

        btnFinish = (Button) findViewById(R.id.btnFinish);
        btnFinish.setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_finish", "Finish"));
        contactFieldWrapper = (LinearLayout) findViewById(R.id.tableWrapper);
        errorMsg = (TableRow) findViewById(R.id.rowErrorMsg);
        txtErrorMsg = (TextView) findViewById(R.id.txtErrorMsg);
    }

    public void fetchView(int i) {
        LinearLayout tableLayout = (LinearLayout) contactFieldWrapper.getChildAt(i);
        contactFirstName = (EditText) tableLayout.findViewById(R.id.contactFirstName);
        contactFirstName.addTextChangedListener(this);
        contactLastName = (EditText) tableLayout.findViewById(R.id.contactLastName);
        contactLastName.addTextChangedListener(this);
        contactEmailAddressName = (EditText) tableLayout.findViewById(R.id.contact_EmailAddress);// Check
                                                                                                 // what
                                                                                                 // is
                                                                                                 // contactEmailAddress
        contactEmailAddressName.addTextChangedListener(this);

        alertType[0] = (CheckBox) tableLayout.findViewById(R.id.chkAlertType1);
        alertType[1] = (CheckBox) tableLayout.findViewById(R.id.chkAlertType2);
        alertType[2] = (CheckBox) tableLayout.findViewById(R.id.chkAlertType3);
        alertType[3] = (CheckBox) tableLayout.findViewById(R.id.chkAlertType4);

        line = (LinearLayout) tableLayout.findViewById(R.id.line);

    }

    public void setBtnListener() {
        llExtraContact.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                addView();
            }
        });

        btnBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    ArrayList<UsersInfo> tempContactList = new ArrayList<UsersInfo>();
                    for (int i = 0; i < contactFieldWrapper.getChildCount(); ++i) {
                        fetchView(i);
                        String firstName = contactFirstName.getText().toString();
                        String lastName = contactLastName.getText().toString();
                        String emailAddress = contactEmailAddressName.getText().toString().trim();

                        UsersInfo contactInfo = setContactInfo(firstName, lastName, emailAddress, alertType);
                        if (contactInfo != null) {
                            tempContactList.add(contactInfo);
                        }
                    }

                    String tempContactJsonString = convertArrayToJsonString(tempContactList, true);
                    Intent intent = getIntent();
                    intent.putExtra("TEMP_CONTACT_INFO", tempContactJsonString != null ? tempContactJsonString : "");
                    setResult(RESULT_OK, intent);
                    finish();
                } catch (Exception ex) {
                    PMWF_Log.fnlog(PMWF_Log.ERROR, "ContactInformation: btnBack onClick()",
                            PMWF_Log.getStringFromStackTrace(ex));
                }
            }
        });

        btnFinish.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finishApp();
            }
        });

    }

    protected void finishApp() {
        boolean allFieldsValid = false;
        String usersInfo = "";

        try {
            if (contactFieldWrapper.getChildCount() > 0) { // if there is one
                                                           // view to add
                                                           // contact

                for (int i = 0; i < contactFieldWrapper.getChildCount(); ++i) {
                    fetchView(i);
                    if (validateFields(contactFirstName, contactLastName, contactEmailAddressName, alertType)) {
                        allFieldsValid = true;
                    } else {
                        allFieldsValid = false;
                        break;
                    }
                }

                if (allFieldsValid) {
                    for (int i = 0; i < contactFieldWrapper.getChildCount(); ++i) {
                        fetchView(i);
                        String firstName = contactFirstName.getText().toString();
                        if (firstName.length() <= 0) {
                            firstName = "";
                        }
                        String lastName = contactLastName.getText().toString();
                        String emailAddress = contactEmailAddressName.getText().toString().trim();

                        // check if the added account is mainuser himself
                        if (ApplicationSettingsFilePath.AccountName != null
                                && ApplicationSettingsFilePath.AccountName.length() > 0) {
                            if (emailAddress.equalsIgnoreCase(ApplicationSettingsFilePath.AccountName)) {
                                AlertMessage.Alert(ApplicationSettings.translationSettings.GetTranslation(
                                        "and_msg_cannotAddYourself", "You cannot add to yourself."),
                                        ContactInformation.this);
                                contactEmailAddressName.requestFocus();
                                return;
                            }
                            // check if the added account is mainuser
                            // himself on register
                            for (UsersInfo item : UsersInformation.tempUserInfoList) {
                                if (emailAddress.equalsIgnoreCase(item.getEmailAddress())) {
                                    AlertMessage.Alert(ApplicationSettings.translationSettings.GetTranslation(
                                            "and_msg_cannotAddYourself", "You cannot add to yourself."),
                                            ContactInformation.this);
                                    contactEmailAddressName.requestFocus();
                                    return;
                                }
                            }
                        }

                        // check if user added already previous account present

                        if (isAddContact) {

                            int fromUserId = ApplicationSettings.dbAccessor
                                    .getUserId(ApplicationSettingsFilePath.AccountName);

                            if (fromUserId > 0) {

                                ArrayList<ContactInfo> contactList = ApplicationSettings.dbAccessor
                                        .getContactsList(fromUserId);

                                if (contactList != null) {
                                    for (ContactInfo item : contactList) {

                                        if (item.getContactType().equalsIgnoreCase("Receiver")
                                                && emailAddress.equalsIgnoreCase(item.getContactEmailAddressName())) {
                                            AlertMessage.Alert(ApplicationSettings.translationSettings.GetTranslation(
                                                    "and_msg_contactAlreadyPresent", "Contact already added."),
                                                    ContactInformation.this);
                                            contactEmailAddressName.requestFocus();
                                            return;
                                        }
                                    }
                                }
                            }

                        }

                        UsersInfo contactInfo = setContactInfo(firstName, lastName, emailAddress, alertType);
                        if (contactInfo != null) {
                            arrContactInfo.add(contactInfo);
                        }

                    }

                    if (isAddContact) { // addContact

                        AddContact addContact = new AddContact();
                        if (addContact.getStatus() == AsyncTask.Status.FINISHED) {
                            addContact = new AddContact();
                        }

                        if (addContact.getStatus() != AsyncTask.Status.RUNNING) {
                            addContact.execute();
                        }
                    } else {
                        if (isEditContact) {
                            EditContact editContact = new EditContact();
                            if (editContact.getStatus() == AsyncTask.Status.FINISHED) {
                                editContact = new EditContact();
                            }

                            if (editContact.getStatus() != AsyncTask.Status.RUNNING) {
                                editContact.execute();
                            }
                        } else {
                            usersInfo = convertArrayToJsonString();
                            ApplicationSettings.dbAccessor.insertUserAndContactInfo(UsersInformation.tempUserInfoList,
                                    arrContactInfo); // insert
                                                     // one
                                                     // copy
                            // to deb
                            registerUser(usersInfo);
                        }

                    }

                }
                return;
            } else {

                if (isAddContact) {
                    // Intent intent = getIntent();
                    setResult(RESULT_OK);// , intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();

                } else {
                    /*
                     * if (isEditContact) {
                     * Toast.makeText(getApplicationContext(), "edit contact",
                     * Toast.LENGTH_SHORT).show(); EditContact editContact= new
                     * EditContact(); if (editContact.getStatus() ==
                     * AsyncTask.Status.FINISHED) { editContact= new
                     * EditContact(); }
                     * 
                     * if (editContact.getStatus() != AsyncTask.Status.RUNNING)
                     * { editContact.execute(); } } else {
                     */

                    /*
                     * usersInfo :
                     * {"EmailAddress":"f@b.com","Contacts":[],"FirstName"
                     * :"","PhoneNumber"
                     * :"5855666","LastName":"F","Password":"123456"}
                     */
                    usersInfo = convertArrayToJsonString();
                    ApplicationSettings.dbAccessor.insertUserAndContactInfo(UsersInformation.tempUserInfoList, null);
                    registerUser(usersInfo);
                    // }

                }

            }

        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "ContactInformation: finishApp()", PMWF_Log.getStringFromStackTrace(ex));
        }
    }

    class AddContact extends AsyncTask<String, String, String> {

        ProgressDialog dialog;
        String msg = "";
        String pushResponse = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(ContactInformation.this);
            String message = ApplicationSettings.translationSettings.GetTranslation("and_msg_addingContact",
                    "Adding Contact....");
            SpannableString ss1 = new SpannableString(message);
            ss1.setSpan(new RelativeSizeSpan(1f), 0, ss1.length(), 0);
            ss1.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.lauren)), 0, ss1.length(), 0);
            dialog.setMessage(ss1);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... arg0) {
            try {
                if (NetworkService.hasInternetConnection(ContactInformation.this)) {
                    /*
                     * addContactInfo :
                     * {"Contacts":[{"AlertTypes":["5","4","2","3"
                     * ],"EmailAddress"
                     * :"vb@a.com","LastName":"","FirstName":""}]}
                     */

                    String addContactInfo = convertArrayToJsonString(arrContactInfo, false);
                    JSONObject contactjson = new JSONObject(addContactInfo);
                    JSONArray contactJsonObj = contactjson.getJSONArray("Contacts");

                    pushResponse = WebServiceWrapper.Push(ApplicationSettingsFilePath.AuthenticationKey,
                            ApplicationSettingsFilePath.Type_Contact, contactJsonObj.toString());

                    if (pushResponse != null) {
                        msg = JSONHelper.getMessageResult("PushResult", "Message", pushResponse);
                        if (JSONHelper.getJsonObjectResult("PushResult", "IsSuccessfull", pushResponse)) {
                            ApplicationSettings.dbAccessor.insertUserAndContactInfo(null, arrContactInfo);
                            return "SUCCESS";
                        } else {
                            return "FAILED";
                        }
                    } else {
                        return ApplicationSettings.translationSettings.GetTranslation("and_msg_noInternetAccess",
                                "No internet access");
                    }
                } else {
                    return ApplicationSettings.translationSettings.GetTranslation("and_msg_noInternetAccess",
                            "No internet access");
                }
            } catch (Exception ex) {
                PMWF_Log.fnlog(PMWF_Log.ERROR, "ContactInformation: AddContact doInBackground()",
                        PMWF_Log.getStringFromStackTrace(ex));
            }
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                if (result.equalsIgnoreCase("SUCCESS")) {
                    String title = ApplicationSettings.translationSettings.GetTranslation("and_lbl_contactAdded",
                            "Contact Added");
                    SpannableString ss = new SpannableString(title);
                    ss.setSpan(new RelativeSizeSpan(1f), 0, ss.length(), 0);
                    ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.lauren)), 0, ss.length(), 0);
                    // AlertShow dialog= new AlertShow(getApplicationContext());
                    new AlertShow(ContactInformation.this).setTitle(ss).setMessage(msg)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Intent intent = getIntent();
                                    // setResult(RESULT_OK, intent);
                                    setResult(RESULT_OK);
                                    ContactInformation.this.finish();
                                }
                            }).setCancelable(false).show();
                } else if (result.equalsIgnoreCase("FAILED")) {
                    AlertMessage.Alert(msg, ContactInformation.this);
                } else {
                    AlertMessage.Alert(result, ContactInformation.this);
                }
                dialog.dismiss();
            } catch (Exception ex) {
                PMWF_Log.fnlog(PMWF_Log.ERROR, "ContactInformation: AddContact onPostExecute()",
                        PMWF_Log.getStringFromStackTrace(ex));
                finish();
            }

        }
    };

    class EditContact extends AsyncTask<String, String, String> {

        ProgressDialog dialog;
        String msg = "";

        // String pushResponse = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(ContactInformation.this);
            String message = ApplicationSettings.translationSettings.GetTranslation("and_msg_editingContact",
                    "Editing Contact...");
            dialog.setMessage(AlertMessage.setAlertHeaderColor(message, getApplicationContext()));
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... arg0) {
            try {

                if (NetworkService.hasInternetConnection(ContactInformation.this)) {
                    JSONArray editInfo = JSONHelper.editContactInfoToJson(arrContactInfo);

                    String guid = ApplicationSettings.dbAccessor.getGuidFromEmail(arrContactInfo.get(0)
                            .getEmailAddress());
                    ApplicationSettings.isWeberviceReachable = WebServiceWrapper.Ping();
                    if (ApplicationSettings.isWeberviceReachable) {

                        String editResponse = WebServiceWrapper.EditContact(
                                ApplicationSettingsFilePath.AuthenticationKey, guid, editInfo);
                        if (editResponse != null) {
                            msg = JSONHelper.getMessageResult("EditConnectionResult", "Message", editResponse);
                            if (JSONHelper.getJsonObjectResult("EditConnectionResult", "IsSuccessfull", editResponse)) {
                                ApplicationSettings.dbAccessor.insertUserAndContactInfo(null, arrContactInfo);
                                ApplicationSettings.dbAccessor.editUserConnections(editInfo, guid);
                                return "SUCCESS";
                            } else {
                                return "FAILED";
                            }
                        } else {
                            return ApplicationSettings.translationSettings.GetTranslation("and_msg_noInternetAccess",
                                    "No internet access");

                        }
                    }
                } else {
                    return ApplicationSettings.translationSettings.GetTranslation("and_msg_noInternetAccess",
                            "No internet access");
                }
            } catch (Exception ex) {
                PMWF_Log.fnlog(PMWF_Log.ERROR, "ContactInformation: editAsyncTask()",
                        PMWF_Log.getStringFromStackTrace(ex));
            }
            return ApplicationSettings.translationSettings.GetTranslation("and_msg_noInternetAccess",
                    "No internet access");
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                if (result.equalsIgnoreCase("SUCCESS")) {
                    String title = ApplicationSettings.translationSettings.GetTranslation("and_lbl_editContact",
                            "Edit contact");
                    new AlertShow(ContactInformation.this)
                            .setTitle(AlertMessage.setAlertHeaderColor(title, getApplicationContext())).setMessage(msg)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = getIntent();
                                    setResult(RESULT_OK, intent);
                                    finish();

                                }
                            }).setCancelable(false).show();
                } else if (result.equalsIgnoreCase("FAILED")) {
                    AlertMessage.Alert(msg, ContactInformation.this);
                } else {
                    arrContactInfo.clear();
                    AlertMessage.Alert(result, ContactInformation.this);

                }
                dialog.dismiss();
            } catch (Exception ex) {
                PMWF_Log.fnlog(PMWF_Log.ERROR, "ContactInformation: EditAsync onPostExecute()",
                        PMWF_Log.getStringFromStackTrace(ex));
                finish();
            }

        }
    };

    private void registerUser(String usersInfo) {
        final String usersInfoFinal = usersInfo;
        if (NetworkService.hasInternetConnection(ContactInformation.this)) {
            ApplicationSettings.isWeberviceReachable = WebServiceWrapper.Ping();
            if (ApplicationSettings.isWeberviceReachable) {

                new AsyncTask<String, Void, String>() {

                    ProgressDialog dialog;

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        dialog = new ProgressDialog(ContactInformation.this);
                        String message = ApplicationSettings.translationSettings.GetTranslation("and_msg_registering",
                                "Registering user....");
                        dialog.setMessage(AlertMessage.setAlertHeaderColor(message, getApplicationContext()));
                        dialog.setCancelable(false);
                        dialog.show();
                    }

                    @Override
                    protected String doInBackground(String... result) {
                        String pushResponse = WebServiceWrapper.RegisterUser(usersInfoFinal);
                        return pushResponse;
                    }

                    @Override
                    protected void onPostExecute(String result) {
                        super.onPostExecute(result);
                        Log.i("registeruser", result != null ? result : "Null response");
                        if (result != null) {
                            String msg = JSONHelper.getMessageResult("RegisterUserResult", "Message", result);

                            if (JSONHelper.getJsonObjectValueForRegister("IsSuccessfull", result)) {
                                String message = ApplicationSettings.translationSettings.GetTranslation(
                                        "and_lbl_register", "Register");

                                new AlertShow(ContactInformation.this)
                                        .setTitle(AlertMessage.setAlertHeaderColor(message, getApplicationContext()))
                                        .setMessage(msg).setPositiveButton("OK",

                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                try {
                                                    Intent intent = getIntent();
                                                    intent.putExtra("EXIT", true);
                                                    setResult(RESULT_OK, intent);
                                                    finish();
                                                    dialog.dismiss();
                                                } catch (Exception ex) {
                                                    PMWF_Log.fnlog(PMWF_Log.ERROR,
                                                            "ContactInformation: register onPostExecute()",
                                                            PMWF_Log.getStringFromStackTrace(ex));
                                                }
                                            }
                                        }).setCancelable(false).show();

                            } else {
                                String title = ApplicationSettings.translationSettings.GetTranslation(
                                        "and_lbl_register", "Register");
                                new AlertShow(ContactInformation.this)
                                        .setTitle(AlertMessage.setAlertHeaderColor(title, getApplicationContext()))
                                        .setMessage(msg).setPositiveButton("OK",

                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                try {
                                                    Intent intent = getIntent();
                                                    intent.putExtra("EXIT", false);
                                                    intent.putExtra("USER_EXIST", true);
                                                    setResult(RESULT_OK, intent);
                                                    finish();
                                                    dialog.dismiss();
                                                } catch (Exception ex) {
                                                    PMWF_Log.fnlog(PMWF_Log.ERROR,
                                                            "ContactInformation: register onPostExecute(2)",
                                                            PMWF_Log.getStringFromStackTrace(ex));
                                                }
                                            }
                                        }).setCancelable(false).show();
                            }
                        } else {
                            AlertMessage.Alert(ApplicationSettings.translationSettings.GetTranslation(
                                    "and_msg_noInternetAccess", "No internet access."), ContactInformation.this);
                        }
                    }

                }.execute();
            } else {
                AlertMessage.Alert(ApplicationSettings.translationSettings.GetTranslation(
                        "and_msg_couldnotAccessToServer", "Couldnot access the server."), ContactInformation.this);
            }
        } else {
            AlertMessage.Alert(ApplicationSettings.translationSettings.GetTranslation("and_msg_noInternetAccess",
                    "No internet access."), ContactInformation.this);
        }
    }

    protected boolean validated(EditText contactFirstName, EditText contactLastName, EditText contactEmailAddressName,
            CheckBox[] alerttype) {

        boolean validFields = false;
        // String toastMessage = "";

        if (Validate.hasText(contactLastName)) {
            if (Validate.hasText(contactFirstName)) {
                contactLastName.setBackgroundResource(R.drawable.edit_text_color);
                if (Validate.isEmailAddress(contactEmailAddressName, true)) {
                    contactEmailAddressName.setBackgroundResource(R.drawable.edit_text_color);
                    if (Validate.hasChecked(alerttype)) {
                        // toastMessage = "success";
                        errorMsg.setVisibility(View.GONE);
                        validFields = true;
                    } else {
                        // toastMessage = "At least choose one alert type.";
                        errorMsg.setVisibility(View.VISIBLE);
                        txtErrorMsg.setText(ApplicationSettings.translationSettings.GetTranslation(
                                "and_lbl_chooseAlertType", "At least choose one alert type"));

                    }
                } else {
                    // toastMessage = "Email is empty or incorrect.";
                    contactEmailAddressName.requestFocus();
                    // toastMessage = "Fill up the all fields.";
                    errorMsg.setVisibility(View.VISIBLE);
                    txtErrorMsg.setText(ApplicationSettings.translationSettings.GetTranslation(
                            "and_msg_emptyEmailOrIncorrect", "Email is empty or incorrect"));
                    contactEmailAddressName.setBackgroundResource(R.drawable.error_border);
                }
            } else {
                contactFirstName.requestFocus();
                contactFirstName.setBackgroundResource(R.drawable.error_border);
                // toastMessage = "Fill up the all fields.";
                errorMsg.setVisibility(View.VISIBLE);
                txtErrorMsg.setText(ApplicationSettings.translationSettings.GetTranslation(
                        "and_msg_requiredFieldsNotFilled", "Required fields are not filled"));

            }
        } else {
            // toastMessage = "Last name is empty.";
            contactLastName.requestFocus();
            contactLastName.setBackgroundResource(R.drawable.error_border);
            // toastMessage = "Fill up the all fields.";
            errorMsg.setVisibility(View.VISIBLE);
            txtErrorMsg.setText(ApplicationSettings.translationSettings.GetTranslation(
                    "and_msg_requiredFieldsNotFilled", "Required fields are not filled"));
        }

        if (validFields) {
            errorMsg.setVisibility(View.GONE);
            txtErrorMsg.setText("");
        }
        /*
         * if (toastMessage != "") { AlertMessage.Alert(toastMessage,
         * ContactInformation.this); }
         */
        return validFields;

    }

    protected UsersInfo setContactInfo(String firstName, String lastName, String emailAddress, CheckBox[] alertType) {
        try {
            UsersInfo tempUsersInfo = new UsersInfo();
            tempUsersInfo.setFirstName(firstName);
            tempUsersInfo.setLastName(lastName);
            tempUsersInfo.setEmailAddress(emailAddress);

            ArrayList<String> alertTypeList = new ArrayList<String>();

            for (int i = 0; i < alertType.length; i++) {

                if (alertType[i].isChecked()) {

                    String alertDesc = alertType[i].getText().toString();

                    String alertId = ApplicationSettings.getAlertTypeId(alertDesc);

                    if (!alertId.equals('0')) {
                        alertTypeList.add(alertId);
                    }

                    // alertTypeList.add(alertType[i].getText().toString());
                }
            }
            tempUsersInfo.setAlertType(alertTypeList);
            return tempUsersInfo;
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "ContactInformation: setContactInfo()", PMWF_Log.getStringFromStackTrace(ex));
        }
        return null;
    }

    public void addView() {

        LinearLayout llTableWrap = (LinearLayout) findViewById(R.id.tableWrapper);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.contact_templatee, llTableWrap);

        HideKeyboard.setupUI(findViewById(R.id.contactTemplateMainLayout), ContactInformation.this);

        LinearLayout tableLayout = (LinearLayout) llTableWrap.getChildAt(llTableWrap.getChildCount() - 1);

        TextView lblName = (TextView) tableLayout.findViewById(R.id.lblName);
        lblName.setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_name", "Name"));

        TextView lblEmail = (TextView) tableLayout.findViewById(R.id.lblEmailAddress);
        lblEmail.setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_email", "Email"));

        TextView lblChooseAlertType = (TextView) tableLayout.findViewById(R.id.lblChooseAlertType);
        lblChooseAlertType.setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_chooseAlertType",
                "Choose alert type"));

        EditText contactLastName = (EditText) tableLayout.findViewById(R.id.contactLastName);
        contactLastName.setHint(ApplicationSettings.translationSettings.GetTranslation("and_lbl_hintLastName",
                "Last name"));
        // contactLastName.requestFocus();

        EditText contactFirstName = (EditText) tableLayout.findViewById(R.id.contactFirstName);
        contactFirstName.setHint(ApplicationSettings.translationSettings.GetTranslation("and_lbl_hintFirstName",
                "First name"));
        contactFirstName.requestFocus();
        alertType[0] = (CheckBox) tableLayout.findViewById(R.id.chkAlertType1);
        alertType[0].setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_alertype_burglary",
                "Burglary")); // Inbraak

        alertType[1] = (CheckBox) tableLayout.findViewById(R.id.chkAlertType2);
        alertType[1].setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_alertype_accident",
                "Accident")); // Ongeval

        alertType[2] = (CheckBox) tableLayout.findViewById(R.id.chkAlertType3);
        alertType[2].setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_alertype_fire", "Fire"));// Brand

        alertType[3] = (CheckBox) tableLayout.findViewById(R.id.chkAlertType4);
        alertType[3].setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_alertype_aggression",
                "Aggression")); // Aggressie

        RelativeLayout rlDelete = (RelativeLayout) tableLayout.findViewById(R.id.layout_delete);

        TextView delButton = (TextView) rlDelete.findViewById(R.id.lblDelete);
        delButton.setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_delete", "Delete"));
        rlDelete.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                ViewGroup parentId = (ViewGroup) arg0.getParent();
                // Toast.makeText(getApplicationContext(),
                // parentId+"",Toast.LENGTH_SHORT).show();
                LinearLayout x = (LinearLayout) findViewById(R.id.tableWrapper);
                x.removeView(parentId);

                if (contactFieldWrapper.getChildCount() <= 0) {
                    if (isAddContact) {
                        btnFinish.setVisibility(View.INVISIBLE);
                    }
                    errorMsg.setVisibility(View.GONE);
                }

            }
        });

        if (isAddContact) {

            if (btnFinish.getVisibility() == View.INVISIBLE) {
                btnFinish.setVisibility(View.VISIBLE);
            }
        }

        for (int i = 0; i < alertType.length; i++) {
            alertType[i].setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (v != null && getApplicationContext() != null) {
                        InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(
                                Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }
            });
        }

    }

    public boolean validateFields(EditText firstName, EditText lastName, EditText emailAddress, CheckBox[] alertType) {
        boolean isAllFieldsEmpty = true;
        boolean isFieldsFilled = true;
        boolean validate = false;
        /*
         * if (Validate.hasText(firstName)) { isAllFieldsEmpty = false; } else {
         * 
         * isFieldsFilled = false; }
         */

        if (Validate.hasText(firstName)) {
            isAllFieldsEmpty = false;
            firstName.setBackgroundResource(R.drawable.edit_text_color);
        } else {
            isFieldsFilled = false;
            firstName.setBackgroundResource(R.drawable.error_border);
        }
        if (Validate.hasText(lastName)) {
            isAllFieldsEmpty = false;
            lastName.setBackgroundResource(R.drawable.edit_text_color);
        } else {
            isFieldsFilled = false;
            lastName.setBackgroundResource(R.drawable.error_border);
        }

        if (Validate.isEmailAddress(emailAddress, true)) {
            isAllFieldsEmpty = false;
            emailAddress.setBackgroundResource(R.drawable.edit_text_color);
        } else {
            isFieldsFilled = false;
            emailAddress.setBackgroundResource(R.drawable.error_border);
        }

        if (Validate.hasChecked(alertType)) {
            isAllFieldsEmpty = false;
        } else {
            isFieldsFilled = false;
            // errorMsg.setVisibility(View.VISIBLE);
            // txtErrorMsg.setText("At least choose one alert type");
        }

        if (isAllFieldsEmpty) {
            errorMsg.setVisibility(View.VISIBLE);
            txtErrorMsg.setText(ApplicationSettings.translationSettings.GetTranslation(
                    "and_msg_requiredFieldsNotFilled", "Required fields are not filled"));

            lastName.setBackgroundResource(R.drawable.error_border);
            emailAddress.setBackgroundResource(R.drawable.error_border);
            firstName.requestFocus();
            return false;
        }

        if (!isFieldsFilled) {
            errorMsg.setVisibility(View.VISIBLE);
            txtErrorMsg.setText(ApplicationSettings.translationSettings.GetTranslation(
                    "and_msg_requiredFieldsNotFilled", "Required fields are not filled"));
            validate = validated(firstName, lastName, emailAddress, alertType);
        } else {
            errorMsg.setVisibility(View.GONE);
            txtErrorMsg.setText("");
            validate = true;
        }

        // if (!isFieldsFilled && !isAllFieldsEmpty) { // validate if even one
        // // fields is filled or if
        // // all fields fill but
        // // incorrect email address
        //
        // }
        // else if (isFieldsFilled) { // all fields are validated
        // validate = validated(firstName, lastName, emailAddress, alertType);
        // validate = true;
        // errorMsg.setVisibility(View.GONE);
        // lastName.setBackgroundResource(R.drawable.edit_text_color);
        // emailAddress.setBackgroundResource(R.drawable.edit_text_color);
        //
        // }
        return validate;
    }

    protected String convertArrayToJsonString() {
        try {
            JSONObject usersInfoJsonObj = JSONHelper.usersListTOJsonArray(UsersInformation.tempUserInfoList,
                    arrContactInfo);
            return usersInfoJsonObj.toString();
            /*
             * JSONObject result = new JSONObject(); result.put("User",
             * usersInfoArray); return result.toString();
             */

        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "ContactInformation: convertArrayToJsonString()",
                    PMWF_Log.getStringFromStackTrace(ex));
        }
        return null;

    }

    protected String convertArrayToJsonString(ArrayList<UsersInfo> tempContactInfo, boolean isEditContact) {
        try {
            JSONArray usersInfoArray = JSONHelper.contactListTOJsonArray(tempContactInfo, isEditContact);
            JSONObject result = new JSONObject();
            result.put("Contacts", usersInfoArray);
            return result.toString();

        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "ContactInformation: convertArrayToJsonString(1)",
                    PMWF_Log.getStringFromStackTrace(ex));
        }
        return null;
    }

    @Override
    public void onBackPressed() {
        try {
            ArrayList<UsersInfo> tempContactList = new ArrayList<UsersInfo>();
            for (int i = 0; i < contactFieldWrapper.getChildCount(); ++i) {
                fetchView(i);
                String firstName = contactFirstName.getText().toString();
                String lastName = contactLastName.getText().toString();
                String emailAddress = contactEmailAddressName.getText().toString().trim();

                UsersInfo contactInfo = setContactInfo(firstName, lastName, emailAddress, alertType);
                tempContactList.add(contactInfo);
            }

            String tempContactJsonString = convertArrayToJsonString(tempContactList, true);
            Intent intent = getIntent();
            intent.putExtra("TEMP_CONTACT_INFO", tempContactJsonString != null ? tempContactJsonString : "");
            setResult(RESULT_OK, intent);
            finish();
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "ContactInformation: onBackPressed()", PMWF_Log.getStringFromStackTrace(ex));
        }
        super.onBackPressed();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if (s.length() > 0) {
            contactEmailAddressName.setTextColor(Color.BLACK);
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() > 0) {
            contactLastName.setBackgroundResource(R.drawable.edit_text_color);
        }

        if (contactEmailAddressName.getText().length() > 0) {
            contactEmailAddressName.setBackgroundResource(R.drawable.edit_text_color);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_CANCELED) {
            // Want to clear the activity stack so I should just go away now
            setResult(RESULT_CANCELED); // Propagate result to the previous
                                        // activity
            finish();
        }
    }

}
