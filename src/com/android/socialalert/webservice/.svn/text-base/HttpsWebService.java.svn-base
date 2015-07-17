package com.android.socialalert.webservice;

import java.net.UnknownHostException;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import com.android.socialalert.logger.PMWF_Log;
import com.android.socialalert.settings.ApplicationSettingsFilePath;

public class HttpsWebService {
    private static final String WEBSERVICE_PING = "Ping";
    private static final String WEBSERVICE_REGISTER = "Register";
    private static final String WEBSERVICE_LOGIN = "Login";
    private static final String WEBSERVICE_PUSH = "Push";
    private static final String WEBSERVICE_PULL = "Pull";
    private static final String RESET_PASSWORD = "ResetPassword";
    private static final String FORGOT_PASSWORD = "ForgotPassword";
    private static final String EDIT_CONTACT = "EditConnection";
    private static final String EDIT_LANGAGUE = "EditLanguage";
    private static final String DELETE_CONTACT = "DeleteContact";
    private static final String CHECK_USER = "CheckUser";
    private static final String WEBSERVICE_PULL_TRANSLATION = "PullSetting";
    private static final String WEBSERVICE_EDIT_USER = "EditUser";

    protected static String URL;

    public static boolean Ping() {
        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("uniqueKey", "hello");
            jsonObj.put("authKey", ApplicationSettingsFilePath.AuthenticationKey);
            String jsonParameter = jsonObj.toString();
            if (HttpsConnection.CallHttpsWebServiceJsonParam(URL, WEBSERVICE_PING, jsonParameter) != null) {
                return true;
            }
        } catch (ConnectTimeoutException ctoEx) {
            PMWF_Log.fnlog(PMWF_Log.INFO, "Ping()", ctoEx.getMessage());
        } catch (UnknownHostException unknownHostEx) {
            PMWF_Log.fnlog(PMWF_Log.INFO, "Ping()", unknownHostEx.getMessage());
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "Ping()", PMWF_Log.getStringFromStackTrace(ex));
        } catch (Error er) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "Ping()", PMWF_Log.getStringFromError(er));
        }
        return false;
    }

    public static String RegisterUser(String usersJsonString) {
        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("userJsonString", usersJsonString);
            // jsonObj.put("localeId", ApplicationSettingsFilePath.language);
            String jsonParameter = jsonObj.toString();
            return (HttpsConnection.CallHttpsWebServiceJsonParam(URL, WEBSERVICE_REGISTER, jsonParameter));

        } catch (ConnectTimeoutException ctoEx) {
            Log.e("RegisterUser()", ctoEx.getMessage());
        } catch (UnknownHostException unknownHostEx) {
            PMWF_Log.fnlog(PMWF_Log.INFO, "RegisterUser()", unknownHostEx.getMessage());
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "RegisterUser()", PMWF_Log.getStringFromStackTrace(ex));
        } catch (Error er) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "RegisterUser()", PMWF_Log.getStringFromError(er));
        }
        return null;

    }

    public static String EditLanguage(String authKey, int localeId) {
        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("authKey", authKey);
            jsonObj.put("localeId", localeId);
            // jsonObj.put("localeId", ApplicationSettingsFilePath.language);
            String jsonParameter = jsonObj.toString();
            return (HttpsConnection.CallHttpsWebServiceJsonParam(URL, EDIT_LANGAGUE, jsonParameter));

        } catch (ConnectTimeoutException ctoEx) {
            Log.e("RegisterUser()", ctoEx.getMessage());
        } catch (UnknownHostException unknownHostEx) {
            PMWF_Log.fnlog(PMWF_Log.INFO, "RegisterUser()", unknownHostEx.getMessage());
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "RegisterUser()", PMWF_Log.getStringFromStackTrace(ex));
        } catch (Error er) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "RegisterUser()", PMWF_Log.getStringFromError(er));
        }
        return null;

    }

    public static String Login(String EmailAddress, String Password) {
        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("username", EmailAddress);
            jsonObj.put("password", Password);
            jsonObj.put("localeId", ApplicationSettingsFilePath.language);
            String jsonParameter = jsonObj.toString();
            return (HttpsConnection.CallHttpsWebServiceJsonParam(URL, WEBSERVICE_LOGIN, jsonParameter));

        } catch (ConnectTimeoutException ctoEx) {
            Log.e("Login()", ctoEx.getMessage());
        } catch (UnknownHostException unknownHostEx) {
            PMWF_Log.fnlog(PMWF_Log.INFO, "Login()", unknownHostEx.getMessage());
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "Login()", PMWF_Log.getStringFromStackTrace(ex));
        } catch (Error er) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "Login()", PMWF_Log.getStringFromError(er));
        }
        return null;
    }

    /*
     * to push data from apk to server parameter : authentication key :
     * type('Alert', 'Contact') : jsonString
     */

    public static String Push(String authKey, String type, String jsonString) {
        try {

            JSONObject jsonObj = new JSONObject();
            jsonObj.put("authKey", authKey);
            jsonObj.put("type", type);
            // jsonObj.put("localeId", ApplicationSettingsFilePath.language);
            jsonObj.put("jsonString", jsonString);
            String jsonParameter = jsonObj.toString();
            return (HttpsConnection.CallHttpsWebServiceJsonParam(URL, WEBSERVICE_PUSH, jsonParameter));

        } catch (ConnectTimeoutException ctoEx) {
            Log.e("Push()", ctoEx.getMessage());
        } catch (UnknownHostException unknownHostEx) {
            PMWF_Log.fnlog(PMWF_Log.INFO, "Push()", unknownHostEx.getMessage());
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "Push()", PMWF_Log.getStringFromStackTrace(ex));
        } catch (Error er) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "Push()", PMWF_Log.getStringFromError(er));
        }
        return null;
    }

    /*
     * pull data from server parameter: authentication key : type
     * ('UserAndContact: to pull user and contact data' or 'contact: pull
     * contact data only' or 'Alert: pull alert from database')
     */

    public static String Pull(String authKey, String type) {
        try {

            JSONObject jsonObj = new JSONObject();
            jsonObj.put("authKey", authKey);
            jsonObj.put("type", type);
            // jsonObj.put("localeId", ApplicationSettingsFilePath.language);
            String jsonParameter = jsonObj.toString();
            return (HttpsConnection.CallHttpsWebServiceJsonParam(URL, WEBSERVICE_PULL, jsonParameter));

        } catch (ConnectTimeoutException ctoEx) {
            Log.e("Pull()", ctoEx.getMessage());
        } catch (UnknownHostException unknownHostEx) {
            PMWF_Log.fnlog(PMWF_Log.INFO, "Pull()", unknownHostEx.getMessage());
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "Pull()", PMWF_Log.getStringFromStackTrace(ex));
        } catch (Error er) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "Pull()", PMWF_Log.getStringFromError(er));
        }
        return null;
    }

    /*
     * Reset password parameter: emailaddress (main user ) : newPassword :
     * oldPassword
     */

    public static String ResetPassword(String authKey, String emailAddress, String newPassword, String oldPassword) {
        try {

            JSONObject jsonObj = new JSONObject();
            jsonObj.put("authKey", authKey);
            jsonObj.put("emailAddress", emailAddress);
            jsonObj.put("newPassword", newPassword);
            jsonObj.put("pinCodePassword", oldPassword);
            // jsonObj.put("localeId", ApplicationSettingsFilePath.language);
            jsonObj.toString();
            String jsonParameter = jsonObj.toString();
            return (HttpsConnection.CallHttpsWebServiceJsonParam(URL, RESET_PASSWORD, jsonParameter));

        } catch (ConnectTimeoutException ctoEx) {
            Log.e("ResetPassword()", ctoEx.getMessage());
        } catch (UnknownHostException unknownHostEx) {
            PMWF_Log.fnlog(PMWF_Log.INFO, "ResetPassword()", unknownHostEx.getMessage());
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "ResetPassword()", PMWF_Log.getStringFromStackTrace(ex));
        } catch (Error er) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "ResetPassword()", PMWF_Log.getStringFromError(er));
        }
        return null;
    }

    /*
     * forgot password parameter: emailAddress
     */

    public static String ForgotPassword(String emailAddress) {
        try {

            /*
             * JSONObject jsonObj = new JSONObject(); jsonObj.put("email",
             * emailAddress); jsonObj.put("confirmed", false);
             * jsonObj.put("cancelled", false); jsonObj.put("localeId",
             * ApplicationSettingsFilePath.language); jsonObj.toString(); String
             * jsonParameter = jsonObj.toString();
             */
            return (HttpsConnection.CallHttpsWebServiceGet(URL, FORGOT_PASSWORD, emailAddress));

        } catch (ConnectTimeoutException ctoEx) {
            Log.e("ForgotPassword()", ctoEx.getMessage());
        } catch (UnknownHostException unknownHostEx) {
            PMWF_Log.fnlog(PMWF_Log.INFO, "ForgotPassword()", unknownHostEx.getMessage());
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "ForgotPassword()", PMWF_Log.getStringFromStackTrace(ex));
        } catch (Error er) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "ForgotPassword()", PMWF_Log.getStringFromError(er));
        }
        return null;
    }

    /*
     * to edit contact parameter: authkey : guid of edit contact : contactvalue
     * ( alertType id )
     */

    public static String EditContact(String authKey, String guid, JSONArray contactValue) {
        try {

            JSONObject jsonObj = new JSONObject();
            jsonObj.put("authKey", authKey);
            jsonObj.put("alertTypes", contactValue);
            jsonObj.put("contactGuid", guid);
            // jsonObj.put("localeId", ApplicationSettingsFilePath.language);
            String jsonParameter = jsonObj.toString();
            return (HttpsConnection.CallHttpsWebServiceJsonParam(URL, EDIT_CONTACT, jsonParameter));

        } catch (ConnectTimeoutException ctoEx) {
            Log.e("EditContact()", ctoEx.getMessage());
        } catch (UnknownHostException unknownHostEx) {
            PMWF_Log.fnlog(PMWF_Log.INFO, "EditContact()", unknownHostEx.getMessage());
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "EditContact()", PMWF_Log.getStringFromStackTrace(ex));
        } catch (Error er) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "EditContact()", PMWF_Log.getStringFromError(er));
        }
        return null;
    }

    /*
     * to delete contact parameter: authentication key : guid of contact to
     * delete
     */

    public static String DeleteContact(String authKey, String guid) {
        try {

            JSONObject jsonObj = new JSONObject();
            jsonObj.put("authKey", authKey);
            jsonObj.put("contactGuid", guid);
            // jsonObj.put("localeId", ApplicationSettingsFilePath.language);
            String jsonParameter = jsonObj.toString();
            return (HttpsConnection.CallHttpsWebServiceJsonParam(URL, DELETE_CONTACT, jsonParameter));

        } catch (ConnectTimeoutException ctoEx) {
            Log.e("DeleteContact()", ctoEx.getMessage());
        } catch (UnknownHostException unknownHostEx) {
            PMWF_Log.fnlog(PMWF_Log.INFO, "DeleteContact()", unknownHostEx.getMessage());
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "DeleteContact()", PMWF_Log.getStringFromStackTrace(ex));
        } catch (Error er) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "DeleteContact()", PMWF_Log.getStringFromError(er));
        }
        return null;
    }

    /*
     * check user email address if already exist in db server parameter:
     * emailAddress (main user register)
     */

    public static String CheckUser(String emailAddress) {
        try {

            JSONObject jsonObj = new JSONObject();
            jsonObj.put("emailAddress", emailAddress);
            jsonObj.put("localeId", ApplicationSettingsFilePath.language);
            String jsonParameter = jsonObj.toString();
            return (HttpsConnection.CallHttpsWebServiceJsonParam(URL, CHECK_USER, jsonParameter));

        } catch (ConnectTimeoutException ctoEx) {
            Log.e("CheckUser()", ctoEx.getMessage());
        } catch (UnknownHostException unknownHostEx) {
            PMWF_Log.fnlog(PMWF_Log.INFO, "CheckUser()", unknownHostEx.getMessage());
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "CheckUser()", PMWF_Log.getStringFromStackTrace(ex));
        } catch (Error er) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "CheckUser()", PMWF_Log.getStringFromError(er));
        }
        return null;
    }

    /*
     * pullsetting type parameter: type ('Translation' or 'AlertType' or
     * 'Alertstatuses')
     */

    public static String PullSetting(String type) {
        try {

            JSONObject jsonObj = new JSONObject();
            jsonObj.put("type", type);
            String jsonParameter = jsonObj.toString();
            return (HttpsConnection.CallHttpsWebServiceJsonParam(URL, WEBSERVICE_PULL_TRANSLATION, jsonParameter));

        } catch (ConnectTimeoutException ctoEx) {
            Log.e("PullTranslation()", ctoEx.getMessage());
        } catch (UnknownHostException unknownHostEx) {
            PMWF_Log.fnlog(PMWF_Log.INFO, "PullTranslation()", unknownHostEx.getMessage());
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "PullTranslation()", PMWF_Log.getStringFromStackTrace(ex));
        } catch (Error er) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "PullTranslation()", PMWF_Log.getStringFromError(er));
        }
        return null;
    }

    public static String editUser(String authKey, String jsonString) {
        try {

            JSONObject jsonObj = new JSONObject();
            jsonObj.put("authKey", authKey);
            jsonObj.put("userJsonString", jsonString);
            // jsonObj.put("localeId", localeId);
            String jsonParameter = jsonObj.toString();
            Log.i("jsonParameter", jsonParameter);
            return (HttpsConnection.CallHttpsWebServiceJsonParam(URL, WEBSERVICE_EDIT_USER, jsonParameter));

        } catch (ConnectTimeoutException ctoEx) {
            Log.e("editUser()", ctoEx.getMessage());
        } catch (UnknownHostException unknownHostEx) {
            PMWF_Log.fnlog(PMWF_Log.INFO, "editUser()", unknownHostEx.getMessage());
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "editUser()", PMWF_Log.getStringFromStackTrace(ex));
        } catch (Error er) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "editUser()", PMWF_Log.getStringFromError(er));
        }
        return null;
    }
}
