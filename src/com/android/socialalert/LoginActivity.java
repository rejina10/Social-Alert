package com.android.socialalert;

import java.io.FileNotFoundException;

import org.json.JSONArray;
import org.json.JSONObject;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;

import com.android.socialalert.accountAuthenticator.AccountGeneral;
import com.android.socialalert.accountAuthenticator.UdinicAuthenticator;
import com.android.socialalert.common.AlertMessage;
import com.android.socialalert.common.NetworkService;
import com.android.socialalert.common.Validate;
import com.android.socialalert.json.JSONHelper;
import com.android.socialalert.logger.PMWF_Log;
import com.android.socialalert.settings.ApplicationSettings;
import com.android.socialalert.settings.ApplicationSettingsFilePath;
import com.android.socialalert.settings.WebServiceWrapper;
import com.android.socialalert.utilities.HideKeyboard;

public class LoginActivity extends SherlockFragmentActivity implements TextWatcher {

    /* AccountAuthenticatorActivity */
    private AccountAuthenticatorResponse mAccountAuthenticatorResponse = null;

    public static final int INTENT_REGISTER = 2;
    public static final int INTENT_MAINALERT = 6;

    public static final int INTENT_CHG_PSW = 5;
    public static final int INTENT_FORGOT_PSW = 15;

    public final static String ARG_ACCOUNT_NAME = "ACCOUNT_NAME";
    public final static String ARG_IS_ADDING_NEW_ACCOUNT = "IS_ADDING_ACCOUNT";

    public static final String KEY_ERROR_MESSAGE = "ERR_MSG";

    public final static String PARAM_USER_PASS = "USER_PASS";

    EditText txtEmail;
    EditText txtPassword;
    Button btnLogin;
    CheckBox chkRemember;
    TextView txtRemember;
    TextView txtRegister;
    TextView txtForgotPsw;
    // Context context;
    private AccountManager mAccountManager;
    private String mAuthTokenType;

    TextView lblEmail;
    TextView lblPsw;

    /**/

