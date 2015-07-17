package com.android.socialalert.settings;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;
import android.os.Environment;

import com.android.socialalert.database.UserCursor;

public class ApplicationSettings {
    public static String SDCARD_APP_PATH;
    public static String APP_DATABASE_PATH;
    public static final String DB_NAME = "samen_safe.sqlite";

    public static boolean isWeberviceReachable = false;
    public static TranslationSettings translationSettings;

    static {
        SDCARD_APP_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/Android/Data/com.android.socialalert" + "/data";
        APP_DATABASE_PATH = SDCARD_APP_PATH + "/database/" + DB_NAME;

        translationSettings = new TranslationSettings();
    }
    public static UserCursor dbAccessor;

    public static int initDBAccessor(Context context, String dbPath) {
        dbAccessor = new UserCursor(context, dbPath);
        return 0;
    }

    public static void closeDBAccessor() {
        dbAccessor.close();
    }

    public static boolean createFolders(String folderName) throws Exception {

        File appStore = new File(folderName);

        if (!appStore.exists()) {
            return CreateDirs(appStore);
        }
        return false;
    }

    /**
     * Creates directory and sub-directories
     * 
     * @param directory
     *            - File to create directory
     * @throws Exception
     */
    public static boolean CreateDirs(File directory) {

        if (!directory.exists()) {
            return directory.mkdirs();
        }
        return true;
    }

    public static void CreateApplicationFolder() {
        try {
            Create_Folders(ApplicationSettings.SDCARD_APP_PATH, "database");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Creates folder in given path with given name
     * 
     * @param baseDirPath
     *            - Base directory path for creating folder
     * @param folderName
     *            - Name of the folder to create
     * @throws Exception
     */
    public static void Create_Folders(String baseDirPath, String folderName) throws Exception {
        File o_base_dir = new File(baseDirPath);
        if (!o_base_dir.exists()) {
            CreateDirs(o_base_dir);
        }
        File o_dir = new File(o_base_dir, folderName);
        CreateDirs(o_dir);
    }

    public static ArrayList<Integer> getContactCount(String fromUser, String AlertType, String[] whereValues) {
        int fromUserId = dbAccessor.getUserId(fromUser);
        int alertId = dbAccessor.getAlertTypeId(AlertType);
        whereValues[0] = fromUserId + "";
        whereValues[1] = alertId + "";
        whereValues[2] = "1";

        if (fromUserId != 0) {
            String conditions = "FromUserId = ? AND AlertTypeId = ? AND IsAccepted = ? ";
            // UserCursor.FROM_USER_ID + " = ? " + " AND " +
            // UserCursor.ALERTTYPE_ID + " = ?";
            return dbAccessor.getContactCount(conditions, whereValues);
        }
        return new ArrayList<Integer>();
    }

    public static boolean isLanguageSet() {
        return dbAccessor.isLanguageSet();
    }

    public static void setLanguageToSetting(int language) {
        dbAccessor.setLanguageToSetting(language);

    }

    public static String getAlertTypeId(String alertTypeDesc) {

        int alertTypeId = dbAccessor.getAlertTypeId(alertTypeDesc);

        return alertTypeId + "";

    }

    public static void saveLoginInfo(String emailAddress, String password) {
        dbAccessor.saveLoginInfo(emailAddress, password);
    }

}
