package com.android.socialalert.database;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.android.socialalert.AlertInfo;
import com.android.socialalert.ContactInfo;
import com.android.socialalert.Person;
import com.android.socialalert.logger.PMWF_Log;
import com.android.socialalert.settings.ApplicationSettings;
import com.android.socialalert.settings.ApplicationSettingsFilePath;
import com.android.socialalert.utilities.DateTimeProcessor;

public class UserCursor {

    private SQLiteDatabase database;
    private SqlDatabase dbHelper;
    public static final String TABLE_ALERTTYPES = "AlertTypes";
    public static final String TABLE_STATUS = "Status";
    // public static final String TABLE_ALERTTYPEITEMS = "AlertTypeItems";
    public static final String ALERTTYPE_ID = "AlertTypeId";
    public static final String ALERTTYPE_DESCRIPTION = "Description";
    public static final String ALERTTYPE_GUID = "Guid";
    public static final String ALERTTYPE_LOCALE = "LocaleId";
    public static final String USERSTATUS_ID = "UserStatusId";

    private String[] allAlertTypesColumns = { ALERTTYPE_ID, ALERTTYPE_DESCRIPTION, ALERTTYPE_LOCALE };

    private String[] allUserStatusColumns = { USERSTATUS_ID, ALERTTYPE_DESCRIPTION, ALERTTYPE_GUID };
    public static final String TABLE_USERS = "Users";
    public static final String USER_ID = "UserId";
    public static final String USER_ISACTIVE = "IsActive";
    public static final String USER_ISREGISTER = "IsRegister";
    public static final String FIRSTNAME = "FirstName";
    public static final String LASTNAME = "LastName";
    public static final String EMAILADDRESS = "EmailAddress";
    public static final String PASSWORD = "Password";
    public static final String ADDRESS = "Address";
    public static final String PHONENUMBER = "PhoneNumber";
    public static final String USER_GUID = "Guid";
    public static final String USER_CREATED_DATETIME = "DateCreated";
    public static final String USER_MODIFIED_DATETIME = "DateModified";
    public static final String USER_DELETED_DATETIME = "DateDeleted";

    public static final String TABLE_USER_CONNECTIONS = "UserConnections";
    public static final String USER_CONNECTION_ID = "UserConnectionId";
    public static final String FROM_USER_ID = "FromUserId";
    public static final String TO_USER_ID = "ToUserId";
    public static final String ALERTYPE_ID = "AlertTypeId";
    public static final String ACCEPTED_YES_NO = "IsAccepted";
    public static final String USER_CONNECTIONS_GUID = "Guid";
    public static final String USERCONNECTIONS_CREATED_DATETIME = "DateCreated";
    public static final String USERCONNECTIONS_MODIFIED_DATETIME = "DateModified";
    public static final String USERCONNECTIONS_DELETED_DATETIME = "DateDeleted";

    private String[] allUserConnectionsColumns = { USER_CONNECTION_ID, FROM_USER_ID, TO_USER_ID, ALERTYPE_ID,
            ACCEPTED_YES_NO, USER_CONNECTIONS_GUID };

    public static final String TABLE_ALERTS = "Alerts";
    public static final String ALERT_ID = "AlertId";
    public static final String ALERT_FROM_USERID = "AlertFromUserId";
    public static final String ALERT_TO_USERID = "AlertToUserId";
    public static final String ALERTTYPEID = "AlertTypeId";
    public static final String ALERTSTATUS_ID = "AlertStatusId";
    public static final String ALERT_SEND_DATETIME = "AlertSendDateTime";
    public static final String ALERT_RECEIVED_DATETIME = "AlertReceivedDateTime";
    public static final String ALERT_ACKNOWLEDGED_DATETIME = "AlertAcknowledgedDateTime";
    public static final String ALERT_GUID = "Guid";
    public static final String ALERT_KEY = "AlertKey";
    public static final String ALERT_CREATED_DATETIME = "CreatedDateTime";
    public static final String ALERT_MODIFIED_DATETIME = "ModifiedDateTime";
    public static final String ALERT_DELETED_DATETIME = "DeletedDateTime";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String STATUS_FLAG = "status_flag";

    public static final String TABLE_TRANSLATIONS = "Translations";
    public static final String TRANSLATION_ID = "TranslationId";
    public static final String TRANSLATION_CODE = "TranslationCode";
    public static final String TRANSLATION_TEXT = "TranslationText";
    public static final String LOCALE = "Locale";

    public static final String TABLE_LANGUAGE_SETTING = "Setting";
    public static final String LANGUAGE_ID = "id";
    public static final String LANGUAGE = "Language";

    public static final String TABLE_LOGIN_INFO = "LogIn";
    public static final String LOGINID = "id";
    public static final String LOGIN_EMAILADDRESS = "Emailaddress";
    public static final String LOGIN_PASSWORD = "Password";

    public static final String TABLE_SHAKE_THRESHOLD_INFO = "ShakeInfo";
    public static final String SHAKE_THRESHOLDVALUE = "ThresholdValue";

    public static final String TABLE_USER_STATUS = "UserStatus";
    public static final String QUERY_CREATE_TABLE_USER_STATUS = "CREATE TABLE IF NOT EXISTS " + TABLE_USER_STATUS
            + " (" + USER_GUID + " TEXT ," + USERSTATUS_ID + " INTEGER)";

    public static final String QUERY_CREATE_TABLE_USERS = "CREATE TABLE IF NOT EXISTS " + TABLE_USERS + " (" + USER_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," + USER_ISACTIVE + " INTEGER, " + USER_ISREGISTER + " INTEGER, "
            + FIRSTNAME + " TEXT, " + LASTNAME + " TEXT," + EMAILADDRESS + " TEXT," + PASSWORD + " TEXT," + ADDRESS
            + " TEXT, " + PHONENUMBER + " NUMERIC, " + USER_GUID + " TEXT, " + USER_MODIFIED_DATETIME + " TEXT)";

    public static final String QUERY_CREATE_TABLE_USER_CONNECTIONS = "CREATE TABLE IF NOT EXISTS "
            + TABLE_USER_CONNECTIONS + " (" + USER_CONNECTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FROM_USER_ID + " INTEGER, " + TO_USER_ID + " INTEGER, " + ALERTYPE_ID + " INTEGER," + ACCEPTED_YES_NO
            + " INTEGER, " + USER_CONNECTIONS_GUID + " TEXT)";

    public static final String QUERY_CREATE_TABLE_ALERTS = "CREATE TABLE IF NOT EXISTS " + TABLE_ALERTS + " ("
            + ALERT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + ALERT_FROM_USERID + " INTEGER, " + ALERT_TO_USERID
            + " INTEGER, " + ALERTYPE_ID + " INTEGER," + ALERTSTATUS_ID + " INTEGER," + ALERT_SEND_DATETIME + " TEXT, "
            + ALERT_RECEIVED_DATETIME + " TEXT," + ALERT_ACKNOWLEDGED_DATETIME + " TEXT," + ALERT_GUID + " TEXT,"
            + ALERT_CREATED_DATETIME + " TEXT," + ALERT_MODIFIED_DATETIME + " TEXT," + ALERT_DELETED_DATETIME // +
                                                                                                              // " TEXT)";
            + " TEXT," + LATITUDE + " TEXT," + LONGITUDE + " TEXT," + STATUS_FLAG + " INTEGER ," + ALERT_KEY + " TEXT)";

    public static final String QUERY_CREATE_TABLE_ALERTTYPES = "CREATE TABLE IF NOT EXISTS " + TABLE_ALERTTYPES + " ("
            + ALERTTYPE_ID + " INTEGER ," + ALERTTYPE_DESCRIPTION + " TEXT," + ALERTTYPE_LOCALE + " TEXT)";

    public static final String TABLE_ALERTS_STATUS = "AlertStatus";
    public static final String ALERT_STATUS_ID = "AlertStatusId";
    public static final String STATUS_DESCRIPTION = "Description";

    public static final String QUERY_CREATE_TABLE_STATUS = "CREATE TABLE IF NOT EXISTS " + TABLE_STATUS + " ("
            + USERSTATUS_ID + " INTEGER ," + STATUS_DESCRIPTION + " TEXT," + ALERT_GUID + " TEXT)";

    private String[] allAlertStatusesColumns = { ALERT_STATUS_ID, STATUS_DESCRIPTION };

    public static final String QUERY_CREATE_TABLE_ALERTS_STATUS = "CREATE TABLE IF NOT EXISTS " + TABLE_ALERTS_STATUS
            + " (" + ALERT_STATUS_ID + " INTEGER PRIMARY KEY," + STATUS_DESCRIPTION + " TEXT)";

    public static final String QUERY_CREATE_TABLE_TRANSLATIONS = "CREATE TABLE IF NOT EXISTS " + TABLE_TRANSLATIONS
            + " (" + TRANSLATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TRANSLATION_CODE + " TEXT, "
            + TRANSLATION_TEXT + " TEXT, " + LOCALE + " TEXT)";

    public static final String QUERY_CREATE_TABLE_SETTING = "CREATE TABLE IF NOT EXISTS " + TABLE_LANGUAGE_SETTING
            + " (" + LANGUAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + LANGUAGE + " TEXT)";

    public static final String QUERY_CREATE_TABLE_LOGIN_INFO = "CREATE TABLE IF NOT EXISTS " + TABLE_LOGIN_INFO + " ("
            + LOGINID + " INTEGER PRIMARY KEY AUTOINCREMENT," + LOGIN_EMAILADDRESS + " TEXT," + LOGIN_PASSWORD
            + " TEXT)";

    public static final String QUERY_CREATE_TABLE_SHAKE_THRESHOLD_INFO = "CREATE TABLE IF NOT EXISTS "
            + TABLE_SHAKE_THRESHOLD_INFO + " (" + SHAKE_THRESHOLDVALUE + " TEXT)";

    public UserCursor(Context context, String dbPath) {
        dbHelper = new SqlDatabase(context, dbPath);
        try {
            database = dbHelper.createDataBase();

        } catch (Exception ex) {
            // throw new Error("Unable to create database " + ex.getMessage());
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor::createDataBase()", PMWF_Log.getStringFromStackTrace(ex));
        }
    }

    public static int initDBAccessor(Context context, String dbPath) {
        new UserCursor(context, dbPath);
        return 0;
    }

    public void close() {
        if (database != null) {
            database.close();
        }
        dbHelper.close();
    }

