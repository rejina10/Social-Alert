package com.android.socialalert.json;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.android.socialalert.database.UsersInfo;
import com.android.socialalert.logger.PMWF_Log;

public class JSONHelper {
    public static JSONArray usersListTOJsonArray1(ArrayList<UsersInfo> usersInfo, ArrayList<UsersInfo> contactInfo) {
        JSONArray result = new JSONArray();
        try {
            JSONArray contactInfoResult = new JSONArray();
            JSONArray objAlertTypeList = new JSONArray();
            ArrayList<String> alertTypeArr = new ArrayList<String>();

            for (int i = 0; i < contactInfo.size(); i++) {
                JSONObject objContact = new JSONObject();
                objContact.put("FirstName", contactInfo.get(i).getFirstName());
                objContact.put("LastName", contactInfo.get(i).getLastName());
                objContact.put("EmailAddress", contactInfo.get(i).getEmailAddress());
                alertTypeArr = contactInfo.get(i).getAlertType();
                for (int j = 0; j < alertTypeArr.size(); j++) {
                    objAlertTypeList.put(alertTypeArr.get(j).toString());
                }
                objContact.put("AlertTypes", objAlertTypeList);
                contactInfoResult.put(objContact);
                objAlertTypeList = new JSONArray();
            }

            for (UsersInfo item : usersInfo) {
                JSONObject obj = new JSONObject();
                obj.put("FirstName", item.getFirstName());
                obj.put("LastName", item.getLastName());
                obj.put("EmailAddress", item.getEmailAddress());
                obj.put("Password", item.getPassword());
                obj.put("Address", item.getAddress());
                obj.put("PhoneNumber", item.getPhoneNumber());
                if (contactInfoResult == null) {
                    obj.put("Contacts", contactInfoResult);
                } else {
                    obj.put("Contacts", contactInfoResult);
                }
                result.put(obj);
            }

        } catch (Exception e) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "JSONHelper: usersListTOJsonArray1()", PMWF_Log.getStringFromStackTrace(e));
        }
        return result;
    }

    public static JSONObject usersListTOJsonArray(ArrayList<UsersInfo> usersInfo, ArrayList<UsersInfo> contactInfo) {
        JSONObject obj = new JSONObject();
        try {
            // JSONArray contactInfoResult = new JSONArray();
            // JSONArray objAlertTypeList = new JSONArray();
            // ArrayList<String> alertTypeArr = new ArrayList<String>();

            /*
             * for(int i=0;i<contactInfo.size();i++){ JSONObject objContact =
             * new JSONObject(); //objContact.put("FirstName",
             * contactInfo.get(i).getFirstName());
             * objContact.put("FirstName",""); //objContact.put("LastName",
             * contactInfo.get(i).getLastName()); objContact.put("LastName","");
             * objContact.put("EmailAddress",
             * contactInfo.get(i).getEmailAddress()); alertTypeArr =
             * contactInfo.get(i).getAlertType(); for(int
             * j=0;j<alertTypeArr.size();j++){
             * objAlertTypeList.put(alertTypeArr.get(j).toString()); }
             * objContact.put("AlertTypes",objAlertTypeList);
             * contactInfoResult.put(objContact); objAlertTypeList = new
             * JSONArray(); }
             */

            for (UsersInfo item : usersInfo) {

                obj.put("FirstName", item.getFirstName());
                obj.put("LastName", item.getLastName());
                obj.put("EmailAddress", item.getEmailAddress());
                obj.put("Password", item.getPassword());
                obj.put("Address", item.getAddress());
                obj.put("PhoneNumber", item.getPhoneNumber());
                obj.put("LocaleId", item.getSelectedLangauge());
                // obj.put("Contacts", contactInfoResult);
            }

        } catch (Exception e) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "JSONHelper: usersListTOJsonArray()", PMWF_Log.getStringFromStackTrace(e));
        }
        return obj;
    }

    public static JSONArray contactListTOJsonArray(ArrayList<UsersInfo> contactInfo, boolean isEditContact) {
        JSONArray contactInfoResult = new JSONArray();
        try {

            JSONArray objAlertTypeList = new JSONArray();
            JSONObject objContact = new JSONObject();
            ArrayList<String> alertTypeArr = new ArrayList<String>();

            for (int i = 0; i < contactInfo.size(); i++) {

                if (isEditContact) {
                    objContact.put("FirstName", contactInfo.get(i).getFirstName());
                    objContact.put("LastName", contactInfo.get(i).getLastName());

                } else {
                    // objContact.put("FirstName",
                    // contactInfo.get(i).getFirstName());
                    objContact.put("FirstName", "");
                    // objContact.put("LastName",
                    // contactInfo.get(i).getLastName());
                    objContact.put("LastName", "");
                }
                objContact.put("EmailAddress", contactInfo.get(i).getEmailAddress());
                alertTypeArr = contactInfo.get(i).getAlertType();
                for (int j = 0; j < alertTypeArr.size(); j++) {
                    objAlertTypeList.put(alertTypeArr.get(j).toString());
                }

                objContact.put("AlertTypes", objAlertTypeList);
                contactInfoResult.put(objContact);
                objContact = new JSONObject();
                objAlertTypeList = new JSONArray();
            }

        } catch (Exception e) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "JSONHelper: contactListTOJsonArray()", PMWF_Log.getStringFromStackTrace(e));
        }
        return contactInfoResult;
    }

    // public static void ParseJsonString1(String jsonString){
    // try {
    // JSONObject loginInfo = new JSONObject(jsonString);
    // JSONObject uniObject = loginInfo.getJSONObject("LoginResult");
    // String authKey = uniObject.getString("AuthenticationKey");
    // String loginSuccess = uniObject.getString("IsSuccessful");
    // String message = uniObject.getString("Message");
    //
    // ApplicationSettingsFilePath.AuthenticationKey = authKey;
    // } catch (JSONException e) {
    // e.printStackTrace();
    //
    // }
    // }

    public static boolean getJsonObjectValue(String jsonObj, String jsonString) {
        try {
            // String msg;
            JSONObject obj = new JSONObject(jsonString);
            JSONObject uniObject = obj.getJSONObject("PullSettingResult");
            boolean isSuccessful = uniObject.getBoolean(jsonObj);

            if (isSuccessful) {
                return true;
            }
        } catch (Exception e) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "JSONHelper: getJsonObjectValue()", PMWF_Log.getStringFromStackTrace(e));
        }
        return false;
    }

    public static boolean getJsonObjectResult(String mainJsonObj, String jsonObj, String jsonString) {
        try {
            // String msg;
            JSONObject obj = new JSONObject(jsonString);
            JSONObject uniObject = obj.getJSONObject(mainJsonObj);
            boolean isSuccessful = uniObject.getBoolean(jsonObj);

            if (isSuccessful) {
                return true;
            }
        } catch (Exception e) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "JSONHelper: getJsonObjectResult()", PMWF_Log.getStringFromStackTrace(e));
        }
        return false;
    }

    public static boolean getPushResult(String jsonObj, String jsonString) {
        try {
            // String msg;
            JSONObject obj = new JSONObject(jsonString);
            JSONObject uniObject = obj.getJSONObject("PushResult");
            boolean isSuccessful = uniObject.getBoolean(jsonObj);

            if (isSuccessful) {
                return true;
            }
        } catch (Exception e) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "JSONHelper: getPushResult()", PMWF_Log.getStringFromStackTrace(e));
        }
        return false;
    }

    public static String getMessage(String jsonObj, String jsonString) {
        try {

            JSONObject obj = new JSONObject(jsonString);
            JSONObject uniObject = obj.getJSONObject("LoginResult");
            String msg = uniObject.getString(jsonObj);
            if (!msg.equalsIgnoreCase("null")) {
                return msg;
            }

        } catch (Exception e) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "JSONHelper: getMessage()", PMWF_Log.getStringFromStackTrace(e));
        }
        return null;
    }

    public static String getMessageResult(String mainJsonObj, String jsonObj, String jsonString) {
        try {

            JSONObject obj = new JSONObject(jsonString);
            JSONObject uniObject = obj.getJSONObject(mainJsonObj);
            String msg = uniObject.getString(jsonObj);
            if (!msg.equalsIgnoreCase("null")) {
                return msg;
            }

        } catch (Exception e) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "JSONHelper: getMessageResult()", PMWF_Log.getStringFromStackTrace(e));
        }
        return null;
    }

    public static String getAuthenticatioKey(String jsonString) {
        try {

            JSONObject obj = new JSONObject(jsonString);
            JSONObject uniObject = obj.getJSONObject("LoginResult");
            String msg = uniObject.getString("AuthenticationKey");
            if (!msg.equalsIgnoreCase("null")) {
                return msg;
            }

        } catch (Exception e) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "JSONHelper: getAuthenticatioKey()", PMWF_Log.getStringFromStackTrace(e));
        }
        return null;
    }

    public static String getResult(String jsonObj, String jsonString) {
        try {
            JSONObject jsonResult = new JSONObject(jsonString);
            String pullResult = jsonResult.getString("PullResult");
            JSONObject pullResultObj = new JSONObject(pullResult);
            String result = pullResultObj.getString("Result");
            JSONArray resultObj = new JSONArray(result);

            int size = resultObj.length();
            if (size == 0) { // no alerts responded
                return null;
            } else {
                /*
                 * for(int i=0;i<resultObj.length();i++){
                 * 
                 * JSONObject uniObject = resultObj.getJSONObject(i); String
                 * alertStatus = uniObject.getString("AlertStatus");
                 * 
                 * if(alertStatus.equalsIgnoreCase("Sent")){ //insert new
                 * ApplicationSettings.dbAccessor.insertRecieverInfo(result); }
                 * 
                 * if(alertStatus.equalsIgnoreCase("Accepted") ||
                 * alertStatus.equalsIgnoreCase("Rejected")){ String alertGuid =
                 * uniObject.getString("Guid");
                 * ApplicationSettings.dbAccessor.insertUpdateStatus
                 * (alertGuid,alertStatus); } }
                 */
                return result;
            }

        } catch (Exception e) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "JSONHelper: getResult()", PMWF_Log.getStringFromStackTrace(e));
        }
        return null;
    }

    public static String getJsonResult(String maiJsonObj, String jsonObj, String jsonString) {
        try {
            JSONObject jsonResult = new JSONObject(jsonString);
            String pullResult = jsonResult.getString(maiJsonObj);
            JSONObject pullResultObj = new JSONObject(pullResult);
            String result = pullResultObj.getString(jsonObj);
            if (result.length() > 0 && !result.equalsIgnoreCase("null")) {
                return result;
            }

        } catch (Exception e) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "JSONHelper: getJsonResult()", PMWF_Log.getStringFromStackTrace(e));
        }
        return null;
    }

    public static boolean getJsonObjectValueForRegister(String jsonObj, String jsonString) {

        try {
            JSONObject obj = new JSONObject(jsonString);
            JSONObject uniObject = obj.getJSONObject("RegisterUserResult");
            boolean isSuccessful = uniObject.getBoolean(jsonObj);

            if (isSuccessful) {
                return true;
            }
        } catch (Exception e) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "JSONHelper: getJsonObjectValueForRegister()",
                    PMWF_Log.getStringFromStackTrace(e));
        }
        return false;
    }

    public static JSONArray editContactInfoToJson(ArrayList<UsersInfo> contactInfo) { // alertInfo::
                                                                                      // previous
                                                                                      // alert
                                                                                      // info

        try {
            // JSONArray contactInfoResult = new JSONArray();
            JSONArray objAlertTypeList = new JSONArray();
            ArrayList<String> alertTypeArr = new ArrayList<String>();

            // String guid =
            // ApplicationSettings.dbAccessor.getGuidFromEmail(contactInfo.get(0).getEmailAddress());

            // JSONObject objContact = new JSONObject();
            // objContact.put("contactGuid",guid);
            alertTypeArr = contactInfo.get(0).getAlertType();
            for (int j = 0; j < alertTypeArr.size(); j++) {

                objAlertTypeList.put(Integer.parseInt(alertTypeArr.get(j).toString()));
            }

            return objAlertTypeList;

        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "JSONHelper: editContactInfoToJson()", PMWF_Log.getStringFromStackTrace(ex));
        }

        return null;
    }

    public static String editUserInfoJson(UsersInfo editInfoDetails) {

        return null;

    }

}