    private void initActionBar() {

        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.layout_login, null);
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.hide();
    }

    public final void setAccountAuthenticatorResult(Bundle resultResultBundle) {

        if (mAccountAuthenticatorResponse != null) {
            // send the result bundle back if set, otherwise send an error.
            if (resultResultBundle != null) {
                mAccountAuthenticatorResponse.onResult(resultResultBundle);
            } else {
                mAccountAuthenticatorResponse.onError(AccountManager.ERROR_CODE_CANCELED, "canceled");
            }
            mAccountAuthenticatorResponse = null;
        }
    }

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        /* AccountAuthenticatorActivity */
        mAccountAuthenticatorResponse = getIntent().getParcelableExtra(
                AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE);

        if (mAccountAuthenticatorResponse != null) {
            mAccountAuthenticatorResponse.onRequestContinued();
        }

        setContentView(R.layout.login);

        Display display = getWindowManager().getDefaultDisplay();
        int width = display.getWidth();

        ImageView alertViewAlertButton = (ImageView) findViewById(R.id.ImageLogin);
        // alertViewAlertButton.setBackgroundResource(R.drawable.imgalert);

        try {
            alertViewAlertButton.setImageBitmap(ShrinkBitmap(width));
        } catch (Exception e) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "LoginActivity: OnCreate()", "Error resizing Login image");
        }
        initActionBar();

        // context = this;
        // setCustomTitle();
        new UdinicAuthenticator(getApplicationContext());
        mAccountManager = AccountManager.get(this);
        mAuthTokenType = this.getIntent().getStringExtra(AutoUpdateActivity.ARG_AUTH_TYPE);
        if (mAuthTokenType == null) {
            mAuthTokenType = AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS;
        }
        setUpView();
        setViewListener();
    }

    private void setViewListener() {
        btnLogin.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                AsyncTask<String, String, String> asyncTask = new AsyncTask<String, String, String>() {

                    ProgressDialog dialog;

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        dialog = new ProgressDialog(LoginActivity.this);
                        String message = ApplicationSettings.translationSettings.GetTranslation("and_msg_loggin",
                                "Logging in....");
                        dialog.setMessage(AlertMessage.setAlertHeaderColor(message, getApplicationContext()));
                        dialog.setCancelable(false);
                        dialog.show();
                    }

                    @Override
                    protected String doInBackground(String... params) {

                        if (NetworkService.hasInternetConnection(getApplicationContext())) {
                            ApplicationSettings.isWeberviceReachable = WebServiceWrapper.Ping();
                            if (ApplicationSettings.isWeberviceReachable) {
                                String loginResponse = WebServiceWrapper.Login(txtEmail.getText().toString(),
                                        txtPassword.getText().toString());
                                if (loginResponse != null) {
                                    ApplicationSettingsFilePath.AuthenticationKey = JSONHelper
                                            .getAuthenticatioKey(loginResponse);

                                    if (ApplicationSettingsFilePath.AuthenticationKey != null
                                            && ApplicationSettingsFilePath.AuthenticationKey.length() > 0) {
                                        createAccount();
                                        try {

                                            int localId = Integer.valueOf(JSONHelper.getMessage("LocaleId",
                                                    loginResponse));
                                            if (localId != 0) {
                                                ApplicationSettingsFilePath.language = localId;
                                            }
                                        } catch (Exception e) {
                                            PMWF_Log.fnlog(PMWF_Log.ERROR, "Login response: Language setting",
                                                    PMWF_Log.getStringFromStackTrace(e));
                                        }

                                        String pullUsers = WebServiceWrapper.Pull(
                                                ApplicationSettingsFilePath.AuthenticationKey,
                                                ApplicationSettingsFilePath.Type_USERANDCONTACT);
                                        if (pullUsers != null) {
                                            if (JSONHelper
                                                    .getJsonObjectResult("PullResult", "IsSuccessfull", pullUsers)) {
                                                ApplicationSettings.dbAccessor.createUpdateUserAndContact(pullUsers);

                                                // pull contacts
                                                String pullContacts = WebServiceWrapper.Pull(
                                                        ApplicationSettingsFilePath.AuthenticationKey,
                                                        ApplicationSettingsFilePath.Type_Contact);
                                                if (pullContacts != null) {
                                                    if (JSONHelper.getJsonObjectResult("PullResult", "IsSuccessfull",
                                                            pullContacts)) {
                                                        String result = JSONHelper.getResult("Result", pullContacts);

                                                        if (result != null && !result.equalsIgnoreCase("null")) {
                                                            try {
                                                                JSONArray contactListArray = new JSONArray(result);

                                                                for (int i = 0; i < contactListArray.length(); i++) {
                                                                    try {
                                                                        JSONObject contact = contactListArray
                                                                                .getJSONObject(i);

                                                                        ApplicationSettings.dbAccessor
                                                                                .insertUpdateContact(contact.toString());
                                                                        ApplicationSettings.dbAccessor
                                                                                .insertUserConnections(contact
                                                                                        .toString());
                                                                    } catch (Exception ex) {
                                                                        PMWF_Log.fnlog(PMWF_Log.ERROR,
                                                                                "LoginActivity: Load Contact",
                                                                                PMWF_Log.getStringFromStackTrace(ex));
                                                                    }
                                                                }
                                                            } catch (Exception ex) {
                                                                PMWF_Log.fnlog(PMWF_Log.ERROR,
                                                                        "LoginActivity: Pull Contact List",
                                                                        PMWF_Log.getStringFromStackTrace(ex));
                                                            }
                                                        }
                                                    }
                                                }
                                                return "LOGIN_SUCCESS";
                                            } else {
                                                String msg = JSONHelper.getMessage("Message", pullUsers); // activation
                                                                                                          // failed.
                                                return msg;
                                            }
                                        } else {
                                            String msg = JSONHelper.getMessage("Message", loginResponse); // login
                                                                                                          // exist.
                                            return msg;
                                        }
                                    } else {
                                        String msg = JSONHelper.getMessage("Message", loginResponse); // login
                                                                                                      // exist.
                                        return msg;
                                    }
                                }
                            }
                        }
                        return ApplicationSettings.translationSettings.GetTranslation("and_msg_noInternetAccess",
                                "No internet access");
                    }

                    @Override
                    protected void onPostExecute(String result) {
                        super.onPostExecute(result);

                        try {
                            if (result != null && result.length() > 0) {
                                if (result.equals("LOGIN_SUCCESS")) {
                                    try {
                                        if (chkRemember.isChecked()) {
                                            ApplicationSettings.saveLoginInfo(
                                                    ((EditText) findViewById(R.id.login_EmailAddress)).getText()
                                                            .toString().trim().toLowerCase(),
                                                    ((TextView) findViewById(R.id.loginPassword)).getText().toString());
                                        } else {
                                            ApplicationSettings.saveLoginInfo(
                                                    ((EditText) findViewById(R.id.login_EmailAddress)).getText()
                                                            .toString().trim().toLowerCase(), "");
                                        }

                                        ApplicationSettings.setLanguageToSetting(ApplicationSettingsFilePath.language);
                                        ApplicationSettings.dbAccessor.getTranslation();

                                        setResult(RESULT_OK, getIntent());
                                        finish();

                                    } catch (Exception e) {
                                        Toast.makeText(getApplicationContext(), "Login ERROR!!", Toast.LENGTH_LONG)
                                                .show();
                                        PMWF_Log.fnlog(PMWF_Log.ERROR, "Login: onPostExecute()",
                                                PMWF_Log.getStringFromStackTrace(e));
                                    }
                                } else {
                                    AlertMessage.Alert(result, LoginActivity.this);
                                }
                            } else {
                                AlertMessage.Alert(ApplicationSettings.translationSettings.GetTranslation(
                                        "and_msg_noInternetAccess", "No internet access"), LoginActivity.this);
                            }
                            dialog.dismiss();
                        } catch (Exception e) {
                            PMWF_Log.fnlog(PMWF_Log.ERROR, "Login: onPostExecute(1)",
                                    PMWF_Log.getStringFromStackTrace(e));
                        }
                    }

                };

                boolean validUserLogin = Validate();

                if (validUserLogin) {
                    asyncTask.execute();
                }
            }
        });

        txtRegister.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (NetworkService.hasInternetConnection(getApplicationContext())) {

                    Intent registerUserIntent = new Intent(getApplicationContext(), UsersInformation.class);
                    startActivityForResult(registerUserIntent, INTENT_REGISTER);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                } else {
                    AlertMessage.Alert(ApplicationSettings.translationSettings.GetTranslation(
                            "and_msg_noInternetAccess", "No internet access"), LoginActivity.this);

                }
            }

        });

        txtForgotPsw.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                if (NetworkService.hasInternetConnection(getApplicationContext())) {

                    Intent forgotPswIntent = new Intent(getApplicationContext(), ForgetPassword.class);

                    startActivityForResult(forgotPswIntent, INTENT_FORGOT_PSW);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                } else {
                    AlertMessage.Alert(ApplicationSettings.translationSettings.GetTranslation(
                            "and_msg_noInternetAccess", "No internet access."), LoginActivity.this);
                }
            }

        });

    }

    private boolean Validate() {
        String toastMessage = "";
        if (Validate.isEmailAddress(txtEmail, true)) {
            if (Validate.hasText(txtPassword)) {
                return true;
            } else {
                toastMessage = ApplicationSettings.translationSettings.GetTranslation("and_msg_incorrectPassword",
                        "Incorrect password.");
                txtPassword.requestFocus();

            }
        } else {
            toastMessage = ApplicationSettings.translationSettings.GetTranslation("and_msg_invalidEmailAddress",
                    "Invalid email address");
            txtEmail.requestFocus();
        }

        if (toastMessage != "") {
            AlertMessage.Alert(toastMessage, LoginActivity.this);
        }
        return false;
    }

    public void setUpView() {
        txtEmail = (EditText) findViewById(R.id.login_EmailAddress);
        txtEmail.addTextChangedListener(this);
        txtPassword = (EditText) findViewById(R.id.loginPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        txtRegister = (TextView) findViewById(R.id.link_to_register);
        txtForgotPsw = (TextView) findViewById(R.id.forgotPassword);

        lblEmail = (TextView) findViewById(R.id.lblEmail);
        lblEmail.setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_email", "Email"));

        lblPsw = (TextView) findViewById(R.id.lblPassword);
        lblPsw.setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_password", "Password"));

        chkRemember = (CheckBox) findViewById(R.id.chkRememberMe);
        txtRemember = (TextView) findViewById(R.id.lblRememberMe);
        txtRemember
                .setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_rememberMe", "Remember me"));

        txtRegister.setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_notRegistered",
                "Click here if you are not registered"));
        txtForgotPsw.setText(ApplicationSettings.translationSettings.GetTranslation("and_lbl_forgotPassword",
                "Click here if you forgot your password"));

        Intent loginIntent = getIntent();
        String accountName = loginIntent.getStringExtra("ACCOUNT_NAME");
        String accountPass = loginIntent.getStringExtra("ACCOUNT_PASSWORD");

        if (accountName != null) {
            accountName = accountName.trim();
        }

        // if (accountPass != null) {
        // accountPass = accountPass;
        // }

        if ((accountName != null && accountName.length() > 0) && (accountPass != null && accountPass.length() > 0)) {
            txtEmail.setText(accountName);
            txtPassword.setText(accountPass);
            chkRemember.setChecked(true);
        } else if (accountName != null && accountName.length() > 0) {
            txtEmail.setText(accountName);
            txtPassword.setText("");
            chkRemember.setChecked(false);
        } else {
            txtEmail.setText("");
            txtPassword.setText("");
            chkRemember.setChecked(false);
        }

        HideKeyboard.setupUI(findViewById(R.id.loginMainLayout), LoginActivity.this);
    }

    @Override
    public void onBackPressed() {
        // ApplicationSettings.dbAccessor.close();
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // if (resultCode == RESULT_CANCELED) {
        // // Want to clear the activity stack so I should just go away now
        // setResult(RESULT_CANCELED); // Propagate result to the previous
        // // activity
        // finish();
        // }
        if (requestCode == INTENT_REGISTER) {

            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }

        if (requestCode == INTENT_FORGOT_PSW) {
            if (resultCode == RESULT_OK) {
                boolean isAppExit = data.getBooleanExtra("EXIT", false);
                if (isAppExit) {
                    Intent intent = getIntent();
                    intent.putExtra("EXIT", true);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
            }
        }
    }

    private void createAccount() {
        new AsyncTask<String, Void, Intent>() {
            String accountName = ((EditText) findViewById(R.id.login_EmailAddress)).getText().toString().trim()
                    .toLowerCase();
            String accountPassword = ((TextView) findViewById(R.id.loginPassword)).getText().toString();

            @Override
            protected Intent doInBackground(String... params) {
                // Log.d("SocialALert", TAG + "> Started authenticating");

                String authtoken = ApplicationSettingsFilePath.AuthenticationKey;

                Bundle data = new Bundle();
                try {
                    // authtoken = sServerAuthenticate.userSignUp(name,
                    // accountName, accountPassword,
                    // AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS);

                    data.putString(AccountManager.KEY_ACCOUNT_NAME, accountName);
                    data.putString(AccountManager.KEY_ACCOUNT_TYPE, AccountGeneral.ACCOUNT_TYPE);
                    data.putString(AccountManager.KEY_AUTHTOKEN, authtoken);
                    data.putBoolean(ARG_IS_ADDING_NEW_ACCOUNT, true);
                    data.putString(PARAM_USER_PASS, accountPassword);
                } catch (Exception ex) {
                    data.putString(KEY_ERROR_MESSAGE, ex.getMessage());
                    PMWF_Log.fnlog(PMWF_Log.ERROR, "SamenSafe()", PMWF_Log.getStringFromStackTrace(ex));
                }

                final Intent res = new Intent();
                res.putExtras(data);
                return res;
            }

            @Override
            protected void onPostExecute(Intent intent) {
                if (intent.hasExtra(KEY_ERROR_MESSAGE)) {
                    Toast.makeText(getBaseContext(), intent.getStringExtra(KEY_ERROR_MESSAGE), Toast.LENGTH_SHORT)
                            .show();

                } else {
                    finishLogin(intent);
                }
            }
        }.execute();
    }

    private void finishLogin(Intent intent) {
        // Log.d("udinic", TAG + "> finishLogin");

        try {
            String accountName = intent.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
            String accountPassword = intent.getStringExtra(PARAM_USER_PASS);
            final Account account = new Account(accountName, intent.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE));

            if (intent.getBooleanExtra(ARG_IS_ADDING_NEW_ACCOUNT, false)) {
                // Log.d("SocialALert", TAG +
                // "> finishLogin > addAccountExplicitly");
                String authtoken = intent.getStringExtra(AccountManager.KEY_AUTHTOKEN);
                String authtokenType = mAuthTokenType;

                // Creating the account on the device and setting the auth token
                // we got
                // (Not setting the auth token will cause another call to the
                // server to authenticate the user)
                mAccountManager.addAccountExplicitly(account, accountPassword, null);
                mAccountManager.setAuthToken(account, authtokenType, authtoken);
            } else {
                // Log.d("SocialALert", TAG + "> finishLogin > setPassword");
                mAccountManager.setPassword(account, accountPassword);
            }

            setAccountAuthenticatorResult(intent.getExtras());
            ApplicationSettingsFilePath.AccountName = accountName;

        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "LoginActivity::finishLogin()", PMWF_Log.getStringFromStackTrace(ex));
            Toast.makeText(LoginActivity.this, "Problem in settind device account", Toast.LENGTH_SHORT).show();
        }
    }

    private Bitmap ShrinkBitmap(int width) throws FileNotFoundException {

        BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
        bmpFactoryOptions.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.socialalert_logo, bmpFactoryOptions);
        int widthRatio = (int) android.util.FloatMath.ceil(bmpFactoryOptions.outWidth / (float) width);

        bmpFactoryOptions.inSampleSize = widthRatio;

        if (bmpFactoryOptions.inSampleSize <= 0) {
            bmpFactoryOptions.inSampleSize = 0;
        }
        bmpFactoryOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;
        bmpFactoryOptions.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.socialalert_logo, bmpFactoryOptions);
        return bitmap;

    }

    /*
     * @Override protected void onDestroy() { super.onDestroy();
     * Toast.makeText(getApplicationContext(), "onDes",
     * Toast.LENGTH_SHORT).show();
     * android.os.Process.killProcess(android.os.Process.myPid()); finish(); }
     */

    @Override
    public void afterTextChanged(Editable arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if (s.length() > 0) {
            txtEmail.setTextColor(Color.BLACK);
        }

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // TODO Auto-generated method stub

    }

    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id, Bundle args) {
        return super.onCreateDialog(id, args);
    }
}