    public void insertAlertSetting(String alertJsonList) { // insert alert type
                                                           // information like
                                                           // emergency, fire

        Cursor cursor = null;
        try {
            JSONObject userJson = new JSONObject(alertJsonList);
            String pullResult = userJson.getString("PullSettingResult");
            JSONObject pullResultObj = new JSONObject(pullResult);
            String result = pullResultObj.getString("Result");
            JSONArray resultObj = new JSONArray(result);

            int size = resultObj.length();
            for (int i = 0; i < size; i++) {
                JSONObject uniObject = resultObj.getJSONObject(i);
                String alertTypeId = uniObject.getString("TypeId");
                String description = uniObject.getString("Description");
                String locale = uniObject.getString("localeId");

                cursor = database.query(TABLE_ALERTTYPES, allAlertTypesColumns, ALERTTYPE_DESCRIPTION + " = ? ",
                        new String[] { description }, null, null, null, null);
                int rowCount = cursor.getCount();
                ContentValues alertValues = new ContentValues();
                alertValues.clear();
                alertValues.put(ALERTTYPE_ID, alertTypeId);
                alertValues.put(ALERTTYPE_DESCRIPTION, description);
                alertValues.put(ALERTTYPE_LOCALE, locale);
                if (rowCount == 0) {
                    database.insert(TABLE_ALERTTYPES, null, alertValues);
                } else {
                    database.update(TABLE_ALERTTYPES, alertValues, ALERTTYPE_DESCRIPTION + " = ? ",
                            new String[] { description });
                }

            }

        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor::insertAlertSetting()", PMWF_Log.getStringFromStackTrace(ex));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public void insertAlertStatusSetting(String alertJsonList) { // insert alert
                                                                 // status
                                                                 // information
                                                                 // like
                                                                 // pending,
                                                                 // sent,
                                                                 // recieved
        Cursor cursor = null;
        try {
            JSONObject userJson = new JSONObject(alertJsonList);
            String pullResult = userJson.getString("PullSettingResult");
            JSONObject pullResultObj = new JSONObject(pullResult);
            String result = pullResultObj.getString("Result");
            JSONArray resultObj = new JSONArray(result);

            int size = resultObj.length();
            for (int i = 0; i < size; i++) {
                JSONObject uniObject = resultObj.getJSONObject(i);
                String alertStatusId = uniObject.getString("AlertStatusId");
                String description = uniObject.getString("Description");
                // cursor = database.query(TABLE_ALERTS_STATUS,
                // allAlertStatusesColumns, ALERT_STATUS_ID + " = '" +
                // alertStatusId + "'", null, null, null, null);
                cursor = database.query(TABLE_ALERTS_STATUS, allAlertStatusesColumns, STATUS_DESCRIPTION + " = '"
                        + description + "'", null, null, null, null);

                int rowCount = cursor.getCount();
                ContentValues alertStatusValues = new ContentValues();
                alertStatusValues.clear();
                alertStatusValues.put(ALERT_STATUS_ID, alertStatusId);
                alertStatusValues.put(STATUS_DESCRIPTION, description);
                if (rowCount == 0) {
                    database.insert(TABLE_ALERTS_STATUS, null, alertStatusValues);
                } else {
                    database.update(TABLE_ALERTS_STATUS, alertStatusValues, STATUS_DESCRIPTION + " = ? ",
                            new String[] { description });
                }

            }

        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor::insertAlertStatusSetting()",
                    PMWF_Log.getStringFromStackTrace(ex));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public void createUpdateUserAndContact(String jsonString) { // update user
                                                                // and contact
                                                                // information
                                                                // one time
                                                                // after login
        Cursor cursor = null;
        try {
            JSONObject userJson = new JSONObject(jsonString);
            String pullResult = userJson.getString("PullResult");
            JSONObject pullResultObj = new JSONObject(pullResult);
            String result = pullResultObj.getString("Result");
            JSONObject resultObj = new JSONObject(result);
            String uFirstName = resultObj.getString("FirstName");
            String uLastName = resultObj.getString("LastName");
            String uEmailAddress = resultObj.getString("EmailAddress");
            String uPassword = resultObj.getString("Password");
            String uPhoneNumber = resultObj.getString("PhoneNumber");
            // String uSaltKey = resultObj.getString("SaltKey");
            String uGuid = resultObj.getString("Guid");
            String uAddress = resultObj.getString("Address");

            cursor = database.rawQuery("select * from " + TABLE_USERS + " where EMAILADDRESS=?",
                    new String[] { uEmailAddress });
            int rowUserCount = cursor.getCount();
            ContentValues userValues = new ContentValues();
            userValues.clear();
            userValues.put(FIRSTNAME, uFirstName);
            userValues.put(LASTNAME, uLastName);
            userValues.put(EMAILADDRESS, uEmailAddress);
            userValues.put(PASSWORD, uPassword);
            userValues.put(PHONENUMBER, uPhoneNumber);
            userValues.put(ADDRESS, uAddress);
            userValues.put(USER_GUID, uGuid);
            userValues.put(USER_ISACTIVE, 1);
            userValues.put(USER_ISREGISTER, 1);

            if (rowUserCount == 0) {
                database.insert(TABLE_USERS, null, userValues);
            } else if (rowUserCount > 0) {
                database.update(TABLE_USERS, userValues, EMAILADDRESS + "= ?", new String[] { uEmailAddress });
            }

            /*
             * String contacts = resultObj.getString("Contacts");
             * if(!contacts.equalsIgnoreCase("null")){ JSONArray contactJsonObj
             * = new JSONArray(contacts); int size = contactJsonObj.length();
             * 
             * for(int i=0;i<size;i++){ JSONObject uniObject =
             * contactJsonObj.getJSONObject(i); //boolean isApproved =
             * uniObject.getBoolean("IsApproved"); String cFirstName =
             * uniObject.getString("FirstName"); String cLastName =
             * uniObject.getString("LastName"); String cEmailAddress =
             * uniObject.getString("EmailAddress"); String cGuid =
             * uniObject.getString("Guid"); String dateModified =
             * uniObject.getString("DateModified"); String address =
             * resultObj.getString("Address");
             * 
             * cursor = database.rawQuery("select * from " + TABLE_USERS +
             * " where EMAILADDRESS=?", new String[] { cEmailAddress }); int
             * rowCount = cursor.getCount(); ContentValues contactValues = new
             * ContentValues(); contactValues.clear();
             * contactValues.put(FIRSTNAME,cFirstName);
             * contactValues.put(LASTNAME,cLastName);
             * contactValues.put(EMAILADDRESS,cEmailAddress);
             * contactValues.put(USER_GUID,cGuid);
             * contactValues.put(ADDRESS,address);
             * contactValues.put(USER_MODIFIED_DATETIME,dateModified);
             * contactValues.put(USER_ISACTIVE,0);
             * contactValues.put(USER_ISREGISTER,0);
             * 
             * if (rowCount == 0) { database.insert(TABLE_USERS, null,
             * contactValues); } else if (rowCount > 0) {
             * database.update(TABLE_USERS, contactValues, EMAILADDRESS + "= ?",
             * new String[] {cEmailAddress}); } } }
             */

        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor::createUpdateUserAndContact()",
                    PMWF_Log.getStringFromStackTrace(ex));

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

    }

    public void insertUserAndContactInfo(ArrayList<UsersInfo> usersInfo, ArrayList<UsersInfo> contactInfo) { // insert
                                                                                                             // user
                                                                                                             // and
                                                                                                             // contact
                                                                                                             // info
                                                                                                             // after
                                                                                                             // registration
        Cursor cursor = null;

        try {
            if (usersInfo != null) {
                for (UsersInfo item : usersInfo) {

                    ContentValues userValues = new ContentValues();
                    userValues.clear();
                    userValues.put(FIRSTNAME, item.getFirstName());
                    userValues.put(LASTNAME, item.getLastName());
                    userValues.put(EMAILADDRESS, item.getEmailAddress());
                    userValues.put(PASSWORD, item.getPassword());
                    userValues.put(PHONENUMBER, item.getPhoneNumber());
                    // userValues.put(ADDRESS, item.getAddress());
                    // userValues.put(USER_MODIFIED_DATETIME,DateTimeProcessor.GetCurrentFormattedDateTime("yyyyMMdd_HHmmss"));

                    cursor = database.query(TABLE_USERS, null, EMAILADDRESS + " = ? ",
                            new String[] { item.getEmailAddress() }, null, null, null);
                    int rowUserCount = cursor.getCount();

                    if (rowUserCount == 0) {
                        database.insert(TABLE_USERS, null, userValues);
                    } else if (rowUserCount > 0) {
                        database.update(TABLE_USERS, userValues, EMAILADDRESS + "= ?",
                                new String[] { item.getEmailAddress() });
                    }

                }
            }
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor::insertUserAndContactInfo(0)",
                    PMWF_Log.getStringFromStackTrace(ex));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        addUpdateUserContact(contactInfo);
    }

    private void addUpdateUserContact(ArrayList<UsersInfo> contactInfo) {
        Cursor cursor = null;
        try {
            if (contactInfo != null) {
                for (UsersInfo contactItem : contactInfo) {

                    ContentValues userValues = new ContentValues();
                    userValues.clear();
                    userValues.put(FIRSTNAME, contactItem.getFirstName());
                    userValues.put(LASTNAME, contactItem.getLastName());
                    // userValues.put(ADDRESS, contactItem.getAddress());
                    userValues.put(EMAILADDRESS, contactItem.getEmailAddress());
                    // userValues.put(USER_MODIFIED_DATETIME,DateTimeProcessor.GetCurrentFormattedDateTime("yyyyMMdd_HHmmss"));

                    cursor = database.query(TABLE_USERS, null, EMAILADDRESS + " = ? ",
                            new String[] { contactItem.getEmailAddress() }, null, null, null);
                    int rowUserCount = cursor.getCount();

                    if (rowUserCount == 0) {
                        database.insert(TABLE_USERS, null, userValues);
                    } else if (rowUserCount > 0) {
                        database.update(TABLE_USERS, userValues, EMAILADDRESS + "= ?",
                                new String[] { contactItem.getEmailAddress() });
                    }
                }
            }
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor::addUpdateUserContact()", PMWF_Log.getStringFromStackTrace(ex));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public void insertUserConnections(String contactInfo) { // creating a link
                                                            // between main user
                                                            // and contact and
                                                            // insert alert
                                                            // information

        Cursor cursor = null;
        int alertTypeId = 0;
        try {
            if (contactInfo != null) {
                // JSONArray resultObj = new JSONArray(contactInfo);
                // for (int i = 0; i < resultObj.length(); i++) {
                // JSONObject uniObject = resultObj.getJSONObject(i);

                JSONObject uniObject = new JSONObject(contactInfo);
                boolean isAccepted = uniObject.getBoolean("IsApproved");
                // if(isAccepted){
                String cEmailAddress = uniObject.getString("EmailAddress");

                int fromUserId = getUserId(ApplicationSettingsFilePath.AccountName);

                int toUserId = getUserId(cEmailAddress);

                if (fromUserId > 0 && toUserId > 0) {
                    /*
                     * JSONArray contactJsonObj = new JSONArray(jsonString); int
                     * size = contactJsonObj.length();
                     */

                    /* for (int i = 0; i < size; i++) { */
                    // JSONObject uniObject = contact.getJSONObject(i);
                    // boolean isAccepted =
                    // contact.getBoolean("IsApproved");
                    boolean isAddedByUser = uniObject.getBoolean("IsAddedByUser");
                    boolean isAddedByOtherUser = uniObject.getBoolean("IsAddedByOtherUser");
                    // if (isAccepted) {

                    String alertTypes = uniObject.getString("AlertTypes");
                    JSONArray alertTypeArr = new JSONArray(alertTypes);
                    for (int k = 0; k < alertTypeArr.length(); k++) {

                        alertTypeId = Integer.parseInt(alertTypeArr.get(k).toString());
                        /*
                         * 
                         * if (alertTypeArr.get(k).toString().equalsIgnoreCase
                         * ("Inbraak")) { //3 alertTypeId =
                         * getAlertTypeId(alertTypeArr.get(k).toString()); }
                         * 
                         * if (alertTypeArr.get(k).toString().equalsIgnoreCase
                         * ("Ongeval/EHBO")) { //4 alertTypeId =
                         * getAlertTypeId(alertTypeArr.get(k).toString()); }
                         * 
                         * if (alertTypeArr.get(k).toString().equalsIgnoreCase
                         * ("Brand")) { //2 alertTypeId =
                         * getAlertTypeId(alertTypeArr.get(k).toString()); }
                         * 
                         * if (alertTypeArr.get(k).toString().equalsIgnoreCase
                         * ("Aggressie")) { alertTypeId =
                         * getAlertTypeId(alertTypeArr.get(k).toString()); }
                         */

                        String whereValues[] = new String[3];
                        ContentValues userConnectionsValues = null;
                        if (alertTypeId > 0 && isAddedByUser) {
                            userConnectionsValues = new ContentValues();
                            userConnectionsValues.clear();
                            userConnectionsValues.put(FROM_USER_ID, fromUserId);
                            userConnectionsValues.put(TO_USER_ID, toUserId);
                            userConnectionsValues.put(ALERTYPE_ID, alertTypeId);

                            if (isAccepted) {
                                userConnectionsValues.put(ACCEPTED_YES_NO, 1);
                            } else {
                                userConnectionsValues.put(ACCEPTED_YES_NO, 0);
                            }
                            whereValues[0] = Integer.toString(fromUserId);
                            whereValues[1] = Integer.toString(toUserId);
                            whereValues[2] = Integer.toString(alertTypeId);

                        } else if ((alertTypeId > 0 && isAddedByOtherUser)) {
                            if (isAccepted) {
                                userConnectionsValues = new ContentValues();
                                userConnectionsValues.clear();
                                userConnectionsValues.put(FROM_USER_ID, toUserId);
                                userConnectionsValues.put(TO_USER_ID, fromUserId);
                                userConnectionsValues.put(ALERTYPE_ID, alertTypeId);
                                userConnectionsValues.put(ACCEPTED_YES_NO, 1);
                            }

                            whereValues[0] = Integer.toString(toUserId);
                            whereValues[1] = Integer.toString(fromUserId);
                            whereValues[2] = Integer.toString(alertTypeId);

                        }

                        cursor = database.query(TABLE_USER_CONNECTIONS, allUserConnectionsColumns, FROM_USER_ID
                                + " = ? and " + TO_USER_ID + " = ? and " + ALERTYPE_ID + " = ?", whereValues, null,
                                null, null, null);
                        int rowCount = cursor.getCount();
                        if (rowCount == 0) {
                            database.insert(TABLE_USER_CONNECTIONS, null, userConnectionsValues);
                        } else {
                            database.update(TABLE_USER_CONNECTIONS, userConnectionsValues, FROM_USER_ID + " = ? and "
                                    + TO_USER_ID + " = ? and " + ALERTYPE_ID + " = ?", whereValues);
                        }

                    }
                }
            }
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor::insertUserConnections()", PMWF_Log.getStringFromStackTrace(ex));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public void insertUpdateContact(String contactsInfo) { // update contact
                                                           // from background
                                                           // service

        Cursor cursor = null;
        try {
            if (contactsInfo != null) {
                // JSONArray resultObj = new JSONArray(contactsInfo);
                // for (int i = 0; i < resultObj.length(); i++) {

                // JSONObject uniObject = resultObj.getJSONObject(i);
                JSONObject uniObject = new JSONObject(contactsInfo);
                String modifiedDate = uniObject.getString("DateModified");
                String contactGuid = uniObject.getString("Guid");
                String cFirstName = uniObject.getString("FirstName");
                String cLastName = uniObject.getString("LastName");
                String cEmailAddress = uniObject.getString("EmailAddress");
                // String address = uniObject.getString("Address");
                boolean isActive = uniObject.getBoolean("IsActiveUser");
                boolean isRegistered = uniObject.getBoolean("IsRegisteredUser");
                boolean isAddedByUser = uniObject.getBoolean("IsAddedByUser");
                boolean isAddedByOtherUser = uniObject.getBoolean("IsAddedByOtherUser");
                int statusId = uniObject.getInt("StatusId");
                if (statusId > 0) {
                    insertStatusForContact(statusId, contactGuid);
                }
                String date = getModifiedDateFromSqliteDb(cEmailAddress);
                cursor = database.query(TABLE_USERS, null, EMAILADDRESS + " = ? ", new String[] { cEmailAddress },
                        null, null, null);
                int rowCount = cursor.getCount();

                ContentValues contactValues = new ContentValues();
                if (date == null) {

                    if (isRegistered && isActive && isAddedByUser) {

                        contactValues.clear();

                        if (rowCount == 0) {
                            contactValues.put(FIRSTNAME, cFirstName);
                            contactValues.put(LASTNAME, cLastName);
                        }
                        contactValues.put(EMAILADDRESS, cEmailAddress);
                        // contactValues.put(ADDRESS, address);
                        contactValues.put(USER_GUID, contactGuid);
                        contactValues.put(USER_ISACTIVE, 1);
                        contactValues.put(USER_ISREGISTER, 1);
                        contactValues.put(USER_MODIFIED_DATETIME, modifiedDate);
                    } else if (isRegistered && isAddedByUser) {

                        if (rowCount == 0) {
                            contactValues.put(FIRSTNAME, cFirstName);
                            contactValues.put(LASTNAME, cLastName);
                        }
                        contactValues.clear();
                        contactValues.put(EMAILADDRESS, cEmailAddress);
                        contactValues.put(USER_GUID, contactGuid);
                        contactValues.put(USER_ISACTIVE, 0);
                        contactValues.put(USER_ISREGISTER, 1);
                        contactValues.put(USER_MODIFIED_DATETIME, modifiedDate);
                    } else if (!isRegistered && isAddedByUser) {
                        contactValues.clear();
                        if (rowCount == 0) {
                            contactValues.put(FIRSTNAME, cFirstName);
                            contactValues.put(LASTNAME, cLastName);
                        }
                        contactValues.put(EMAILADDRESS, cEmailAddress);
                        // contactValues.put(ADDRESS, address);
                        contactValues.put(USER_GUID, contactGuid);
                        contactValues.put(USER_ISACTIVE, 0);
                        contactValues.put(USER_ISREGISTER, 0);
                        contactValues.put(USER_MODIFIED_DATETIME, modifiedDate);

                    } else if (!isActive && isAddedByUser) {
                        contactValues.clear();
                        if (rowCount == 0) {
                            contactValues.put(FIRSTNAME, cFirstName);
                            contactValues.put(LASTNAME, cLastName);
                        }
                        contactValues.put(EMAILADDRESS, cEmailAddress);
                        // contactValues.put(ADDRESS, address);
                        contactValues.put(USER_GUID, contactGuid);
                        contactValues.put(USER_ISACTIVE, 0);
                        contactValues.put(USER_ISREGISTER, 1);
                        contactValues.put(USER_MODIFIED_DATETIME, modifiedDate);
                    } else if (isAddedByOtherUser) {
                        contactValues.clear();
                        contactValues.put(FIRSTNAME, cFirstName);
                        contactValues.put(LASTNAME, cLastName);
                        contactValues.put(EMAILADDRESS, cEmailAddress);
                        contactValues.put(USER_GUID, contactGuid);
                        contactValues.put(USER_ISACTIVE, 1);
                        contactValues.put(USER_ISREGISTER, 1);
                        contactValues.put(USER_MODIFIED_DATETIME, modifiedDate);

                    } else {

                        contactValues.clear();
                        contactValues.put(FIRSTNAME, cFirstName);
                        contactValues.put(LASTNAME, cLastName);
                        contactValues.put(EMAILADDRESS, cEmailAddress);
                        // contactValues.put(ADDRESS, address);
                        contactValues.put(USER_GUID, contactGuid);
                        contactValues.put(USER_ISACTIVE, 0);
                        contactValues.put(USER_ISREGISTER, 0);
                        contactValues.put(USER_MODIFIED_DATETIME, modifiedDate);
                    }

                    if (rowCount == 0) {
                        database.insert(TABLE_USERS, null, contactValues);
                    } else {
                        database.update(TABLE_USERS, contactValues, EMAILADDRESS + " = ? ",
                                new String[] { cEmailAddress });
                    }

                } else {
                    int value = DateTimeProcessor.compareDates(modifiedDate, date);
                    if (value == 1) {
                        contactValues.clear();

                        if (isRegistered && isActive) {
                            contactValues.put(EMAILADDRESS, cEmailAddress);
                            contactValues.put(USER_GUID, contactGuid);
                            contactValues.put(USER_ISACTIVE, 1);
                            contactValues.put(USER_ISREGISTER, 1);
                            contactValues.put(USER_MODIFIED_DATETIME, modifiedDate);
                        } else if (isRegistered) {
                            contactValues.put(EMAILADDRESS, cEmailAddress);
                            // contactValues.put(ADDRESS, address);
                            contactValues.put(USER_GUID, contactGuid);
                            contactValues.put(USER_ISACTIVE, 0);
                            contactValues.put(USER_ISREGISTER, 1);
                            contactValues.put(USER_MODIFIED_DATETIME, modifiedDate);
                        } else {
                            contactValues.put(EMAILADDRESS, cEmailAddress);
                            // contactValues.put(ADDRESS, address);
                            contactValues.put(USER_GUID, contactGuid);
                            contactValues.put(USER_ISACTIVE, 0);
                            contactValues.put(USER_ISREGISTER, 0);
                            contactValues.put(USER_MODIFIED_DATETIME, modifiedDate);
                        }

                        database.update(TABLE_USERS, contactValues, EMAILADDRESS + " = ? ",
                                new String[] { cEmailAddress });

                    }
                }
                // }
            }
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor::insertUpdateContact()", PMWF_Log.getStringFromStackTrace(ex));

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public void insertAlerts(String AlertType, String alertKey, ArrayList<Integer> connectedUserIds,
            String alertStatus, double gps_positions[]) { // insert the send
                                                          // alerts info

        try {
            int alertTypeId = getAlertTypeId(AlertType);
            int alertStatusId = getAlertStatusId(alertStatus);
            int fromUserId = ApplicationSettings.dbAccessor.getUserId(ApplicationSettingsFilePath.AccountName);
            int count = connectedUserIds != null ? connectedUserIds.size() : 0;
            for (int i = 0; i < count; i++) {

                ContentValues alertsSendValues = null;
                alertsSendValues = new ContentValues();
                alertsSendValues.clear();
                alertsSendValues.put(ALERT_FROM_USERID, fromUserId);
                alertsSendValues.put(ALERT_TO_USERID, connectedUserIds.get(i));
                alertsSendValues.put(ALERTTYPEID, alertTypeId);
                alertsSendValues.put(ALERTSTATUS_ID, alertStatusId);
                alertsSendValues.put(ALERT_KEY, alertKey);
                alertsSendValues.put(ALERT_CREATED_DATETIME,
                        DateTimeProcessor.GetCurrentFormattedDateTime("yyyyMMdd_HHmmss"));
                // alertsSendValues.put(ALERT_SEND_DATETIME,
                // DateTimeProcessor.GetCurrentFormattedDateTime("yyyyMMdd_HHmmss"));

                if (gps_positions == null) {
                    alertsSendValues.put(LATITUDE, "");
                    alertsSendValues.put(LONGITUDE, "");
                } else {
                    alertsSendValues.put(LATITUDE, gps_positions[0]);
                    alertsSendValues.put(LONGITUDE, gps_positions[1]);
                }
                if (alertTypeId > 0) {
                    database.insert(TABLE_ALERTS, null, alertsSendValues);
                }

            }
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor::insertAlerts()", PMWF_Log.getStringFromStackTrace(ex));
        }
    }

    public String getAlertInfo(String alertStatusDescription) { // retrieved the
                                                                // alert info
                                                                // according to
                                                                // alert status
                                                                // description
        Cursor cursor = null;
        JSONArray sendAlertArray = null;
        try {
            int statusId = getAlertStatusId(alertStatusDescription);
            int userId = getUserId(ApplicationSettingsFilePath.AccountName);

            cursor = database.query(TABLE_ALERTS, null, ALERTSTATUS_ID + " = '" + statusId + "'and "
                    + ALERT_FROM_USERID + " = '" + userId + "'", null, null, null, null);
            int rowCount = cursor.getCount();
            if (rowCount > 0) {
                sendAlertArray = new JSONArray();
                int Column1 = cursor.getColumnIndex(ALERT_FROM_USERID);
                int Column2 = cursor.getColumnIndex(ALERT_TO_USERID);
                int Column3 = cursor.getColumnIndex(ALERTTYPEID);
                int Column4 = cursor.getColumnIndex(LATITUDE);
                int Column5 = cursor.getColumnIndex(LONGITUDE);
                int Column6 = cursor.getColumnIndex(ALERT_KEY);

                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    int alertFromUserId = cursor.getInt(Column1);
                    int alertToUserId = cursor.getInt(Column2);
                    int alertTypeId = cursor.getInt(Column3);
                    String latitude = cursor.getString(Column4);
                    String longitude = cursor.getString(Column5);
                    String alertKey = cursor.getString(Column6);
                    String fromUserGuid = getGuid(alertFromUserId + "");
                    String toUserGuid = getGuid(alertToUserId + "");
                    // String alertType = getAlertDescription(alertTypeId);

                    JSONObject obj = new JSONObject();
                    obj.put("FromUserGuid", fromUserGuid);
                    obj.put("ToUserGuid", toUserGuid);
                    obj.put("AlertType", alertTypeId);
                    obj.put("AlertKey", alertKey);
                    obj.put("AlertStatus", "Pending");
                    obj.put("Latitude", latitude);
                    obj.put("Longitude", longitude);
                    obj.put("AlertSendDateTime", DateTimeProcessor.GetCurrentFormattedDateTime("yyyyMMdd_HHmmss"));

                    sendAlertArray.put(obj);

                    cursor.moveToNext();
                    // break;
                }
            }
        } catch (Exception e) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor::getAlertInfo()", PMWF_Log.getStringFromStackTrace(e));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return sendAlertArray != null ? sendAlertArray.toString() : null;
    }

    public String getAcceptedRejectedAlerts() {
        Cursor cursor = null;
        JSONArray sendAlertArray = null;
        try {
            int acceptedStatusId = getAlertStatusId("Accepted");
            int rejectedStatusId = getAlertStatusId("Rejected");
            int userId = getUserId(ApplicationSettingsFilePath.AccountName);

            cursor = database.query(TABLE_ALERTS, null, ALERTSTATUS_ID + " in ('" + acceptedStatusId + "', '"
                    + rejectedStatusId + "') and " + STATUS_FLAG + " = '" + 0 + "'" + " and " + ALERT_TO_USERID
                    + " = '" + userId + "'", null, null, null, null);

            int rowCount = cursor.getCount();
            if (rowCount > 0) {
                sendAlertArray = new JSONArray();
                int Column1 = cursor.getColumnIndex(ALERT_FROM_USERID);
                int Column2 = cursor.getColumnIndex(ALERT_TO_USERID);
                int Column3 = cursor.getColumnIndex(ALERTTYPEID);
                int Column4 = cursor.getColumnIndex(ALERTSTATUS_ID);
                int Column5 = cursor.getColumnIndex(ALERT_KEY);
                int Column6 = cursor.getColumnIndex(ALERT_GUID);

                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    // cursor.moveToFirst();
                    int alertFromUserId = cursor.getInt(Column1);
                    int alertToUserId = cursor.getInt(Column2);
                    int alertTypeId = cursor.getInt(Column3);
                    int alertStatusId = cursor.getInt(Column4);
                    String alertKey = cursor.getString(Column5);
                    String guid = cursor.getString(Column6);
                    String fromUserGuid = getGuid(alertFromUserId + "");
                    String toUserGuid = getGuid(alertToUserId + "");
                    // String alertType = getAlertDescription(alertTypeId);

                    String alertStatus = getStatusDescription(alertStatusId);
                    JSONObject obj = new JSONObject();
                    obj.put("FromUserGuid", fromUserGuid);
                    obj.put("ToUserGuid", toUserGuid);
                    obj.put("Guid", guid);
                    obj.put("AlertType", alertTypeId);
                    obj.put("AlertKey", alertKey);
                    obj.put("AlertStatus", alertStatus);
                    obj.put("AlertReceivedDateTime", DateTimeProcessor.GetCurrentFormattedDateTime("yyyyMMdd_HHmmss"));

                    sendAlertArray.put(obj);

                    cursor.moveToNext();
                    // break;
                }
            }
        } catch (Exception e) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor::getAcceptedRejectedAlerts()",
                    PMWF_Log.getStringFromStackTrace(e));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return sendAlertArray != null ? sendAlertArray.toString() : null;
    }

    public int getUserId(String emailAddress) { // get userid according to email
                                                // address
        // emailAddress.toLowerCase().toString();

        Cursor cursor = null;
        int userId = 0;
        try {
            // cursor = database.rawQuery("select * from " + TABLE_USERS +
            // " where EMAILADDRESS = ? ", new String[] { emailAddress });
            cursor = database.query(TABLE_USERS, null, EMAILADDRESS + " = ? ", new String[] { emailAddress }, null,
                    null, null);

            int Column1 = cursor.getColumnIndex(USER_ID);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                userId = cursor.getInt(Column1);
                cursor.moveToNext();
            }
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor::getUserId()", PMWF_Log.getStringFromStackTrace(ex));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return userId;
    }

    public ArrayList<String> getUserName(String userId) { // get userinformation
                                                          // through userid
        Cursor cursor = null;
        ArrayList<String> name = new ArrayList<String>();
        try {
            cursor = database.query(TABLE_USERS, null, USER_ID + " = ? ", new String[] { userId }, null, null, null);
            int rowCount = cursor.getCount();

            if (rowCount > 0) {
                int Column1 = cursor.getColumnIndex(LASTNAME);
                int Column2 = cursor.getColumnIndex(FIRSTNAME);
                int Column3 = cursor.getColumnIndex(EMAILADDRESS);
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {

                    if (cursor.getString(Column1) == null) {
                        name.add("");
                    } else {
                        name.add(cursor.getString(Column1));
                    }

                    if (cursor.getString(Column2) == null) {
                        name.add("");
                    } else {
                        name.add(cursor.getString(Column2));
                    }

                    name.add(cursor.getString(Column3));
                    cursor.moveToNext();
                }
            }
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor::getUserName()", PMWF_Log.getStringFromStackTrace(ex));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return name;
    }

    public String getUserEmailAddress(String userId) {
        Cursor cursor = null;
        String emailAddress = null;
        try {

            cursor = database.query(TABLE_USERS, null, USER_ID + " = ?", new String[] { userId }, null, null, null);

            int Column1 = cursor.getColumnIndex(EMAILADDRESS);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                emailAddress = cursor.getString(Column1);
                cursor.moveToNext();
            }
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor::getUserEmailAddress()", PMWF_Log.getStringFromStackTrace(ex));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return emailAddress;
    }

    public String getGuid(String usersId) {
        Cursor cursor = null;
        String guid = "";
        try {
            cursor = database.query(TABLE_USERS, null, USER_ID + "='" + usersId + "'", null, null, null, null);

            int Column1 = cursor.getColumnIndex(USER_GUID);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                guid = cursor.getString(Column1);
                cursor.moveToNext();
            }
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor::getGuid()", PMWF_Log.getStringFromStackTrace(ex));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return guid;
    }

    public int geteUserId(String guid) {
        Cursor cursor = null;
        int userId = 0;
        try {
            cursor = database.query(TABLE_USERS, null, USER_GUID + "='" + guid + "'", null, null, null, null);

            int Column1 = cursor.getColumnIndex(USER_ID);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                userId = cursor.getInt(Column1);
                cursor.moveToNext();
            }
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor::geteUserId()", PMWF_Log.getStringFromStackTrace(ex));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return userId;
    }

    public String getGuidFromEmail(String emailAddress) {
        Cursor cursor = null;
        String guid = "";
        try {
            cursor = database
                    .query(TABLE_USERS, null, EMAILADDRESS + "='" + emailAddress + "'", null, null, null, null);

            int rowcount = cursor.getCount();
            if (rowcount > 0) {
                int Column1 = cursor.getColumnIndex(USER_GUID);
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    guid = cursor.getString(Column1);
                    cursor.moveToNext();
                }
            }
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor::getGuid()", PMWF_Log.getStringFromStackTrace(ex));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return guid;
    }

    public void getSendAlertPushResponse(String serverResponse) { // push
                                                                  // pending
                                                                  // alerts to
                                                                  // server

        try {
            JSONObject userJson = new JSONObject(serverResponse);
            String pullResult = userJson.getString("PushResult");
            JSONObject pullResultObj = new JSONObject(pullResult);
            String result = pullResultObj.getString("Result");
            if (!result.equalsIgnoreCase("null")) {
                JSONArray resultObj = new JSONArray(result);

                for (int i = 0; i < resultObj.length(); i++) {

                    JSONObject uniObject = resultObj.getJSONObject(i);
                    // String alertStatus = uniObject.getString("AlertStatus");
                    int alertType = uniObject.getInt("AlertType");

                    String alertKey = uniObject.getString("AlertKey");
                    String FromUserGuid = uniObject.getString("FromUserGuid");
                    String ToUserGuid = uniObject.getString("ToUserGuid");
                    String AlertStatus = uniObject.getString("AlertStatus");
                    if (AlertStatus.equalsIgnoreCase("Pending")) {
                        AlertStatus = "Sent";
                    }
                    String AlertAcknowledgeDateTime = uniObject.getString("AlertAcknowledgeDateTime");
                    if (AlertAcknowledgeDateTime == null) {
                        AlertAcknowledgeDateTime = "";
                    }
                    String AlertReceivedDateTime = uniObject.getString("AlertReceivedDateTime");
                    if (AlertReceivedDateTime == null) {
                        AlertReceivedDateTime = "";
                    }
                    String AlertSendDateTime = uniObject.getString("AlertSendDateTime");
                    String Guid = uniObject.getString("Guid");

                    int fromUserId = getId(FromUserGuid);
                    int toUserId = getId(ToUserGuid);
                    // int alertTypeId = getAlertTypeId(alertType);
                    // int alertStatusId = getAlertStatusId("Sent");
                    int alertStatusId = getAlertStatusId(AlertStatus);

                    updatePendingAlertsInfo(fromUserId, toUserId, alertType, alertKey, Guid, alertStatusId,
                            AlertSendDateTime, AlertReceivedDateTime, AlertAcknowledgeDateTime);

                }
            }

        } catch (Exception e) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor::getSendAlertResponse()", PMWF_Log.getStringFromStackTrace(e));
        }

    }

    public void updatePendingAlertsInfo(int fromUserId, int toUserId, int AlertTypeid, String alertKey, String Guid,
            int alertStatusId, String AlertSendDateTime, String AlertReceivedDateTime, String AlertAcknowledgeDateTime) {
        Cursor cursor = null;
        try {

            ContentValues alertsSendValues = null;
            alertsSendValues = new ContentValues();
            alertsSendValues.clear();
            alertsSendValues.put(ALERT_FROM_USERID, fromUserId);
            alertsSendValues.put(ALERT_TO_USERID, toUserId);
            alertsSendValues.put(ALERTTYPEID, AlertTypeid);
            alertsSendValues.put(ALERT_KEY, alertKey);
            alertsSendValues.put(ALERTSTATUS_ID, alertStatusId);
            alertsSendValues.put(USER_GUID, Guid);
            alertsSendValues.put(ALERT_SEND_DATETIME, DateTimeProcessor.GetCurrentFormattedDateTime("yyyyMMdd_HHmmss"));

            cursor = database.query(TABLE_ALERTS, null, ALERT_FROM_USERID + " = ? AND " + ALERT_TO_USERID + " = ? AND "
                    + ALERTTYPEID + " = ? AND " + ALERT_KEY + " = ? ", new String[] { fromUserId + "", toUserId + "",
                    AlertTypeid + "", alertKey + "" }, null, null, null, null);
            int rowCount = cursor.getCount();

            if (rowCount == 0) {

                database.insert(TABLE_ALERTS, null, alertsSendValues);
            } else {

                database.update(TABLE_ALERTS, alertsSendValues, ALERT_FROM_USERID + " = ? AND " + ALERT_TO_USERID
                        + " = ? AND " + ALERTTYPEID + " = ? AND " + ALERT_KEY + " = ?", new String[] { fromUserId + "",
                        toUserId + "", AlertTypeid + "", alertKey + "" });
            }

        } catch (Exception e) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor::updateAlertsInfo()", PMWF_Log.getStringFromStackTrace(e));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public void getSeenAlertPullResponse(String serverResponse) { // get pull
                                                                  // response
                                                                  // for
                                                                  // seen(Received)
                                                                  // status

        try {
            JSONObject uniObject = new JSONObject(serverResponse);
            /*
             * String pullResult = userJson.getString("PullResult"); JSONObject
             * pullResultObj = new JSONObject(pullResult); String result =
             * pullResultObj.getString("Result"); if
             * (!result.equalsIgnoreCase("null")) { JSONArray resultObj = new
             * JSONArray(result);
             * 
             * for (int i = 0; i < resultObj.length(); i++) {
             * 
             * JSONObject uniObject = resultObj.getJSONObject(i);
             */
            // String alertStatus = uniObject.getString("AlertStatus");
            int alertType = uniObject.getInt("AlertType");

            String alertKey = uniObject.getString("AlertKey");
            String FromUserGuid = uniObject.getString("FromUserGuid");
            String ToUserGuid = uniObject.getString("ToUserGuid");
            String AlertStatus = uniObject.getString("AlertStatus");
            /*
             * if (AlertStatus.equalsIgnoreCase("Received")) { AlertStatus =
             * "Seen"; }
             */
            String AlertAcknowledgeDateTime = uniObject.getString("AlertAcknowledgeDateTime");
            if (AlertAcknowledgeDateTime == null) {
                AlertAcknowledgeDateTime = "";
            }
            String AlertReceivedDateTime = uniObject.getString("AlertReceivedDateTime");
            if (AlertReceivedDateTime == null) {
                AlertReceivedDateTime = "";
            }
            String AlertSendDateTime = uniObject.getString("AlertSendDateTime");
            String Guid = uniObject.getString("Guid");

            int fromUserId = getId(FromUserGuid);
            int toUserId = getId(ToUserGuid);
            // int alertTypeId = getAlertTypeId(alertType);
            // int alertStatusId = getAlertStatusId("Sent");
            int alertStatusId = getAlertStatusId(AlertStatus);

            updateSeenAlertsInfo(fromUserId, toUserId, alertType, alertKey, Guid, alertStatusId, AlertSendDateTime,
                    AlertReceivedDateTime, AlertAcknowledgeDateTime);

            // }
            // }

        } catch (Exception e) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor::getResponse()", PMWF_Log.getStringFromStackTrace(e));
            Log.i("UserCursor::getResponse", e.getMessage());
        }

    }

    public void updateSeenAlertsInfo(int fromUserId, int toUserId, int AlertTypeid, String alertKey, String Guid,
            int alertStatusId, String AlertSendDateTime, String AlertReceivedDateTime, String AlertAcknowledgeDateTime) {
        Cursor cursor = null;
        try {

            ContentValues alertsSendValues = null;
            alertsSendValues = new ContentValues();
            alertsSendValues.clear();
            // alertsSendValues.put(ALERT_FROM_USERID, fromUserId);
            // alertsSendValues.put(ALERT_TO_USERID, toUserId);
            // alertsSendValues.put(ALERTTYPEID, AlertTypeid);
            // alertsSendValues.put(ALERT_KEY, alertKey);
            alertsSendValues.put(ALERTSTATUS_ID, alertStatusId);
            // alertsSendValues.put(USER_GUID, Guid);
            // alertsSendValues.put(ALERT_SEND_DATETIME,
            // DateTimeProcessor.GetCurrentFormattedDateTime("yyyyMMdd_HHmmss"));

            cursor = database.query(TABLE_ALERTS, null, ALERT_FROM_USERID + " = ? AND " + ALERT_TO_USERID + " = ? AND "
                    + ALERTTYPEID + " = ? AND " + ALERT_KEY + " = ? ", new String[] { fromUserId + "", toUserId + "",
                    AlertTypeid + "", alertKey + "" }, null, null, null, null);
            int rowCount = cursor.getCount();

            if (rowCount > 0) {
                // alertsSendValues.put(ALERT_SEND_DATETIME,
                // DateTimeProcessor.GetCurrentFormattedDateTime("yyyyMMdd_HHmmss"));
                // database.insert(TABLE_ALERTS, null, alertsSendValues);
                // } else {
                // int Column1 = cursor.getColumnIndex(ALERT_ID);
                // int alertId = cursor.getInt(Column1);
                database.update(TABLE_ALERTS, alertsSendValues, ALERT_FROM_USERID + " = ? AND " + ALERT_TO_USERID
                        + " = ? AND " + ALERTTYPEID + " = ? AND " + ALERT_KEY + " = ?", new String[] { fromUserId + "",
                        toUserId + "", AlertTypeid + "", alertKey + "" });
            }

        } catch (Exception e) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor::updateAlertsInfo()", PMWF_Log.getStringFromStackTrace(e));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public void updateAcceptedRejectedAlerts(String response) {
        // get send alert response from server and change pending status with id
        // 1 to send status id(2)

        try {
            JSONObject uniObject = new JSONObject(response);

            int alertType = uniObject.getInt("AlertType");

            String alertKey = uniObject.getString("AlertKey");
            String FromUserGuid = uniObject.getString("FromUserGuid");
            String ToUserGuid = uniObject.getString("ToUserGuid");
            String alertStatus = uniObject.getString("AlertStatus");
            String Guid = uniObject.getString("Guid");

            int fromUserId = getId(FromUserGuid);
            int toUserId = getId(ToUserGuid);
            int alertStatusId = getAlertStatusId(alertStatus);

            updateAcceptedRejectedAlerts(fromUserId, toUserId, alertType, alertKey, Guid, alertStatusId);

        } catch (Exception e) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor::getResponse()", PMWF_Log.getStringFromStackTrace(e));
        }

    }

    public void updateAcceptedRejectedAlerts(int fromUserId, int toUserId, int alertType, String alertKey, String Guid,
            int alertStatusId) {
        Cursor cursor = null;
        try {

            ContentValues alertsSendValues = null;
            alertsSendValues = new ContentValues();
            alertsSendValues.clear();
            alertsSendValues.put(ALERTSTATUS_ID, alertStatusId);

            cursor = database.query(TABLE_ALERTS, null, ALERT_FROM_USERID + " = ? AND " + ALERT_TO_USERID + " = ? AND "
                    + ALERTTYPEID + " = ? AND " + ALERT_KEY + " = ? AND " + ALERT_GUID + " = ? ", new String[] {
                    fromUserId + "", toUserId + "", alertType + "", alertKey + "", Guid + "" }, null, null, null, null);
            int rowCount = cursor.getCount();

            if (rowCount > 0) {
                // alertsSendValues.put(ALERT_SEND_DATETIME,
                // DateTimeProcessor.GetCurrentFormattedDateTime("yyyyMMdd_HHmmss"));
                // database.insert(TABLE_ALERTS, null, alertsSendValues);
                // } else {
                // int Column1 = cursor.getColumnIndex(ALERT_ID);
                // int alertId = cursor.getInt(Column1);
                database.update(TABLE_ALERTS, alertsSendValues, ALERT_FROM_USERID + " = ? AND " + ALERT_TO_USERID
                        + " = ? AND " + ALERTTYPEID + " = ? AND " + ALERT_KEY + " = ?", new String[] { fromUserId + "",
                        toUserId + "", alertType + "", alertKey + "" });
            }

        } catch (Exception e) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor::updateAlertsInfo()", PMWF_Log.getStringFromStackTrace(e));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

    }

    public int getId(String fromGuid) {
        Cursor cursor = null;
        int id = 0;
        try {
            cursor = database.query(TABLE_USERS, null, USER_GUID + "='" + fromGuid + "'", null, null, null, null);

            int Column1 = cursor.getColumnIndex(USER_ID);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                id = cursor.getInt(Column1);
                cursor.moveToNext();
            }
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor::getId()", PMWF_Log.getStringFromStackTrace(ex));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return id;
    }

    public int getAlertTypeId(String alertDescription) {
        Cursor cursor = null;
        int alertTypeId = 0;
        try {
            cursor = database.query(TABLE_ALERTTYPES, allAlertTypesColumns, ALERTTYPE_DESCRIPTION + " = '"
                    + alertDescription + "'", null, null, null, null);
            int Column1 = cursor.getColumnIndex(ALERTTYPE_ID);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                alertTypeId = cursor.getInt(Column1);
                cursor.moveToNext();
            }

        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor::getAlertTypeId()", PMWF_Log.getStringFromStackTrace(ex));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return alertTypeId;

    }

    public String getAlertDescription(int alertTypeId) {
        Cursor cursor = null;
        String alertType = null;
        try {
            cursor = database.query(TABLE_ALERTTYPES, allAlertTypesColumns, ALERTTYPE_ID + " = ? and "
                    + ALERTTYPE_LOCALE + " = ? ", new String[] { alertTypeId + "",
                    ApplicationSettingsFilePath.language + "" }, null, null, null, null);
            int Column1 = cursor.getColumnIndex(ALERTTYPE_DESCRIPTION);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                alertType = cursor.getString(Column1);
                cursor.moveToNext();
            }
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor::getAlertDescription()", PMWF_Log.getStringFromStackTrace(ex));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return alertType;

    }

    public int getAlertStatusId(String alertStatusDescription) {
        Cursor cursor = null;
        int alertStatusId = 0;
        try {
            cursor = database.query(TABLE_ALERTS_STATUS, allAlertStatusesColumns, STATUS_DESCRIPTION + " = '"
                    + alertStatusDescription + "'", null, null, null, null);
            int Column1 = cursor.getColumnIndex(ALERT_STATUS_ID);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                alertStatusId = cursor.getInt(Column1);
                cursor.moveToNext();
            }
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor::getAlertStatusId()", PMWF_Log.getStringFromStackTrace(ex));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return alertStatusId;

    }

    public ArrayList<String> loadAlertItems() { // viewing alert items in
                                                // alertactivity
        Cursor cursor = null;
        ArrayList<String> alertList = null;
        try {
            alertList = new ArrayList<String>();
            cursor = database.query(TABLE_ALERTTYPES, allAlertTypesColumns, ALERTTYPE_LOCALE + " = ? ",
                    new String[] { ApplicationSettingsFilePath.language + "" }, null, null, null);
            int alertIndex = cursor.getColumnIndex(ALERTTYPE_DESCRIPTION);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String alertDescription = cursor.getString(alertIndex);
                alertList.add(alertDescription);
                cursor.moveToNext();
            }
            return alertList;
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor::loadAlertItems()", PMWF_Log.getStringFromStackTrace(ex));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    public ArrayList<Integer> loadAcceptedAlertItems(int id) {// to show alert
                                                              // item that can
                                                              // be accepted
                                                              // by user
        Cursor cursor = null;
        ArrayList<Integer> alertList = new ArrayList<Integer>();
        try {
            String query = "select distinct(AlerttypeId) from userconnections where IsAccepted=1 AND  FromUserId=" + id
                    + " AND ToUserId>0";
            cursor = database.rawQuery(query, null);
            int alertTypeId = cursor.getColumnIndex(ALERTTYPEID);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int alertType = cursor.getInt(alertTypeId);
                alertList.add(alertType);
                cursor.moveToNext();
            }
            return alertList;
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor::loadAcceptedAlertItems()", PMWF_Log.getStringFromStackTrace(ex));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return alertList;

    }

    public String getStatusDescription(int alertStatusId) {
        Cursor cursor = null;
        String alertStatus = null;
        try {
            cursor = database.query(TABLE_ALERTS_STATUS, allAlertStatusesColumns, ALERTSTATUS_ID + " = '"
                    + alertStatusId + "" + "'", null, null, null, null);
            int Column1 = cursor.getColumnIndex(STATUS_DESCRIPTION);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                alertStatus = cursor.getString(Column1);
                cursor.moveToNext();
            }
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor::getStatusDescription()", PMWF_Log.getStringFromStackTrace(ex));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return alertStatus;

    }

    public ArrayList<Integer> getContactCount(String conditions, String[] whereValues) { // get
                                                                                         // contact
                                                                                         // count
                                                                                         // for
                                                                                         // particular
                                                                                         // alert
                                                                                         // type
                                                                                         // selected
        Cursor cursor = null;
        ArrayList<Integer> list = new ArrayList<Integer>();

        try {

            cursor = database.query(TABLE_USER_CONNECTIONS, null, conditions, whereValues, null, null, null);
            int column1 = cursor.getColumnIndex(TO_USER_ID);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(column1);
                String mainUserEmailAddress = getUserEmailAddress(Integer.toString(id));
                if (mainUserEmailAddress != null
                        && !mainUserEmailAddress.equalsIgnoreCase(ApplicationSettingsFilePath.AccountName)) {
                    list.add(id);
                }
                cursor.moveToNext();
            }

        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor: getContactCount()", PMWF_Log.getStringFromStackTrace(ex));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return list;
    }

    public ArrayList<Person> getAlertList() { // get all the send alert list
        Cursor cursor = null;
        ArrayList<Person> m_data = new ArrayList<Person>();
        ArrayList<String> usersInfo = new ArrayList<String>();
        // int alertStatusId = getAlertStatusId("Received");

        try {
            int alertStatusId1 = getAlertStatusId("Pending");
            int alertStatusId2 = getAlertStatusId("Sent");
            int alertStatusId3 = getAlertStatusId("Seen");
            int alertStatusId4 = getAlertStatusId("Accepted");
            int alertStatusId5 = getAlertStatusId("Rejected");
            int mainUserId = getUserId(ApplicationSettingsFilePath.AccountName);

            // select * from Alerts where AlertStatusId
            // !=alertStatusId(Received) and AlertFromUserid=mainuserId
            /*
             * cursor = database.query(TABLE_ALERTS, null, ALERTSTATUS_ID +
             * " <> ? and " + ALERT_FROM_USERID + " = ? ", new String[] {
             * alertStatusId + "", mainUserId + "" }, null, null,
             * ALERT_CREATED_DATETIME + " DESC", null);
             */// cursor = database.query(TABLE_ALERTS, null,null,null, null,
               // null,
               // null, null);
            cursor = database.query(TABLE_ALERTS, null, ALERTSTATUS_ID + " in(?,?,?,?,?)  and " + ALERT_FROM_USERID
                    + " = ? ", new String[] {

            alertStatusId1 + "", alertStatusId2 + "", alertStatusId3 + "", alertStatusId4 + "", alertStatusId5 + "",
                    mainUserId + "" }, null, null, ALERT_CREATED_DATETIME + " DESC", null);

            int rowcount = cursor.getCount();

            if (rowcount > 0) {
                int column1 = cursor.getColumnIndex(ALERT_TO_USERID);
                int column2 = cursor.getColumnIndex(ALERT_SEND_DATETIME);
                int column3 = cursor.getColumnIndex(ALERTTYPEID);
                int column4 = cursor.getColumnIndex(ALERTSTATUS_ID);
                int column5 = cursor.getColumnIndex(LATITUDE);
                int column6 = cursor.getColumnIndex(LONGITUDE);
                int column7 = cursor.getColumnIndex(ALERT_TO_USERID);
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    try {
                        String alertToUserId = cursor.getString(column1);
                        usersInfo = getUserName(alertToUserId);

                        String alertStatus = getStatusDescription(cursor.getInt(column4));
                        String alertType = getAlertDescription(cursor.getInt(column3));
                        String fullDateTime = cursor.getString(column2);
                        String latitude = cursor.getString(column5);
                        String longitude = cursor.getString(column6);
                        int toUserID = cursor.getInt(column7);
                        String toUserGuid = getGuid(toUserID + "");

                        String formattedDateTime = DateTimeProcessor.getFormattedDateTimeValue(fullDateTime);

                        Person arrperson = new Person();
                        arrperson.setContactLastName(usersInfo.get(0));
                        arrperson.setContactFirstName(usersInfo.get(1));
                        arrperson.setAlertSendDateTimes(formattedDateTime);
                        arrperson.setContactAlertType(alertType);
                        arrperson.setAlertStatus(alertStatus);
                        arrperson.setLatitude(latitude);
                        arrperson.setLongitude(longitude);
                        arrperson.setUserStatus(getContactStatusId(toUserGuid));
                        m_data.add(arrperson);
                    } catch (Exception ex) {
                        PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor: getAlertList(0)",
                                PMWF_Log.getStringFromStackTrace(ex));
                    }
                    cursor.moveToNext();

                }
                return m_data;
            }
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor: getAlertList()", PMWF_Log.getStringFromStackTrace(ex));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return null;
    }

