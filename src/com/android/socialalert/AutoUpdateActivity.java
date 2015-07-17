package com.android.socialalert;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.android.socialalert.accountAuthenticator.AccountGeneral;
import com.android.socialalert.common.AlertMessage;
import com.android.socialalert.common.NetworkService;
import com.android.socialalert.database.AccountInfo;
import com.android.socialalert.json.JSONHelper;
import com.android.socialalert.logger.PMWF_Log;
import com.android.socialalert.settings.ApplicationSettings;
import com.android.socialalert.settings.ApplicationSettingsFilePath;
import com.android.socialalert.settings.WebServiceWrapper;

public class AutoUpdateActivity extends Activity {

    private static final int INTENT_HOME_ACTIVITY_SHAKE = 1001;
    private static final int INTENT_SELECTLANGUAGE = 1004;
    private static final int INTENT_LOGIN = 1005;
    private static final int INTENT_HOME_ACTIVITY = 1006;

    public final static String ARG_ACCOUNT_TYPE = "ACCOUNT_TYPE";
    public final static String ARG_AUTH_TYPE = "AUTH_TYPE";
    private AccountManager mAccountManager;

    private ProgressDialog progress_dialog;
    boolean isSettingDownload = true;

    // AccountInfo accountInfo;
    // ArrayList<AccountInfo> accountInfoList;

