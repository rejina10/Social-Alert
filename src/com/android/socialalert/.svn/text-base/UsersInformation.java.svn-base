package com.android.socialalert;

import java.util.ArrayList;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

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

public class UsersInformation extends SherlockFragmentActivity implements TextWatcher, OnItemSelectedListener {

    public static final int INTENT_NEXTWIZARD = 3;

    EditText editUserFirstName;
    EditText editUserLastName;
    EditText editUserEmailAddress;
    EditText editUserPassword;
    EditText editConfirmPsw;
    EditText editUserAddress;
    EditText editUserPhoneNumber;

    EditText[] editTexts;

    TextView lblName, lblEmail, lblPassword, lblConfirmPassword, lblPhone, lblLangauge;

    Button btnNext;
    Button btnBack;
    TableRow errorMsg;
    TextView txtErrorMsg;
    Spinner langaugeSelect;

    String selectedLangauge;
    int selectLangaugeCode;
    public static ArrayList<UsersInfo> tempUserInfoList = new ArrayList<UsersInfo>();

    /** Called when the activity is first created. */

    private void initActionBar(String titlebarText) {

        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.custom_titlebar, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.titlebarText);
        mTitleTextView.setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_registerAccount",
                titlebarText));

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);

        HideKeyboard.setupUI(mCustomView.findViewById(R.id.custom_action_bar), UsersInformation.this); // set
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

        setContentView(R.layout.register_user);

        String title = ApplicationSettings.translationSettings.GetTranslation("and_lbl_registerAccount",
                "Register account");
        initActionBar(title);

        /*
         * final View activityRootView = findViewById(R.id.registerUserLayout);
         * activityRootView.getViewTreeObserver().addOnGlobalLayoutListener( new
         * OnGlobalLayoutListener() {
         * 
         * @Override public void onGlobalLayout() { Rect r = new Rect(); // r
         * will be populated with the coordinates of your view // that area
         * still visible. activityRootView.getWindowVisibleDisplayFrame(r);
         * 
         * int heightDiff = activityRootView.getRootView().getHeight() -
         * (r.bottom - r.top); if (heightDiff > 100) { // if more than 100
         * pixels, its // probably a keyboard...
         * //Toast.makeText(getApplicationContext(), "keyboard might be::" +
         * heightDiff, Toast.LENGTH_SHORT).show();
         * findViewById(R.id.bottomMenus).setVisibility(View.GONE);
         * //activityRootView.invalidate();
         * //getWindow().getDecorView().findViewById
         * (R.id.bottomMenus).invalidate(); } else {
         * //Toast.makeText(getApplicationContext(), "keyboard else::" +
         * heightDiff, Toast.LENGTH_SHORT).show();
         * findViewById(R.id.bottomMenus).setVisibility(View.VISIBLE);
         * //getWindow
         * ().getDecorView().findViewById(R.id.bottomMenus).invalidate();
         * //findViewById(R.id.bottomMenus).invalidate();
         * //activityRootView.invalidate(); } } });
         */
        setUpView();

        setBtnListener();
    }

    private void setBtnListener() {
        btnNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {

                try {
                    boolean validUser = getUserValidated();

                    if (validUser) {

                        user checkUser = new user();

                        // registerUser(userInfoRegister);
                        if (checkUser.getStatus() == AsyncTask.Status.FINISHED) {
                            checkUser = new user();
                        }

                        if (checkUser.getStatus() != AsyncTask.Status.RUNNING) {
                            checkUser.execute();
                            // registerUser(userInfoRegister);
                        }

                    }
                } catch (Exception ex) {
                    // Log.e("SocialAlertActivity()","BtnNextClick" +
                    // ex.getMessage());
                    PMWF_Log.fnlog(PMWF_Log.ERROR, "UserInformation::BtnNextClick()", ex.getMessage());
                }
            }

        });

        btnBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });
    }

    protected boolean getUserValidated() {
        boolean userValid = true;
        // String toastMessage = "";
        for (int i = 0; i < editTexts.length; i++) {
            if (Validate.hasText(editTexts[i])) {
                editTexts[i].setBackgroundResource(R.drawable.edit_text_color);
                errorMsg.setVisibility(View.GONE);
            } else {
                editTexts[i].setBackgroundResource(R.drawable.error_border);
                userValid = false;
            }

        }

        if (!userValid) {
            errorMsg.setVisibility(View.VISIBLE);
            txtErrorMsg.setText(ApplicationSettings.translationSettings.GetTranslation(
                    "and_msg_requiredFieldsNotFilled", "Required fields are not filled"));
            return false;
        }

        // if (!(editUserLastName.getText().length() > 0) ||
        // !(editUserFirstName.getText().length() > 0)
        // || !(editUserEmailAddress.getText().length() > 0) // ||
        // // !(editUserPhoneNumber.getText().length()
        // // > 0)
        // || (!(editUserPassword.getText().length() > 0) ||
        // !(editConfirmPsw.getText().length() > 0))) {
        //
        // errorMsg.setVisibility(View.VISIBLE);
        // txtErrorMsg.setText("");
        // txtErrorMsg.setText(ApplicationSettings.translationSettings.GetTranslation(
        // "and_msg_requiredFieldsNotFilled",
        // "Required fields are not filled"));
        // userValid = false;
        // return false;
        // // txtErrorMsg.setText("");
        // }
        // TODO removed repeated code
        // if (!(editUserFirstName.getText().length() > 0) ||
        // !(editUserEmailAddress.getText().length() > 0) // ||
        // // !(editUserPhoneNumber.getText().length()
        // // >
        // // 0)
        // || (!(editUserPassword.getText().length() > 0) ||
        // !(editConfirmPsw.getText().length() > 0))) {
        //
        // errorMsg.setVisibility(View.VISIBLE);
        // txtErrorMsg.setText("");
        // txtErrorMsg.setText(ApplicationSettings.translationSettings.GetTranslation(
        // "and_msg_requiredFieldsNotFilled",
        // "Required fields are not filled"));
        // userValid = false;
        // return false;
        // // txtErrorMsg.setText("");
        // }
        if (Validate.isEmailAddress(editUserEmailAddress, true)) {
            editUserEmailAddress.setBackgroundResource(R.drawable.edit_text_color);
            errorMsg.setVisibility(View.GONE);
        } else {
            errorMsg.setVisibility(View.VISIBLE);
            txtErrorMsg.setText(ApplicationSettings.translationSettings.GetTranslation("and_msg_invalidEmailAddress",
                    "Invalid email address"));
            editUserEmailAddress.setBackgroundResource(R.drawable.error_border);
            // userValid = false;
            return false;
        }

        if (editUserPassword.getText().length() < 6) {
            errorMsg.setVisibility(View.VISIBLE);
            editUserPassword.setBackgroundResource(R.drawable.error_border);
            txtErrorMsg.setText(ApplicationSettings.translationSettings.GetTranslation("and_msg_password_length",
                    "Password should be of minimum 6 characters."));
            return false;
        }

        if (editUserPhoneNumber.getText().length() > 15) {
            errorMsg.setVisibility(View.VISIBLE);
            editUserPhoneNumber.setBackgroundResource(R.drawable.error_border);
            txtErrorMsg.setText(ApplicationSettings.translationSettings.GetTranslation(
                    "and_msg_validation_phoneNumber", "Phone number field exceed the maximum length"));
            editUserPhoneNumber.setFocusable(true);
            return false;
        }
        /*
         * String[] result = editUserPassword.getText().toString().split("");
         * if(result.length!=0){ errorMsg.setVisibility(View.VISIBLE);
         * editUserPassword.setBackgroundResource(R.drawable.error_border);
         * txtErrorMsg.setText("Password shouldnot contain space characters.");
         * return false; }
         */

        if (Validate.matchPassword(editUserPassword, editConfirmPsw)) {
            editUserPassword.setBackgroundResource(R.drawable.edit_text_color);
            editConfirmPsw.setBackgroundResource(R.drawable.edit_text_color);
            errorMsg.setVisibility(View.GONE);
            // txtErrorMsg.setText("");
        } else {
            errorMsg.setVisibility(View.VISIBLE);
            editConfirmPsw.setBackgroundResource(R.drawable.error_border);
            txtErrorMsg.setText(ApplicationSettings.translationSettings.GetTranslation("and_msg_passwordDoesntMatch",
                    "Password doesn't match"));

            return false;
        }

        // if (toastMessage != "") {
        // AlertMessage.Alert(toastMessage, UsersInformation.this);
        // }

        // if (userValid) {
        // txtErrorMsg.setText("");
        // } else {
        // errorMsg.setVisibility(View.VISIBLE);
        // txtErrorMsg.setText("Required fields are not filled");
        // }
        return userValid;

    }

    private void setUpView() {

        txtErrorMsg = (TextView) findViewById(R.id.txtErrorMsg);
        errorMsg = (TableRow) findViewById(R.id.rowErrorMsg);
        editUserFirstName = (EditText) findViewById(R.id.userFirstName);
        editUserFirstName.addTextChangedListener(this);
        editUserLastName = (EditText) findViewById(R.id.userLastName);
        editUserLastName.addTextChangedListener(this);
        editUserEmailAddress = (EditText) findViewById(R.id.user_EmailAddress);
        editUserEmailAddress.addTextChangedListener(this);
        editUserPassword = (EditText) findViewById(R.id.userPassword);
        editUserPassword.addTextChangedListener(this);
        editConfirmPsw = (EditText) findViewById(R.id.confirmPassword);
        editConfirmPsw.addTextChangedListener(this);
        // editUserAddress = (EditText) findViewById(R.id.userAddress);
        editUserPhoneNumber = (EditText) findViewById(R.id.phoneNumber);
        editUserPhoneNumber.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                String text = editUserPhoneNumber.getText().toString();
                if (!text.startsWith("0") && text.length() > 0) {
                    editUserPhoneNumber.setText("0" + text);
                    // editUserPhoneNumber.setP
                    Selection.setSelection(editUserPhoneNumber.getText(), 2);
                }
            }
        });

        editTexts = new EditText[5];

        editTexts[0] = editUserLastName;
        editTexts[1] = editUserFirstName;
        editTexts[2] = editUserEmailAddress;
        editTexts[3] = editUserPassword;
        editTexts[4] = editConfirmPsw;
        // editTexts[4] = editUserPhoneNumber;
        lblLangauge = (TextView) findViewById(R.id.txtLangauge);
        lblLangauge.setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_selectLanguage",
                "Please select langauge"));
        langaugeSelect = (Spinner) findViewById(R.id.langaugeSelect);

        String[] country = getResources().getStringArray(R.array.language_array);
        String langaugedescription = "English";
        if (ApplicationSettingsFilePath.language == 1043) {
            langaugedescription = "English";
        } else {
            langaugedescription = "Dutch";
        }
        ArrayAdapter<String> ad = new ArrayAdapter<String>(UsersInformation.this,
                android.R.layout.simple_spinner_dropdown_item, country);
        int sp_position = ad.getPosition(langaugedescription);
        langaugeSelect.setAdapter(ad);
        langaugeSelect.setSelection(sp_position);
        langaugeSelect.setOnItemSelectedListener(this);

        btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_finish", "Finish"));

        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_back", "Back"));

        lblName = (TextView) findViewById(R.id.name);
        lblName.setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_name", "Name"));

        lblEmail = (TextView) findViewById(R.id.email);
        lblEmail.setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_email", "Email"));

        lblPassword = (TextView) findViewById(R.id.password);
        lblPassword.setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_password", "Password"));

        lblConfirmPassword = (TextView) findViewById(R.id.lblconfirmPassword);
        lblConfirmPassword.setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_confirmPassword",
                "Confirm password"));

        lblPhone = (TextView) findViewById(R.id.phone);
        lblPhone.setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_phone", "Phone"));

        editUserLastName.setHint(ApplicationSettings.translationSettings.GetTranslation("and_lbl_hintLastName",
                "Last name"));
        editUserFirstName.setHint(ApplicationSettings.translationSettings.GetTranslation("and_lbl_hintFirstName",
                "First name"));

        HideKeyboard.setupUI(findViewById(R.id.registerUserLayout), UsersInformation.this);
    }

    private UsersInfo getUsersInfo() {

        UsersInfo tempUsersInfo = new UsersInfo();
        tempUsersInfo.setFirstName(editUserFirstName.getText().toString().trim());
        tempUsersInfo.setLastName(editUserLastName.getText().toString().trim());
        tempUsersInfo.setEmailAddress(editUserEmailAddress.getText().toString().toLowerCase().trim());
        tempUsersInfo.setPassword(editUserPassword.getText().toString().trim());
        if (selectedLangauge.equals("English")) {
            selectLangaugeCode = 1043;
        } else {
            selectLangaugeCode = 1045;
        }
        tempUsersInfo.setSelectedLangauge(selectLangaugeCode);
        // tempUsersInfo.setAddress(editUserAddress.getText().toString().trim());

        /*
         * if(editUserPhoneNumber.getText().toString().length()==0){
         * tempUsersInfo.setPhoneNumber(""); }else{
         */
        tempUsersInfo.setPhoneNumber(editUserPhoneNumber.getText().toString());
        // }

        return tempUsersInfo;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == INTENT_NEXTWIZARD) {
            if (resultCode == RESULT_OK) {
                boolean isAppExit = data.getBooleanExtra("EXIT", false);
                boolean isUserExist = data.getBooleanExtra("USER_EXIST", false);
                String tempContactInfo = data.getStringExtra("TEMP_CONTACT_INFO");
                if (isAppExit) {
                    Intent intent = getIntent();
                    intent.putExtra("EXIT", true);
                    setResult(RESULT_OK, intent);
                    finish();
                } else if (isUserExist) {
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    tempUserInfoList.clear();
                    editUserEmailAddress.setText("");
                    editUserEmailAddress.requestFocus();
                    PMWF_Log.fnlog(PMWF_Log.INFO, "UsersInformation::onActivityResult()", tempContactInfo);
                } else {
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    tempUserInfoList.clear();
                    tempContactInfo = data.getStringExtra("TEMP_CONTACT_INFO");
                }
            }
        }

    }

    @Override
    public void onBackPressed() {
        /*
         * Intent intent = getIntent(); intent.putExtra("EXIT", false);
         * setResult(RESULT_OK, intent);
         * overridePendingTransition(R.anim.slide_in_left,
         * R.anim.slide_out_right); finish();
         */
        // View activityRootView = findViewById(R.id.registerUserLayout);
        // if (activityRootView != null && getApplicationContext() != null) {
        // InputMethodManager imm = (InputMethodManager) getApplicationContext()
        // .getSystemService(Context.INPUT_METHOD_SERVICE);
        // imm.hideSoftInputFromWindow(
        // activityRootView.getApplicationWindowToken(),
        // InputMethodManager.HIDE_NOT_ALWAYS);
        // }
        // overridePendingTransition(R.anim.slide_in_right,
        // R.anim.slide_out_left);
        finish();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // TODO Auto-generated method stub
        if (s.length() > 0) {
            editUserEmailAddress.setTextColor(Color.BLACK);
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // TODO Auto-generated method stub
        try {
            if (s.length() > 0) {
                editUserEmailAddress.setTextColor(Color.BLACK);
                /*
                 * Toast.makeText(getApplicationContext(), "OnTextChanged",
                 * Toast.LENGTH_SHORT).show();
                 */

                // for (int i = 0; i < editTexts.length; i++) {
                // if (editTexts[i].getText().length() > 0) {
                // editTexts[i].setBackgroundResource(R.drawable.edit_text_color);
                //
                // }
                //
                // }
                /*
                 * if (editUserEmailAddress.getText().length() > 0) {
                 * editUserEmailAddress
                 * .setBackgroundResource(R.drawable.edit_text_color);
                 * editUserEmailAddress.setTextColor(Color.BLACK); }
                 * 
                 * if (Validate.isEmailAddress(editUserEmailAddress, true)) {
                 * editUserEmailAddress
                 * .setBackgroundResource(R.drawable.edit_text_color);
                 * errorMsg.setVisibility(View.GONE); }
                 */

                /*
                 * if(editUserPassword.getText().length() < 6 ){
                 * editUserPassword
                 * .setBackgroundResource(R.drawable.error_border);
                 * txtErrorMsg.setText("Password should be 6 chars."); return; }
                 */

                if (Validate.matchPassword(editUserPassword, editConfirmPsw)) {
                    editUserPassword.setBackgroundResource(R.drawable.edit_text_color);
                    editConfirmPsw.setBackgroundResource(R.drawable.edit_text_color);
                    errorMsg.setVisibility(View.GONE);
                    // txtErrorMsg.setText("");

                    txtErrorMsg.setText("");
                }
            }
        } catch (Exception e) {
            Log.e("Error in textWatcher OnTextChanged", e.toString());
        }
        // getUserValidated();

    }

    class user extends AsyncTask<String, String, String> {

        ProgressDialog dialog;
        String msg;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(UsersInformation.this);
            String message = ApplicationSettings.translationSettings.GetTranslation("and_msg_checkingUser",
                    "Checking user....");
            AlertMessage.setAlertHeaderColor(message, getApplicationContext());
            dialog.setCancelable(false);
            dialog.setMessage(AlertMessage.setAlertHeaderColor(message, getApplicationContext()));
            dialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            if (NetworkService.hasInternetConnection(getApplicationContext())) {
                String userResponse = WebServiceWrapper.CheckUser(editUserEmailAddress.getText().toString().trim());
                if (userResponse != null) {
                    msg = JSONHelper.getMessageResult("CheckUserResult", "Message", userResponse);
                    if (JSONHelper.getJsonObjectResult("CheckUserResult", "IsSuccessfull", userResponse)) {
                        return "FAILED"; // user already exist
                    } else {
                        return "SUCCESS";
                    }
                } else {
                    return ApplicationSettings.translationSettings.GetTranslation("and_msg_noInternetAccess",
                            "No internet access");
                }
            } else {
                return ApplicationSettings.translationSettings.GetTranslation("and_msg_noInternetAccess",
                        "No internet access");
            }

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                if (result != null && result.length() > 0) {
                    if (result.equalsIgnoreCase("FAILED")) {
                        AlertMessage.Alert(msg, UsersInformation.this);
                        editUserEmailAddress.requestFocus();
                    } else if (result.equalsIgnoreCase("SUCCESS")) {
                        if (tempUserInfoList != null) {
                            tempUserInfoList.clear();
                        }
                        UsersInfo usersInfo = getUsersInfo();
                        tempUserInfoList.add(usersInfo);
                        String userInfoRegister = convertArrayToJsonString();
                        registerUser(userInfoRegister != null ? userInfoRegister : "");
                        // String pushResponse =
                        // WebServiceWrapper.RegisterUser(userInfoRegister);

                        // Intent contactInfo = new
                        // Intent(getApplicationContext(),ContactInformation.class);
                        // contactInfo.putExtra("TEMP_CONTACT_INFO",tempContactInfo);
                        // startActivityForResult(contactInfo,
                        // INTENT_NEXTWIZARD);
                        // overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                    } else {
                        AlertMessage.Alert(result, UsersInformation.this);
                    }
                }
                dialog.dismiss();
            } catch (Exception ex) {
                PMWF_Log.fnlog(PMWF_Log.ERROR, "UsersInformation: user onPostExecute()",
                        PMWF_Log.getStringFromStackTrace(ex));
            }
        }

    }

    protected String convertArrayToJsonString() {
        try {
            JSONObject usersInfoJsonObj = JSONHelper.usersListTOJsonArray(tempUserInfoList, null);
            return usersInfoJsonObj.toString();
            /*
             * JSONObject result = new JSONObject(); result.put("User",
             * usersInfoArray); return result.toString();
             */

        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "convertArrayToJsonString()", ex.getMessage());
        }
        return null;

    }

    private void registerUser(String usersInfo) {
        final String usersInfoFinal = usersInfo;
        if (NetworkService.hasInternetConnection(getApplicationContext())) {
            ApplicationSettings.isWeberviceReachable = WebServiceWrapper.Ping();
            if (ApplicationSettings.isWeberviceReachable) {

                new AsyncTask<String, Void, String>() {

                    ProgressDialog dialog;

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        dialog = new ProgressDialog(UsersInformation.this);
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

                        try {
                            if (dialog != null) {
                                dialog.dismiss();
                            }
                        } catch (Exception ex) {

                        }
                        try {
                            Log.i("registeruser", result != null ? result : "Null response");
                            if (result != null) {
                                String msg = JSONHelper.getMessageResult("RegisterUserResult", "Message", result);

                                if (JSONHelper.getJsonObjectValueForRegister("IsSuccessfull", result)) {
                                    String message = ApplicationSettings.translationSettings.GetTranslation(
                                            "and_lbl_register", "Register");
                                    new AlertShow(UsersInformation.this)
                                            .setTitle(
                                                    AlertMessage.setAlertHeaderColor(message, getApplicationContext()))
                                            .setMessage(msg).setPositiveButton("OK",

                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    finish();
                                                }
                                            }).setCancelable(false).show();

                                } else {
                                    String title = ApplicationSettings.translationSettings.GetTranslation(
                                            "and_lbl_register", "Register");
                                    new AlertShow(UsersInformation.this)
                                            .setTitle(AlertMessage.setAlertHeaderColor(title, getApplicationContext()))
                                            .setMessage(msg).setPositiveButton("OK",

                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    finish();
                                                }
                                            }).setCancelable(false).show();
                                }
                            } else {
                                AlertMessage.Alert(ApplicationSettings.translationSettings.GetTranslation(
                                        "and_msg_noInternetAccess", "No internet access."), UsersInformation.this);
                            }
                        } catch (Exception ex) {
                            PMWF_Log.fnlog(PMWF_Log.ERROR, "UsersInformation: register onPostExecute()",
                                    PMWF_Log.getStringFromStackTrace(ex));
                            finish();
                        }
                    }

                }.execute();
            } else {
                AlertMessage.Alert(ApplicationSettings.translationSettings.GetTranslation(
                        "and_msg_couldnotAccessToServer", "Couldnot access the server."), UsersInformation.this);
            }
        } else {
            AlertMessage.Alert(ApplicationSettings.translationSettings.GetTranslation("and_msg_noInternetAccess",
                    "No internet access."), UsersInformation.this);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long arg3) {
        // TODO Auto-generated method stub
        selectedLangauge = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

}