    public boolean getAlertListWithStatus(String alertStatus) {
        Cursor cursor = null;

        try {
            int alertStatusId = getAlertStatusId(alertStatus);
            int mainUserId = getUserId(ApplicationSettingsFilePath.AccountName);

            cursor = database.query(TABLE_ALERTS, null, ALERTSTATUS_ID + " = ?  and " + ALERT_FROM_USERID + " = ? ",
                    new String[] { alertStatusId + "", mainUserId + "" }, null, null, null, null);

            int rowcount = cursor.getCount();

            if (rowcount > 0) {
                return true;
            }
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor: getAlertListWithStatus()", PMWF_Log.getStringFromStackTrace(ex));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return false;
    }

    public ArrayList<Person> getAlertRecieved() { // get alert received details
                                                  // for the pop up from
                                                  // notification
        Cursor cursor = null;
        ArrayList<Person> m_data = new ArrayList<Person>();
        ArrayList<String> usersInfo = new ArrayList<String>();
        int alertStatusId = getAlertStatusId("Received");
        int mainUserId = getUserId(ApplicationSettingsFilePath.AccountName);

        try {
            cursor = database.query(TABLE_ALERTS, null, ALERTSTATUS_ID + " = ? and " + ALERT_TO_USERID + " = ? ",
                    new String[] { alertStatusId + "", mainUserId + "" }, null, null,
                    ALERT_RECEIVED_DATETIME + " DESC", 1 + "");

            int rowcount = cursor.getCount();

            if (rowcount > 0) {

                int column1 = cursor.getColumnIndex(ALERT_FROM_USERID);
                int column2 = cursor.getColumnIndex(ALERT_RECEIVED_DATETIME);
                int column3 = cursor.getColumnIndex(ALERTTYPEID);
                int column4 = cursor.getColumnIndex(ALERTSTATUS_ID);
                int column5 = cursor.getColumnIndex(LATITUDE);
                int column6 = cursor.getColumnIndex(LONGITUDE);

                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    String alertFromUserId = cursor.getString(column1);
                    usersInfo = getUserName(alertFromUserId);

                    String alertStatus = getStatusDescription(cursor.getInt(column4));
                    String alertType = getAlertDescription(cursor.getInt(column3));
                    String latitude = cursor.getString(column5);
                    String longitude = cursor.getString(column6);

                    String formattedDateTime = DateTimeProcessor.getFormattedDateTimeValue(cursor.getString(column2));
                    Person arrperson = new Person();
                    arrperson.setContactLastName(usersInfo.get(0));
                    arrperson.setContactFirstName(usersInfo.get(1));
                    arrperson.setAlertSendDateTimes(formattedDateTime);
                    arrperson.setContactAlertType(alertType);
                    arrperson.setAlertStatus(alertStatus);
                    arrperson.setLatitude(latitude);
                    arrperson.setLongitude(longitude);
                    m_data.add(arrperson);
                    cursor.moveToNext();
                }
                return m_data;
            }
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor: getAlertRecieved()", PMWF_Log.getStringFromStackTrace(ex));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    public ArrayList<Person> getAllAlertRecievedList() { // retrieve all alert
                                                         // received list
        Cursor cursor = null;
        ArrayList<Person> m_data = new ArrayList<Person>();
        ArrayList<String> usersInfo = new ArrayList<String>();

        // cursor = database.query(TABLE_ALERTS, null, ALERTSTATUS_ID + " = ? ",
        // new String[] {alertStatusId + "" }, null, null, null, null);

        try {
            int alertStatusId = getAlertStatusId("Received");
            int alertAcceptedStatusId = getAlertStatusId("Accepted");
            int alertRejectedStatusId = getAlertStatusId("Rejected");

            int mainUserId = getUserId(ApplicationSettingsFilePath.AccountName);

            cursor = database.query(TABLE_ALERTS, null, ALERTSTATUS_ID + " in (?,?,?) " + " and " + ALERT_TO_USERID
                    + " = ? ", new String[] { alertStatusId + "", alertAcceptedStatusId + "",
                    alertRejectedStatusId + "", mainUserId + "" }, null, null, ALERT_RECEIVED_DATETIME + " DESC", null);
            // cursor = database.query(TABLE_ALERTS, null,null,null, null, null,
            // null, null);
            int rowcount = cursor.getCount();

            if (rowcount > 0) {
                int column1 = cursor.getColumnIndex(ALERT_FROM_USERID);
                int column2 = cursor.getColumnIndex(ALERT_RECEIVED_DATETIME);
                int column3 = cursor.getColumnIndex(ALERTTYPEID);
                int column4 = cursor.getColumnIndex(ALERTSTATUS_ID);
                int column5 = cursor.getColumnIndex(LATITUDE);
                int column6 = cursor.getColumnIndex(LONGITUDE);

                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    try {
                        String alertFromUserId = cursor.getString(column1);
                        usersInfo = getUserName(alertFromUserId);

                        String alertStatus = getStatusDescription(cursor.getInt(column4));
                        String alertType = getAlertDescription(cursor.getInt(column3));
                        String latitude = cursor.getString(column5);
                        String longitude = cursor.getString(column6);

                        String formattedDateTime = DateTimeProcessor.getFormattedDateTimeValue(cursor
                                .getString(column2));
                        Person arrperson = new Person();
                        arrperson.setContactLastName(usersInfo.get(0));
                        arrperson.setContactFirstName(usersInfo.get(1));
                        arrperson.setAlertSendDateTimes(formattedDateTime);
                        arrperson.setContactAlertType(alertType);
                        arrperson.setAlertStatus(alertStatus);
                        arrperson.setLatitude(latitude);
                        arrperson.setLongitude(longitude);
                        m_data.add(arrperson);
                    } catch (Exception ex) {
                        PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor: getAllAlertRecievedList(0)",
                                PMWF_Log.getStringFromStackTrace(ex));
                    }
                    cursor.moveToNext();
                }
                return m_data;
            }
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor: getAllAlertRecievedList()",
                    PMWF_Log.getStringFromStackTrace(ex));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    // public void insertRecieverInfo(String alertSenderInfo) { // alerts
    // recieved