    public void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);

            requestWindowFeature(Window.FEATURE_NO_TITLE);
            mAccountManager = AccountManager.get(this);
            ApplicationSettings.CreateApplicationFolder();
            PMWF_Log.InitializeLogSettings(ApplicationSettingsFilePath.logPath);
            ApplicationSettings.initDBAccessor(getApplicationContext(), ApplicationSettings.APP_DATABASE_PATH);
            WebServiceWrapper.initializeWebServiceWrapper(ApplicationSettingsFilePath.webservice_url);

            String mAuthTokenType = this.getIntent().getStringExtra(ARG_AUTH_TYPE);
            if (mAuthTokenType == null) {
                mAuthTokenType = AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS;
            }

            // check if account is previously available
            boolean isAccountAvail = getAccounts(mAuthTokenType);

            startApplication(isAccountAvail);
        } catch (Exception ex) {

            PMWF_Log.fnlog(PMWF_Log.ERROR, "AutoUpdateActivity::initiateApplication(2)",
                    PMWF_Log.getStringFromStackTrace(ex));

            Toast.makeText(getApplicationContext(), "Failed initializing application ", Toast.LENGTH_LONG).show();
            finish();

        }
    }

    public void pullSetting() {

        try {
            progress_dialog = new ProgressDialog(AutoUpdateActivity.this);
            progress_dialog.setMessage(AlertMessage.setAlertHeaderColor(ApplicationSettings.translationSettings
                    .GetTranslation("and_msg_downloadingSetting", "Downloading settings..."), AutoUpdateActivity.this));
            progress_dialog.setCancelable(false);
            progress_dialog.show();

            Runnable runnable_autoupdate = new Runnable() {

                @Override
                public void run() {
                    try {

                        if (NetworkService.hasInternetConnection(AutoUpdateActivity.this)) {

                            ApplicationSettings.isWeberviceReachable = WebServiceWrapper.Ping();
                            if (ApplicationSettings.isWeberviceReachable) {
                                String translationResponse = WebServiceWrapper.PullSetting("Translation");
                                // Log.i("translationValue",
                                // translationResponse);
                                if (translationResponse != null) {

                                    if (JSONHelper.getJsonObjectResult("PullSettingResult", "IsSuccessfull",
                                            translationResponse)) {

                                        String result = JSONHelper.getJsonResult("PullSettingResult", "Result",
                                                translationResponse);

                                        ApplicationSettings.dbAccessor.insertTranslations(result);
                                    }
                                } else {
                                    isSettingDownload = false;
                                }

                                String settingsResponse = WebServiceWrapper
                                        .PullSetting(ApplicationSettingsFilePath.Type_AlertTypes);
                                // Log.i("settingsResponse::",
                                // settingsResponse);
                                if (settingsResponse != null
                                        && JSONHelper.getJsonObjectValue("IsSuccessfull", settingsResponse)) {
                                    ApplicationSettings.dbAccessor.insertAlertSetting(settingsResponse);

                                } else {
                                    isSettingDownload = false;
                                }

                                String settingAlertStatusResponse = WebServiceWrapper
                                        .PullSetting(ApplicationSettingsFilePath.Type_AlertStatuses);
                                if (settingAlertStatusResponse != null
                                        && JSONHelper.getJsonObjectValue("IsSuccessfull", settingAlertStatusResponse)) {
                                    ApplicationSettings.dbAccessor.insertAlertStatusSetting(settingAlertStatusResponse);

                                } else {
                                    isSettingDownload = false;
                                }

                                String userStatusResponse = WebServiceWrapper
                                        .PullSetting(ApplicationSettingsFilePath.Type_UserStatuses);
                                if (userStatusResponse != null
                                        && JSONHelper.getJsonObjectValue("IsSuccessfull", userStatusResponse)) {
                                    ApplicationSettings.dbAccessor.insertStatus(userStatusResponse);
                                } else {
                                    isSettingDownload = false;

                                }
                            } else {
                                isSettingDownload = false;
                            }
                        } else {
                            isSettingDownload = false;
                        }
                    } catch (Exception ex) {
                        PMWF_Log.fnlog(PMWF_Log.ERROR, "pullSetting(0)",
                                "Exception!!! " + PMWF_Log.getStringFromStackTrace(ex));
                        isSettingDownload = false;

                    } catch (Error err) {
                        PMWF_Log.fnlog(PMWF_Log.ERROR, "pullSetting(0)", "Error!!! " + PMWF_Log.getStringFromError(err));
                        isSettingDownload = false;

                    }
                    progress_dialog.dismiss();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (isSettingDownload) {
                                    Intent loginIntent = new Intent(AutoUpdateActivity.this, SelectLanguage.class);
                                    startActivityForResult(loginIntent, INTENT_SELECTLANGUAGE); // Result
                                                                                                // Cancelled
                                } else {
                                    AlertShow builder = new AlertShow(AutoUpdateActivity.this);
                                    builder.setCancelable(false);
                                    builder.setTitle(AlertMessage.setAlertHeaderColor("Alert", AutoUpdateActivity.this));
                                    builder.setMessage("Either no internet access or could not access to server.");
                                    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            ApplicationSettings.closeDBAccessor();
                                            setResult(RESULT_CANCELED, getIntent());
                                            finish();
                                        }
                                    });
                                    builder.show();
                                }
                            } catch (Exception ex) {

                                PMWF_Log.fnlog(PMWF_Log.ERROR, "pullSetting()", PMWF_Log.getStringFromStackTrace(ex));
                                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                                ApplicationSettings.closeDBAccessor();
                                finish();
                            }
                        }
                    });

                }

            };

            Thread autoUpdateThread = new Thread(null, runnable_autoupdate, "AutoUpdateThread");
            autoUpdateThread.start();

        } catch (Exception e) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "pullSetting()", PMWF_Log.getStringFromStackTrace(e));
            finish();
        }

    }

    private void startApplication(boolean isAccountAvail) throws Exception {

        try {
            boolean isLanguageSet = ApplicationSettings.isLanguageSet();

            if (!isAccountAvail) {
                if (isLanguageSet) { // if true get translation text to hashmap
                                     // according to the language store in
                                     // sqlite setting table
                    ApplicationSettings.dbAccessor.getTranslation();
                    // accountInfoList = new ArrayList<AccountInfo>();
                    // accountInfo = new AccountInfo();
                    AccountInfo accountInfo = ApplicationSettings.dbAccessor.getAccountName();

                    String accountName = "";
                    String password = "";
                    if (accountInfo != null) {
                        accountName = accountInfo.getEmailaddress();
                        password = accountInfo.getPassword();
                    }
                    Intent loginIntent = new Intent(this, LoginActivity.class);
                    loginIntent.putExtra("ACCOUNT_NAME", accountName);
                    loginIntent.putExtra("ACCOUNT_PASSWORD", password);
                    startActivityForResult(loginIntent, INTENT_LOGIN);

                } else {
                    pullSetting(); // pull all required setting from database
                                   // server that is translation, alerttype,
                                   // alertstatuses
                }

            } else {
                Intent i = getIntent(); // get intent from shakelistener class
                                        // if device is shake outside
                                        // application
                boolean isShakeEvent = i != null ? i.getBooleanExtra("Shake", false) : false;
                /*
                 * if(isShakeEvent){ Intent alertIntent = new Intent(this,
                 * AlertActivity.class);
                 * alertIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                 * Intent.FLAG_ACTIVITY_NEW_TASK); startActivity(alertIntent);
                 * 
                 * }else{
                 */
                ApplicationSettings.dbAccessor.getTranslation();
                Log.i("Autoupdateactivity::", isShakeEvent + "");
                Intent homePageIntent = new Intent(this, HomePageActivity.class);// ListViewActivity
                homePageIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                homePageIntent.putExtra("Shake", isShakeEvent);
                startActivityForResult(homePageIntent, INTENT_HOME_ACTIVITY_SHAKE);
                // }
            }
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "AutoUpdateActivity::initiateApplication(2)", ex.getMessage());
            throw ex;
        }
    }

    protected boolean getAccounts(final String authTokenType) {

        try {
            final Account availableAccounts[] = mAccountManager.getAccountsByType(AccountGeneral.ACCOUNT_TYPE);

            if (availableAccounts.length != 0) {
                ApplicationSettingsFilePath.AuthenticationKey = null;
                getExistingAccountAuthToken(availableAccounts[0], authTokenType);
                if (ApplicationSettingsFilePath.AuthenticationKey != null
                        && ApplicationSettingsFilePath.AuthenticationKey.length() > 0) {
                    ApplicationSettingsFilePath.AccountName = availableAccounts[0].name;
                    return true;
                } else {
                    ApplicationSettingsFilePath.AccountName = null;
                    return false;
                }

            }

        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "AutoUpdateActivity::getAccounts()", PMWF_Log.getStringFromStackTrace(ex));

        }
        return false;
    }

    private void getExistingAccountAuthToken(final Account account, final String authTokenType) {
        try {
            final AccountManagerFuture<Bundle> future = mAccountManager.getAuthToken(account, authTokenType, null,
                    this, null, null);
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Bundle bnd = future.getResult();
                            final String authtoken = bnd.getString(AccountManager.KEY_AUTHTOKEN);
                            // Log.i("auth", authtoken);
                            ApplicationSettingsFilePath.AuthenticationKey = authtoken;
                            // Log.i("authKey",
                            // ApplicationSettingsFilePath.AuthenticationKey);

                        } catch (Exception ex) {
                            PMWF_Log.fnlog(PMWF_Log.ERROR, "AutoUpdateActivity::getExistingAccountAuthToken()",
                                    PMWF_Log.getStringFromStackTrace(ex));
                        }
                    }
                });
                thread.start();
                
                try{
                    Thread.sleep(500);
                }catch(Exception e){
                    
                }
               /* try {
                    thread.wait(0);
                } catch (Exception e) {
                    PMWF_Log.fnlog(PMWF_Log.ERROR,
                            "AutoUpdateActivity -Checking Wait thread-getExistingAccountAuthToken",
                            "wait needs object locked");

                }
            */

            // return ApplicationSettingsFilePath.AuthenticationKey;
        } catch (Exception e) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "getExistingAccountAuthToken()", PMWF_Log.getStringFromStackTrace(e));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        try {
            if (requestCode == INTENT_HOME_ACTIVITY_SHAKE && resultCode == RESULT_CANCELED) {
                setResult(RESULT_OK);
                finish();
            } else if (requestCode == INTENT_SELECTLANGUAGE) {
                if (resultCode == RESULT_OK) {
                    Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                    // loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                    // Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivityForResult(loginIntent, INTENT_LOGIN);
                } else {
                    Toast.makeText(AutoUpdateActivity.this, "Language not selected", Toast.LENGTH_LONG).show();
                    finish();
                }

            } else if (requestCode == INTENT_LOGIN) {

                if (resultCode == RESULT_OK) {
                    boolean isAppExit = data.getBooleanExtra("EXIT", false);
                    if (isAppExit) {
                        finish();
                    } else {
                        // Start Home activity
                        Intent loginUserIntent = new Intent(getApplicationContext(), HomePageActivity.class);
                        startActivityForResult(loginUserIntent, INTENT_HOME_ACTIVITY);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                } else {
                    finish();
                }

            } else if (requestCode == INTENT_HOME_ACTIVITY) {
                // && resultCode == RESULT_CANCELED
                finish();
                // android.os.Process.killProcess(android.os.Process.myPid());
            }
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "AutoUpdateActivity::onActivityResult(2)",
                    PMWF_Log.getStringFromStackTrace(ex));
            finish();
        }
    }
}