    /*
     * public void insertRecieverInfo(ArrayList<AlertInfo> obj) { // alerts //
     * recieved // if reciever send alert to maincontact
     * 
     * try { // JSONArray alertRecieveByUseInfo = new
     * JSONArray(alertSenderInfo); // for (int i = 0; i <
     * alertRecieveByUseInfo.length(); i++) {
     * 
     * // JSONObject obj = alertRecieveByUseInfo.getJSONObject(i); // String
     * alertType = obj.getString("AlertType"); for (AlertInfo alertList : obj) {
     * int alertType = alertList.getAlertType(); String fromUserGuid =
     * alertList.getFromUserGuid(); String toUserGuid =
     * alertList.getToUserGuid();
     * 
     * String Guid = alertList.getGuid(); String latitude =
     * alertList.getLatitude(); String longitude = alertList.getLongitude();
     * 
     * // int alertTypeId = getAlertTypeId(alertType); int fromUserId =
     * getId(fromUserGuid); int toUserId = getId(toUserGuid); ContentValues
     * alertsValues = new ContentValues();
     * 
     * alertsValues.clear(); alertsValues.put(ALERT_FROM_USERID, fromUserId);
     * alertsValues.put(ALERT_TO_USERID, toUserId);
     * alertsValues.put(ALERTTYPEID, alertType); alertsValues.put(ALERT_GUID,
     * Guid); alertsValues.put(LATITUDE, latitude); alertsValues.put(LONGITUDE,
     * longitude); alertsValues.put(ALERTSTATUS_ID,
     * getAlertStatusId("Received")); alertsValues.put(STATUS_FLAG, 0);
     * alertsValues.put(ALERT_RECEIVED_DATETIME,
     * DateTimeProcessor.GetCurrentFormattedDateTime("yyyyMMdd_HHmmss"));
     * 
     * database.insert(TABLE_ALERTS, null, alertsValues); } // }
     * 
     * } catch (Exception e) { // TODO Auto-generated catch block
     * e.printStackTrace(); }
     * 
     * }
     */

    public void UpdateReceiverStatus(ArrayList<AlertInfo> obj, String alertStatus) { // alerts
        // recieved
        // if reciever send alert to maincontact

        try {
            // JSONArray alertRecieveByUseInfo = new JSONArray(alertSenderInfo);
            // for (int i = 0; i < alertRecieveByUseInfo.length(); i++) {

            // JSONObject obj = alertRecieveByUseInfo.getJSONObject(i);
            // String alertType = obj.getString("AlertType");
            for (AlertInfo alertList : obj) {
                // int alertType = alertList.getAlertType();
                String fromUserGuid = alertList.getFromUserGuid();
                String toUserGuid = alertList.getToUserGuid();

                String Guid = alertList.getGuid();
                // String latitude = alertList.getLatitude();
                // String longitude = alertList.getLongitude();

                // int alertTypeId = getAlertTypeId(alertType);
                int fromUserId = getId(fromUserGuid);
                int toUserId = getId(toUserGuid);
                ContentValues alertsValues = new ContentValues();

                alertsValues.clear();
                // alertsValues.put(ALERT_FROM_USERID, fromUserId);
                // alertsValues.put(ALERT_TO_USERID, toUserId);
                // alertsValues.put(ALERTTYPEID, alertType);
                // alertsValues.put(ALERT_GUID, Guid);
                // alertsValues.put(LATITUDE, latitude);
                // alertsValues.put(LONGITUDE, longitude);
                alertsValues.put(ALERTSTATUS_ID, getAlertStatusId(alertStatus));
                int alertStatusId = getAlertStatusId("Received");
                // alertsValues.put(STATUS_FLAG, 0);
                // alertsValues.put(ALERT_RECEIVED_DATETIME,
                // DateTimeProcessor.GetCurrentFormattedDateTime("yyyyMMdd_HHmmss"));

                /*
                 * database.update(TABLE_ALERTS, alertsValues, ALERT_FROM_USERID
                 * + " = ? AND " + ALERT_TO_USERID + " = ? AND " + ALERTTYPEID +
                 * " = ? AND " + ALERT_KEY + " = ? AND " + ALERT_GUID + " = ?",
                 * new String[] { fromUserId + "", toUserId + "", alertStatus +
                 * "", alertKey + "", Guid + "" });
                 */

                database.update(TABLE_ALERTS, alertsValues, ALERT_FROM_USERID + " = ? AND " + ALERT_TO_USERID
                        + " = ? AND " + ALERTSTATUS_ID + " = ? AND " + ALERT_GUID + " = ?", new String[] {
                        fromUserId + "", toUserId + "", alertStatusId + "", Guid + "" });

                // database.insert(TABLE_ALERTS, null, alertsValues);
            }
            // }

        } catch (Exception e) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor: UpdateReceiverStatus()", PMWF_Log.getStringFromStackTrace(e));
        }

    }

    public void insertRecieverInfo(String alertSenderInfo) { // alerts recieved
        // if reciever send alert to maincontact

        try {
            // JSONArray alertRecieveByUseInfo = new JSONArray(alertSenderInfo);
            // for (int i = 0; i < alertRecieveByUseInfo.length(); i++) {

            // JSONObject obj = alertRecieveByUseInfo.getJSONObject(i);
            JSONObject obj = new JSONObject(alertSenderInfo);
            // String alertType = obj.getString("AlertType");
            int alertType = obj.getInt("AlertType");
            String fromUserGuid = obj.getString("FromUserGuid");
            String toUserGuid = obj.getString("ToUserGuid");
            String Guid = obj.getString("Guid");
            String latitude = obj.getString("Latitude");
            String longitude = obj.getString("Longitude");

            // int alertTypeId = getAlertTypeId(alertType);
            int fromUserId = getId(fromUserGuid);
            int toUserId = getId(toUserGuid);
            ContentValues alertsValues = new ContentValues();

            alertsValues.clear();
            alertsValues.put(ALERT_FROM_USERID, fromUserId);
            alertsValues.put(ALERT_TO_USERID, toUserId);
            alertsValues.put(ALERTTYPEID, alertType);
            alertsValues.put(ALERT_GUID, Guid);
            alertsValues.put(LATITUDE, latitude);
            alertsValues.put(LONGITUDE, longitude);
            alertsValues.put(ALERTSTATUS_ID, getAlertStatusId("Received"));
            alertsValues.put(STATUS_FLAG, 0);
            alertsValues.put(ALERT_RECEIVED_DATETIME, DateTimeProcessor.GetCurrentFormattedDateTime("yyyyMMdd_HHmmss"));

            database.insert(TABLE_ALERTS, null, alertsValues);
            // }

        } catch (Exception e) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor: insertRecieverInfo()", PMWF_Log.getStringFromStackTrace(e));
        }

    }

    /*
     * public void insertRecieverInfo(JSONObject obj, String alertStatus) { //
     * alerts // recieved // if reciever send alert to maincontact
     * 
     * try { // JSONArray alertRecieveByUseInfo = new
     * JSONArray(alertSenderInfo); // for (int i = 0; i <
     * alertRecieveByUseInfo.length(); i++) {
     * 
     * // JSONObject obj = alertRecieveByUseInfo.getJSONObject(i); // String
     * alertType = obj.getString("AlertType"); int alertType =
     * obj.getInt("AlertType"); String fromUserGuid =
     * obj.getString("FromUserGuid"); String toUserGuid =
     * obj.getString("ToUserGuid"); String Guid = obj.getString("Guid"); String
     * latitude = obj.getString("Latitude"); String longitude =
     * obj.getString("Longitude");
     * 
     * // int alertTypeId = getAlertTypeId(alertType); int fromUserId =
     * getId(fromUserGuid); int toUserId = getId(toUserGuid); ContentValues
     * alertsValues = new ContentValues();
     * 
     * alertsValues.clear(); alertsValues.put(ALERT_FROM_USERID, fromUserId);
     * alertsValues.put(ALERT_TO_USERID, toUserId);
     * alertsValues.put(ALERTTYPEID, alertType); alertsValues.put(ALERT_GUID,
     * Guid); alertsValues.put(LATITUDE, latitude); alertsValues.put(LONGITUDE,
     * longitude); alertsValues.put(ALERTSTATUS_ID,
     * getAlertStatusId(alertStatus)); alertsValues.put(STATUS_FLAG, 0);
     * alertsValues.put(ALERT_RECEIVED_DATETIME,
     * DateTimeProcessor.GetCurrentFormattedDateTime("yyyyMMdd_HHmmss"));
     * 
     * database.insert(TABLE_ALERTS, null, alertsValues); // }
     * 
     * } catch (JSONException e) { // TODO Auto-generated catch block
     * e.printStackTrace(); }
     * 
     * }
     */

    public void UpdateRecieverInfo(String receiverResponse) { // Update receive
                                                              // flag to
                                                              // database for
                                                              // stop syncing

        try {

            JSONObject response = new JSONObject(receiverResponse);
            String pullResult = response.getString("PushResult");
            JSONObject pullResultObj = new JSONObject(pullResult);
            String result = pullResultObj.getString("Result");
            if (!result.equalsIgnoreCase("null")) {
                JSONArray resultObj = new JSONArray(result);

                for (int i = 0; i < resultObj.length(); i++) {

                    JSONObject uniObject = resultObj.getJSONObject(i);
                    String fromUserGuid = uniObject.getString("FromUserGuid");
                    String toUserGuid = uniObject.getString("ToUserGuid");
                    String Guid = uniObject.getString("Guid");
                    String alertKey = uniObject.getString("AlertKey");
                    String alertStatus = uniObject.getString("AlertStatus");

                    // int alertTypeId = getAlertTypeId(alertType);
                    int fromUserId = getId(fromUserGuid);
                    int toUserId = getId(toUserGuid);
                    ContentValues alertsValues = new ContentValues();

                    alertsValues.clear();
                    alertsValues.put(STATUS_FLAG, 1);

                    database.update(TABLE_ALERTS, alertsValues, ALERT_FROM_USERID + " = ? AND " + ALERT_TO_USERID
                            + " = ? AND " + ALERTTYPEID + " = ? AND " + ALERT_KEY + " = ? AND " + ALERT_GUID + " = ?",
                            new String[] { fromUserId + "", toUserId + "", alertStatus + "", alertKey + "", Guid + "" });
                    // }
                }
            }
        } catch (Exception e) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor: UpdateRecieverInfo()", PMWF_Log.getStringFromStackTrace(e));
        }

    }

    public int getAllContactCount() { // get contact count for alert type
                                      // emergency
        ArrayList<Integer> list = getConnectedContactsForLoginUser();

        return list != null ? list.size() : 0;
    }

    public ArrayList<Integer> getConnectedContactsForLoginUser() {
        Cursor cursor = null;

        ArrayList<Integer> list = new ArrayList<Integer>();
        try {
            int mainUserId = getUserId(ApplicationSettingsFilePath.AccountName);

            String query = "select distinct (" + TO_USER_ID + ")" + " from userConnections where fromuserid = "
                    + mainUserId + " and isAccepted = " + "1";

            cursor = database.rawQuery(query, null);

            int column1 = cursor.getColumnIndex(TO_USER_ID);// for column
                                                            // position
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(column1);
                if (id > 0) {
                    String mainUserEmailAddress = getUserEmailAddress(Integer.toString(id));
                    if (mainUserEmailAddress != null
                            && !mainUserEmailAddress.equalsIgnoreCase(ApplicationSettingsFilePath.AccountName)) {
                        list.add(id);
                    }
                }
                cursor.moveToNext();
            }

        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor: getConnectedContactsForLoginUser()",
                    PMWF_Log.getStringFromStackTrace(ex));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return list;
    }

    public String getModifiedDateFromSqliteDb(String emailAddress) { // to
                                                                     // update
                                                                     // info
                                                                     // from
                                                                     // server
                                                                     // to
                                                                     // sqlite
                                                                     // db
                                                                     // compare
                                                                     // the
                                                                     // modified
                                                                     // date
                                                                     // from
                                                                     // users
                                                                     // table
        String modifiedDate = null;
        Cursor cursor = null;
        try {
            cursor = database.query(TABLE_USERS, null, EMAILADDRESS + " = ? ", new String[] { emailAddress }, null,
                    null, null);

            int Column1 = cursor.getColumnIndex(USER_MODIFIED_DATETIME);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                modifiedDate = cursor.getString(Column1);
                cursor.moveToNext();
            }
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor::getModifiedDateFromSqliteDb()",
                    PMWF_Log.getStringFromStackTrace(ex));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return modifiedDate;
    }

    public ArrayList<String> getEditAlertInfo(String emailAddress, String staus) { // get
                                                                                   // contact's
                                                                                   // selected
                                                                                   // alert
                                                                                   // type
                                                                                   // info
                                                                                   // to
                                                                                   // edit

        ArrayList<String> alertTypeList = new ArrayList<String>();
        Cursor cursor = null;
        try {
            int fromUserId = getUserId(ApplicationSettingsFilePath.AccountName);
            int toUserId = getUserId(emailAddress);
            String[] queryParams = null;
            if (staus.equalsIgnoreCase("Receiver")) {
                queryParams = new String[] { fromUserId + "", toUserId + "" };
            } else if (staus.equalsIgnoreCase("Sender")) {
                queryParams = new String[] { toUserId + "", fromUserId + "" };
                // cursor = database.query(TABLE_USER_CONNECTIONS, null,
                // TO_USER_ID + " = ? and " + FROM_USER_ID + " = ? ", new
                // String[] { fromUserId + "",
                // toUserId + "" }, null, null, null, null);

            }
            cursor = database.query(TABLE_USER_CONNECTIONS, null, FROM_USER_ID + " = ? and " + TO_USER_ID + " = ? ",
                    queryParams, null, null, null, null);
            // int rowcount = cursor.getCount();
            int column1 = cursor.getColumnIndex(ALERTYPE_ID);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int alertTypeId = cursor.getInt(column1);
                /*
                 * String alertDescription = getAlertDescription(alertTypeId);
                 * alertTypeList.add(alertDescription);
                 */

                alertTypeList.add(alertTypeId + "");

                cursor.moveToNext();
            }
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor: getEditAlertInfo()", PMWF_Log.getStringFromStackTrace(ex));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return alertTypeList;
    }

    public UsersInfo getContactInfo(String emailAddress, ArrayList<String> alertTypeList) { // get
                                                                                            // the
                                                                                            // data
                                                                                            // to
                                                                                            // view
                                                                                            // into
                                                                                            // the
                                                                                            // fields
        Cursor cursor = null;
        UsersInfo tempUsersInfo = new UsersInfo();
        try {
            cursor = database.query(TABLE_USERS, null, EMAILADDRESS + " = ?  ", new String[] { emailAddress }, null,
                    null, null, null);

            int column1 = cursor.getColumnIndex(FIRSTNAME);
            int column2 = cursor.getColumnIndex(LASTNAME);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {

                String firstName = cursor.getString(column1);
                String lastName = cursor.getString(column2);

                if (firstName == null) {
                    tempUsersInfo.setFirstName("");
                } else {
                    tempUsersInfo.setFirstName(firstName);
                }

                if (lastName == null) {
                    tempUsersInfo.setLastName("");
                } else {
                    tempUsersInfo.setLastName(lastName);
                }

                tempUsersInfo.setEmailAddress(emailAddress);
                tempUsersInfo.setAlertType(alertTypeList);

                cursor.moveToNext();
            }

            return tempUsersInfo;
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor: getContactInfo()", PMWF_Log.getStringFromStackTrace(ex));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    public ArrayList<ContactInfo> getContactsList(int fromUserId) { // get
                                                                    // contact
                                                                    // list of
                                                                    // main user

        ArrayList<ContactInfo> m_data = new ArrayList<ContactInfo>();
        try {
            ArrayList<ContactInfo> receiverList = getAlertReceiverContactList(fromUserId);
            if (receiverList != null && receiverList.size() > 0) {
                m_data.addAll(receiverList);
            }

            ArrayList<ContactInfo> senderList = getAlertSenderContactList(fromUserId);
            if (senderList != null && senderList.size() > 0) {
                m_data.addAll(senderList);
            }
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor: getContactsList()", PMWF_Log.getStringFromStackTrace(ex));
        }

        return m_data;
    }

    private ArrayList<ContactInfo> getAlertReceiverContactList(int fromUserId) {
        Cursor cursor = null;
        ArrayList<ContactInfo> m_data = new ArrayList<ContactInfo>();
        try {
            cursor = database.query(true, TABLE_USER_CONNECTIONS, null, FROM_USER_ID + " = ?  ",
                    new String[] { fromUserId + "" }, TO_USER_ID, null, null, null);

            int rowcount = cursor.getCount();

            if (rowcount > 0) {
                int column2 = cursor.getColumnIndex(TO_USER_ID);
                int column3 = cursor.getColumnIndex(ACCEPTED_YES_NO);

                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    try {
                        ContactInfo contactDetails = new ContactInfo();
                        String toUserId = cursor.getString(column2);

                        ArrayList<String> usersInfo = getUserName(toUserId);
                        String GUID = getGuid(toUserId);

                        int isActive = cursor.getInt(column3);

                        contactDetails.setUserStatus(getContactStatusId(GUID));
                        contactDetails.setContactLastName(usersInfo.get(0));
                        contactDetails.setContactFirstName(usersInfo.get(1));
                        contactDetails.setContactEmailAddressName(usersInfo.get(2));
                        contactDetails.setContactType("Receiver");// for
                                                                  // determining
                                                                  // sender and
                                                                  // receiver

                        if (isActive == 0) {
                            contactDetails.setUserIsActive(ApplicationSettings.translationSettings.GetTranslation(
                                    "and_lbl_pending", "Pending"));
                        } else {
                            contactDetails.setUserIsActive(ApplicationSettings.translationSettings.GetTranslation(
                                    "and_lbl_connected", "Connected"));
                        }

                        m_data.add(contactDetails.clone());
                    } catch (Exception ex) {
                        PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor: getAlertReceiverContactList(0)",
                                PMWF_Log.getStringFromStackTrace(ex));
                    }
                    cursor.moveToNext();
                }
            }

        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor: getAlertReceiverContactList(1)",
                    PMWF_Log.getStringFromStackTrace(ex));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return m_data;
    }

    private ArrayList<ContactInfo> getAlertSenderContactList(int fromUserId) {
        Cursor cursorReceiver = null;
        ArrayList<ContactInfo> m_data = new ArrayList<ContactInfo>();
        try {
            cursorReceiver = database.query(true, TABLE_USER_CONNECTIONS, null, TO_USER_ID + " = ?  ",
                    new String[] { fromUserId + "" }, FROM_USER_ID, null, null, null);

            int rowcount1 = cursorReceiver.getCount();

            if (rowcount1 > 0) {
                int colFromUserId = cursorReceiver.getColumnIndex(FROM_USER_ID);
                int colIsAccepted = cursorReceiver.getColumnIndex(ACCEPTED_YES_NO);

                cursorReceiver.moveToFirst();
                while (!cursorReceiver.isAfterLast()) {
                    try {
                        ContactInfo contactDetails = new ContactInfo();
                        String senderUserId = cursorReceiver.getString(colFromUserId);
                        String GUID = getGuid(senderUserId + "");

                        ArrayList<String> usersInfo = getUserName(senderUserId);

                        int isActive = cursorReceiver.getInt(colIsAccepted);

                        contactDetails.setUserStatus(getContactStatusId(GUID));
                        contactDetails.setContactLastName(usersInfo.get(0));
                        contactDetails.setContactFirstName(usersInfo.get(1));
                        contactDetails.setContactEmailAddressName(usersInfo.get(2));
                        contactDetails.setContactType("Sender");

                        if (isActive == 0) {
                            contactDetails.setUserIsActive(ApplicationSettings.translationSettings.GetTranslation(
                                    "and_lbl_pending", "Pending"));
                        } else {
                            contactDetails.setUserIsActive(ApplicationSettings.translationSettings.GetTranslation(
                                    "and_lbl_connected", "Connected"));
                        }

                        m_data.add(contactDetails.clone());
                    } catch (Exception ex) {
                        PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor: getAlertSenderContactList(0)",
                                PMWF_Log.getStringFromStackTrace(ex));
                    }
                    cursorReceiver.moveToNext();
                }
            }
        } catch (Exception ex1) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor: getAlertSenderContactList(1)",
                    PMWF_Log.getStringFromStackTrace(ex1));
        } finally {
            if (cursorReceiver != null) {
                cursorReceiver.close();
            }
        }
        return m_data;
    }

    private int getContactStatusId(String uniqueKey) {
        Cursor statusCursor = null;
        int statusId = 0;
        try {
            if (uniqueKey != null && uniqueKey.length() > 0) {
                statusCursor = database.query(true, TABLE_USER_STATUS, null, "GUID = ?", new String[] { uniqueKey },
                        null, null, null, null);
                int statusCount = statusCursor.getCount();
                if (statusCount > 0) {
                    int Column1 = statusCursor.getColumnIndex(USERSTATUS_ID);
                    statusCursor.moveToFirst();
                    while (!statusCursor.isAfterLast()) {
                        statusId = statusCursor.getInt(Column1);
                        statusCursor.moveToNext();
                    }
                }

            }
        } catch (Exception e) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor: getAlertSenderContactList(0)",
                    PMWF_Log.getStringFromStackTrace(e));
        } finally {
            if (statusCursor != null) {
                statusCursor.close();
            }
        }
        return statusId;
    }

    public int getConnectedContactsList(int fromuserid) { // get contact list of
                                                          // main user

        // ArrayList<ContactInfo> connectedContact = new
        // ArrayList<ContactInfo>();
        // ArrayList<String> usersInfo = new ArrayList<String>();
        Cursor cursor = null;
        int rowcount;
        // int fromUserId = getUserId(ApplicationSettingsFilePath.AccountName);

        // cursor = database.query(TABLE_USERS, null, null, null, null, null,
        // null, null);

        try {
            String query = "select distinct(ToUserId) from userconnections where IsAccepted=1 AND  FromUserId="
                    + fromuserid + " ";
            cursor = database.rawQuery(query, null);
            // cursor = database.query(true, TABLE_USER_CONNECTIONS, null,
            // FROM_USER_ID + " = ?  ", new String[] { fromuserid + "" },
            // TO_USER_ID, null, null, null);

            rowcount = cursor.getCount();

            if (rowcount > 0) {

                return rowcount;
            }

        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor: getConnectedContactsList()",
                    PMWF_Log.getStringFromStackTrace(ex));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return 0;
    }

    public void editUserConnections(JSONArray alertType, String guid) { // edit
                                                                        // alert
                                                                        // type
                                                                        // info
                                                                        // of
                                                                        // contact
        Cursor cursor = null;
        try {

            int editUserId = geteUserId(guid);

            int fromUserId = getUserId(ApplicationSettingsFilePath.AccountName);

            int contactStatus = getcontactStatus(fromUserId, editUserId);

            List<Integer> ids = new ArrayList<Integer>();

            if (fromUserId > 0 && editUserId > 0) {

                for (int k = 0; k < alertType.length(); k++) {

                    // int alertTypeId =
                    // getAlertTypeId(alertType.get(k).toString());

                    ids.add(Integer.parseInt(alertType.get(k).toString()));
                }

                StringBuilder b = new StringBuilder("delete from" + " " + TABLE_USER_CONNECTIONS + " where "
                        + FROM_USER_ID + " = '" + fromUserId + "'" + " and " + TO_USER_ID + " = '" + editUserId + "'"
                        + " and " + ALERTYPE_ID + " NOT IN(");
                String[] whereArgs = new String[ids.size()];
                int index = 0;
                for (int id : ids) {
                    whereArgs[index] = String.valueOf(id);
                    b.append("?");
                    if (index < ids.size() - 1) {
                        b.append(",");
                    }
                    index++;
                }
                b.append(")");
                database.execSQL(b.toString(), whereArgs);
            }

            for (int k = 0; k < ids.size(); k++) {
                cursor = database.query(TABLE_USER_CONNECTIONS, null, FROM_USER_ID + " = ?  and " + TO_USER_ID
                        + " = ? and " + ALERTYPE_ID + " = ? ",
                        new String[] { fromUserId + "", editUserId + "", ids.get(k) + "" }, null, null, null, null);
                int rowcount = cursor.getCount();

                ContentValues userConnValue = new ContentValues();
                userConnValue.put(FROM_USER_ID, fromUserId);
                userConnValue.put(TO_USER_ID, editUserId);
                userConnValue.put(ALERTYPE_ID, ids.get(k) + "");
                userConnValue.put(ACCEPTED_YES_NO, contactStatus);

                if (rowcount == 0) {
                    database.insert(TABLE_USER_CONNECTIONS, null, userConnValue);
                } else {
                    database.update(TABLE_USER_CONNECTIONS, userConnValue, FROM_USER_ID + " = ?  and " + TO_USER_ID
                            + " = ? and " + ALERTYPE_ID + " = ? ", new String[] { fromUserId + "",
                            editUserId + "" + ids.get(k) + "" });
                }

            }

        } catch (Exception ex) {

            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor: editUserConnections()", PMWF_Log.getStringFromStackTrace(ex));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public int getUserConnectionsCountRcv(String emailAddress) { // on deleting
                                                                 // contact,
                                                                 // contact
                                                                 // may also
                                                                 // have
                                                                 // added
                                                                 // main user
                                                                 // in his
                                                                 // contact
                                                                 // list so
                                                                 // at time
                                                                 // of
                                                                 // recieved
                                                                 // alert to
                                                                 // main user
                                                                 // need
                                                                 // contact
                                                                 // info,we
                                                                 // couldnot
                                                                 // delete
                                                                 // contact
                                                                 // if count
                                                                 // is
                                                                 // greater
                                                                 // than 0

        Cursor cursor = null;
        int count = 0;
        try {
            int fromUserId = getUserId(emailAddress); // contact
            int toUserId = getUserId(ApplicationSettingsFilePath.AccountName);
            cursor = database.query(TABLE_USER_CONNECTIONS, null, FROM_USER_ID + " = ?  and " + TO_USER_ID + " = ? ",
                    new String[] { fromUserId + "", toUserId + "" }, null, null, null, null);

            count = cursor.getCount();

        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor: getUserConnectionsCountRcv()",
                    PMWF_Log.getStringFromStackTrace(ex));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return count;
    }

    public void deleteContact(String emailAddress) { //
        Cursor cursor = null;
        try {
            cursor = database.query(TABLE_USERS, null, EMAILADDRESS + " = ?  ", new String[] { emailAddress }, null,
                    null, null, null);

            int rowcount = cursor.getCount();
            if (rowcount > 0) {

                database.delete(TABLE_USERS, EMAILADDRESS + " = ?  ", new String[] { emailAddress });
            }

        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor: deleteContact()", PMWF_Log.getStringFromStackTrace(ex));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

    }

    public void deleteUserConnection(String emailAddress) { // delete user
                                                            // connections
                                                            // incase of delete
                                                            // contact
        Cursor cursor = null;
        try {

            int fromUserId = getUserId(ApplicationSettingsFilePath.AccountName);
            int toUserId = getUserId(emailAddress);

            if (fromUserId > 0 && toUserId > 0) {
                cursor = database.query(TABLE_USER_CONNECTIONS, null, FROM_USER_ID + " = ?  and " + TO_USER_ID
                        + " = ? ", new String[] { fromUserId + "", toUserId + "" }, null, null, null, null);

                int rowcount = cursor.getCount();

                if (rowcount > 0) {

                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {

                        database.delete(TABLE_USER_CONNECTIONS, FROM_USER_ID + " = ?  and " + TO_USER_ID + " = ? ",
                                new String[] { fromUserId + "", toUserId + "" });

                        cursor.moveToNext();
                    }

                }
            }
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor: deleteUserConnection()", PMWF_Log.getStringFromStackTrace(ex));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

    }

    public void deleteAlerts(String emailAddress) { // delete alerts incase of
                                                    // deleting contacts
        Cursor cursor = null;
        try {

            int fromUserId = getUserId(ApplicationSettingsFilePath.AccountName);
            int toUserId = getUserId(emailAddress);

            if (fromUserId > 0 && toUserId > 0) {
                cursor = database.query(TABLE_ALERTS, null, ALERT_FROM_USERID + " = ?  and " + ALERT_TO_USERID
                        + " = ? ", new String[] { fromUserId + "", toUserId + "" }, null, null, null, null);

                int rowcount = cursor.getCount();

                if (rowcount > 0) {

                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {

                        database.delete(TABLE_ALERTS, ALERT_FROM_USERID + " = ?  and " + ALERT_TO_USERID + " = ? ",
                                new String[] { fromUserId + "", toUserId + "" });

                        cursor.moveToNext();
                    }

                }
            }
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor: deleteAlerts()", PMWF_Log.getStringFromStackTrace(ex));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

    }

    // Delete sender from db
    public void deletedByOthersUserConnection(String emailAddress) {
        Cursor cursor = null;
        try {

            int fromUserId = getUserId(ApplicationSettingsFilePath.AccountName);
            int toUserId = getUserId(emailAddress);

            if (fromUserId > 0 && toUserId > 0) {
                cursor = database.query(TABLE_USER_CONNECTIONS, null, FROM_USER_ID + " = ?  and " + TO_USER_ID
                        + " = ? ", new String[] { toUserId + "", fromUserId + "" }, null, null, null, null);

                int rowcount = cursor.getCount();

                if (rowcount > 0) {

                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {

                        database.delete(TABLE_USER_CONNECTIONS, FROM_USER_ID + " = ?  and " + TO_USER_ID + " = ? ",
                                new String[] { toUserId + "", fromUserId + "" });

                        cursor.moveToNext();
                    }

                }
            }
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor: deletedByOthersUserConnection()",
                    PMWF_Log.getStringFromStackTrace(ex));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

    }

    // delete alerts from sender
    public void deletedByOthersAlerts(String emailAddress) {
        // deleting contacts
        Cursor cursor = null;
        try {

            int fromUserId = getUserId(ApplicationSettingsFilePath.AccountName);
            int toUserId = getUserId(emailAddress);

            if (fromUserId > 0 && toUserId > 0) {
                cursor = database.query(TABLE_ALERTS, null, ALERT_FROM_USERID + " = ?  and " + ALERT_TO_USERID
                        + " = ? ", new String[] { toUserId + "", fromUserId + "" }, null, null, null, null);

                int rowcount = cursor.getCount();

                if (rowcount > 0) {

                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {

                        database.delete(TABLE_ALERTS, ALERT_FROM_USERID + " = ?  and " + ALERT_TO_USERID + " = ? ",
                                new String[] { toUserId + "", fromUserId + "" });

                        cursor.moveToNext();
                    }

                }
            }
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor: deletedByOthersAlerts()", PMWF_Log.getStringFromStackTrace(ex));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

    }

    // check if deleted sender is in receiver list
    public int getDeletedByOthersUserConnectionsCount(String emailAddress) {
        // Checking whether main user id add that deleted contact or not
        Cursor cursor = null;
        int count = 0;
        int toUserId = getUserId(emailAddress); // contact
        int fromUserId = getUserId(ApplicationSettingsFilePath.AccountName);
        try {
            cursor = database.query(TABLE_USER_CONNECTIONS, null, FROM_USER_ID + " = ?  and " + TO_USER_ID + " = ? ",
                    new String[] { fromUserId + "", toUserId + "" }, null, null, null, null);

            count = cursor.getCount();

        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor: getDeletedByOthersUserConnectionsCount()",
                    PMWF_Log.getStringFromStackTrace(ex));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return count;
    }

    public void insertTranslations(String jsonTranslation) { // insert
                                                             // translation
                                                             // key, value
                                                             // and locale

        Cursor cursor = null;
        try {

            JSONArray languageKey = new JSONArray(jsonTranslation);

            for (int i = 0; i < languageKey.length(); i++) {
                JSONObject uniObject = languageKey.getJSONObject(i);
                String key = uniObject.getString("Key");
                String value = uniObject.getString("Value");

                JSONArray arrValue = new JSONArray(value);
                for (int j = 0; j < arrValue.length(); j++) {
                    JSONObject valueObj = arrValue.getJSONObject(j);

                    String trnsCode = valueObj.getString("TranslationCode");
                    String trnsText = valueObj.getString("Translations");

                    ContentValues translationValue = new ContentValues();
                    translationValue.put(TRANSLATION_CODE, trnsCode);
                    translationValue.put(TRANSLATION_TEXT, trnsText);
                    translationValue.put(LOCALE, key);

                    cursor = database.query(TABLE_TRANSLATIONS, null, TRANSLATION_TEXT + " = ? ",
                            new String[] { trnsText }, null, null, null, null);

                    int rowCount = cursor.getCount();
                    if (rowCount > 0) {
                        database.update(TABLE_TRANSLATIONS, translationValue, TRANSLATION_TEXT + " = ? ",
                                new String[] { trnsText });
                    } else {
                        database.insert(TABLE_TRANSLATIONS, null, translationValue);
                    }

                }
            }
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor: insertTranslations()", PMWF_Log.getStringFromStackTrace(ex));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

    }

    public boolean isLanguageSet() { // check if language is set or not

        Cursor cursor = null;

        try {
            cursor = database.query(TABLE_LANGUAGE_SETTING, null, null, null, null, null, null, null);
            int rowcount = cursor.getCount();

            if (rowcount > 0) {

                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {

                    int column1 = cursor.getColumnIndex(LANGUAGE);
                    int language = cursor.getInt(column1);
                    ApplicationSettingsFilePath.language = language;
                    cursor.moveToNext();
                }

                return true;
            }

        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor: isLanguageSet()", PMWF_Log.getStringFromStackTrace(ex));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return false;
    }

    public void setLanguageToSetting(int language) { // on select of language
                                                     // set language value to
                                                     // database
        Cursor cursor = null;

        try {
            cursor = database.query(TABLE_LANGUAGE_SETTING, null, null, null, null, null, null, null);
            int rowcount = cursor.getCount();

            ContentValues value = new ContentValues();
            value.put(LANGUAGE, language);
            if (rowcount > 0) {
                database.update(TABLE_LANGUAGE_SETTING, value, null, null);
            } else {
                database.insert(TABLE_LANGUAGE_SETTING, null, value);
            }

            ApplicationSettingsFilePath.language = language;

        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor: setLanguageToSetting()", PMWF_Log.getStringFromStackTrace(ex));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public void getTranslation() { // on change of language retrieve all
                                   // translation key and value and put into
                                   // hashmap
        Cursor cursor = null;

        try {
            cursor = database.query(TABLE_TRANSLATIONS, null, LOCALE + " = ?",
                    new String[] { ApplicationSettingsFilePath.language + "" }, null, null, null);
            int rowcount = cursor.getCount();

            if (rowcount > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {

                    int column1 = cursor.getColumnIndex(TRANSLATION_CODE);
                    int column2 = cursor.getColumnIndex(TRANSLATION_TEXT);

                    String key = cursor.getString(column1);
                    String value = cursor.getString(column2);

                    ApplicationSettings.translationSettings.translations.put(key, value);

                    cursor.moveToNext();
                }
            }

        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor: getTranslation()", PMWF_Log.getStringFromStackTrace(ex));

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

    }

    public void saveLoginInfo(String emailAddress, String password) {
        Cursor cursor = null;

        try {

            cursor = database.query(TABLE_LOGIN_INFO, null, null, null, null, null, null, null);

            int rowCount = cursor.getCount();

            ContentValues loginValue = new ContentValues();
            loginValue.clear();
            loginValue.put(LOGIN_EMAILADDRESS, emailAddress);
            loginValue.put(LOGIN_PASSWORD, password);

            if (rowCount == 0) {
                database.insert(TABLE_LOGIN_INFO, null, loginValue);
            } else {
                database.update(TABLE_LOGIN_INFO, loginValue, null, null);
            }

        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor: saveLoginInfo()", PMWF_Log.getStringFromStackTrace(ex));

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

    }

    public AccountInfo getAccountName() { // get previous login
                                          // account name

        Cursor cursor = null;
        String accountName = null;
        String accountPass = null;
        AccountInfo accountInfo = null;

        try {
            cursor = database.query(TABLE_LOGIN_INFO, null, null, null, null, null, null);

            int rowCount = cursor.getCount();

            if (rowCount > 0) {

                cursor.moveToFirst();
                int column1 = cursor.getColumnIndex(LOGIN_EMAILADDRESS);
                int column2 = cursor.getColumnIndex(LOGIN_PASSWORD);
                accountName = cursor.getString(column1);
                accountPass = cursor.getString(column2);
                accountInfo = new AccountInfo();
                accountInfo.setEmailaddress(accountName);
                accountInfo.setPassword(accountPass);
            }

        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor: getAccountName()", PMWF_Log.getStringFromStackTrace(ex));

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return accountInfo;
    }

    public int getcontactStatus(int fromUserId, int toUserId) { // get contact
                                                                // status if
                                                                // contact had
                                                                // accepted
                                                                // alert type
                                                                // from
                                                                // userconnections
                                                                // table

        Cursor cursor = null;
        int isAccepted = 0;

        try {
            cursor = database.query(TABLE_USER_CONNECTIONS, null, FROM_USER_ID + " = ? and " + TO_USER_ID + " = ? ",
                    new String[] { fromUserId + "", toUserId + "" }, null, null, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int column1 = cursor.getColumnIndex(ACCEPTED_YES_NO);
                isAccepted = cursor.getInt(column1);
                cursor.moveToNext();
                break;
            }

        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor: getcontactStatus()", PMWF_Log.getStringFromStackTrace(ex));

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return isAccepted;
    }

    public UsersInfo getMainUserInfo(String emailAddress) { // get main user
                                                            // info to view in
                                                            // edit personal
                                                            // information page
        Cursor cursor = null;

        // ArrayList<UsersInfo> arrContactInfo = new ArrayList<UsersInfo>();
        UsersInfo personalDetails = new UsersInfo();
        try {
            cursor = database.query(TABLE_USERS, null, EMAILADDRESS + " = ? ", new String[] { emailAddress }, null,
                    null, null);

            int rowCount = cursor.getCount();

            if (rowCount > 0) {

                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    int column1 = cursor.getColumnIndex(FIRSTNAME);
                    int column2 = cursor.getColumnIndex(LASTNAME);
                    int column4 = cursor.getColumnIndex(EMAILADDRESS);
                    int column3 = cursor.getColumnIndex(PHONENUMBER);

                    String firstName = cursor.getString(column1);
                    String lastName = cursor.getString(column2);
                    String phoneNumber = cursor.getString(column3);
                    String userEmailAddress = cursor.getString(column4);

                    if (firstName.length() == 0) {
                        personalDetails.setFirstName("");
                    } else {
                        personalDetails.setFirstName(firstName);
                    }

                    personalDetails.setLastName(lastName);
                    personalDetails.setEmailAddress(userEmailAddress);

                    if (phoneNumber.length() == 0 || phoneNumber.equals("null")) {
                        personalDetails.setPhoneNumber("");

                    } else {
                        personalDetails.setPhoneNumber(phoneNumber);
                    }

                    cursor.moveToNext();
                }

                return personalDetails;
            }

        } catch (Exception ex) {

            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor: getMainUserInfo()", PMWF_Log.getStringFromStackTrace(ex));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return null;

    }

    public void updateUserInfo(String userInfo) { // update main user info on
                                                  // edit personal information
        Cursor cursor = null;

        try {

            String lastName = null;
            String firstName = null;
            String phoneNumber = null;

            JSONObject usersInfo = new JSONObject(userInfo);

            lastName = usersInfo.getString("LastName");
            firstName = usersInfo.getString("FirstName");
            phoneNumber = usersInfo.getString("PhoneNumber");

            cursor = database.query(TABLE_USERS, null, EMAILADDRESS + " = ? ",
                    new String[] { ApplicationSettingsFilePath.AccountName }, null, null, null);
            ContentValues editInfoValues = new ContentValues();
            editInfoValues.put(FIRSTNAME, firstName);
            editInfoValues.put(LASTNAME, lastName);
            editInfoValues.put(PHONENUMBER, phoneNumber);

            int rowCount = cursor.getCount();
            if (rowCount > 0) {
                database.update(TABLE_USERS, editInfoValues, EMAILADDRESS + " = ? ",
                        new String[] { ApplicationSettingsFilePath.AccountName });
            }

        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor: updateUserInfo()", PMWF_Log.getStringFromStackTrace(ex));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public void saveShakeThresholdValue(float thresholdValue) {

        Cursor cursor = null;

        try {
            cursor = database.query(TABLE_SHAKE_THRESHOLD_INFO, null, null, null, null, null, null);
            int rowcount = cursor.getCount();

            ContentValues shakeValues = new ContentValues();
            shakeValues.clear();
            shakeValues.put(SHAKE_THRESHOLDVALUE, thresholdValue);
            if (rowcount == 0) {
                database.insert(TABLE_SHAKE_THRESHOLD_INFO, null, shakeValues);
            } else {
                database.update(TABLE_SHAKE_THRESHOLD_INFO, shakeValues, null, null);
            }

        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor: saveShakeThresholdValue()",
                    PMWF_Log.getStringFromStackTrace(ex));

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

    }

    public float getShakeThresholdValue() {

        Cursor cursor = null;
        float thresholdValue = 10.0f;
        try {

            cursor = database.query(TABLE_SHAKE_THRESHOLD_INFO, null, null, null, null, null, null);

            int rowcount = cursor.getCount();

            if (rowcount > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    int column1 = cursor.getColumnIndex(SHAKE_THRESHOLDVALUE);

                    thresholdValue = cursor.getFloat(column1);
                    cursor.moveToNext();
                }
            }

        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor: getShakeThresholdValue()", PMWF_Log.getStringFromStackTrace(ex));

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return thresholdValue;
    }

    public void insertStatus(String statusList) {
        Cursor cursor = null;
        try {
            JSONObject userJson = new JSONObject(statusList);
            String pullResult = userJson.getString("PullSettingResult");
            JSONObject pullResultObj = new JSONObject(pullResult);
            String result = pullResultObj.getString("Result");
            JSONArray resultObj = new JSONArray(result);

            int size = resultObj.length();
            for (int i = 0; i < size; i++) {
                JSONObject uniObject = resultObj.getJSONObject(i);
                String StatusId = uniObject.getString("UserStatusTypeId");
                String description = uniObject.getString("Description");
                String GUID = uniObject.getString("Guid");
                cursor = database.query(TABLE_STATUS, allUserStatusColumns, STATUS_DESCRIPTION + " = '" + description
                        + "'", null, null, null, null);

                int rowCount = cursor.getCount();
                ContentValues alertStatusValues = new ContentValues();
                alertStatusValues.clear();
                alertStatusValues.put(USERSTATUS_ID, StatusId);
                alertStatusValues.put(STATUS_DESCRIPTION, description);
                alertStatusValues.put(ALERT_GUID, GUID);
                if (rowCount == 0) {
                    database.insert(TABLE_STATUS, null, alertStatusValues);
                } else {
                    database.update(TABLE_STATUS, alertStatusValues, STATUS_DESCRIPTION + " = ? ",
                            new String[] { description });
                }

            }

        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor::insertStatus()", ex.getMessage());
        }

        finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public void insertUserStatus(String statusList) {
        try {
            JSONObject userJson = new JSONObject(statusList);
            String pullResult = userJson.getString("PullResult");
            JSONObject pullResultObj = new JSONObject(pullResult);
            String result = pullResultObj.getString("Result");
            if (result != null && !result.equalsIgnoreCase("null")) {

                JSONArray resultObj = new JSONArray(result);
                for (int i = 0; i < resultObj.length(); i++) {
                    Cursor cursor = null;
                    try {
                        JSONObject uniObject = resultObj.getJSONObject(i);
                        String StatusId = uniObject.getString("UserStatusTypeId");
                        String GUID = uniObject.getString("UserGuid");
                        cursor = database.query(TABLE_USER_STATUS, null, USER_GUID + " = '" + GUID + "'", null, null,
                                null, null);

                        int rowCount = cursor.getCount();
                        ContentValues alertStatusValues = new ContentValues();
                        alertStatusValues.clear();
                        alertStatusValues.put(USERSTATUS_ID, StatusId);
                        alertStatusValues.put(USER_GUID, GUID);
                        if (rowCount == 0) {
                            database.insert(TABLE_USER_STATUS, null, alertStatusValues);
                        } else {
                            database.update(TABLE_USER_STATUS, alertStatusValues, USER_GUID + " = ? ",
                                    new String[] { GUID });
                        }
                    } catch (Exception ex) {
                        PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor::insertUserStatus()",
                                PMWF_Log.getStringFromStackTrace(ex));
                    } finally {
                        if (cursor != null) {
                            cursor.close();
                        }
                    }

                }

            }
        } catch (Exception e) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor::insertUserStatus(1)", PMWF_Log.getStringFromStackTrace(e) + ":"
                    + statusList);
        }
    }

    public String getUserStatusDescription(int StatusId) {
        Cursor cursor = null;
        String alertStatus = null;
        try {
            cursor = database.query(TABLE_STATUS, null, USERSTATUS_ID + " = '" + StatusId + "" + "'", null, null, null,
                    null);
            int Column1 = cursor.getColumnIndex(STATUS_DESCRIPTION);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                alertStatus = cursor.getString(Column1);
                cursor.moveToNext();
            }
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor::getStatusDescription()", PMWF_Log.getStringFromStackTrace(ex));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return alertStatus;

    }

    public void deleteContactStatus(String emailAddress) { //
        String GUID = getGuidFromEmail(emailAddress);
        Cursor cursor = null;
        try {
            cursor = database.query(TABLE_USER_STATUS, null, USER_GUID + " = ?  ", new String[] { GUID }, null, null,
                    null, null);

            int rowcount = cursor.getCount();
            if (rowcount > 0) {
                database.delete(TABLE_USER_STATUS, USER_GUID + " = ?  ", new String[] { GUID });
            }

        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor: deleteContact()", PMWF_Log.getStringFromStackTrace(ex));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

    }

    public void insertStatusForContact(int statusId, String GUID) {
        Cursor cursor = null;

        try {
            cursor = database.query(TABLE_USER_STATUS, null, USER_GUID + "=?", new String[] { GUID }, null, null, null,
                    null);
            int rowCount = cursor.getCount();
            ContentValues alertValues = new ContentValues();
            alertValues.clear();
            alertValues.put(USER_GUID, GUID);
            alertValues.put(USERSTATUS_ID, statusId);
            if (rowCount == 0) {
                Log.d("row", "rowcount >> " + rowCount);
                database.insert(TABLE_USER_STATUS, null, alertValues);
            } else {
                database.update(TABLE_USER_STATUS, alertValues, USER_GUID + " = ? ", new String[] { GUID });
            }

        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "UserCursor: insertStatusForContact()", PMWF_Log.getStringFromStackTrace(ex));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

}
