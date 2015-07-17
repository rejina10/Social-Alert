package com.android.socialalert.database;

import java.io.File;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.socialalert.logger.PMWF_Log;
import com.android.socialalert.settings.ApplicationSettings;

public class SqlDatabase extends SQLiteOpenHelper {

    // private final Context myContext;
    private String dbPath;

    /**
     * Constructor Takes and keeps a reference of the passed context in order to
     * access to the application assets and resources.
     * 
     * @param context
     */
    public SqlDatabase(Context context, String dbPath) {

        super(context, ApplicationSettings.DB_NAME, null, 1);
        // this.myContext = context;
        this.dbPath = dbPath;
    }

    @Override
    public void onCreate(SQLiteDatabase arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

    }

    @Override
    public synchronized void close() {
        super.close();

    }

    public SQLiteDatabase createDataBase() throws Exception {
        SQLiteDatabase myDataBase;
        try {
            File databaseFile = new File(dbPath);
            if (databaseFile.exists()) {
                try {
                    myDataBase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);

                } catch (Exception ex) {
                    PMWF_Log.fnlog(PMWF_Log.ERROR, "SamenSafe()", PMWF_Log.getStringFromStackTrace(ex));
                    databaseFile.delete();
                    myDataBase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);

                }
            } else {
                myDataBase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);

            }

            myDataBase.execSQL(UserCursor.QUERY_CREATE_TABLE_USERS);
            myDataBase.execSQL(UserCursor.QUERY_CREATE_TABLE_USER_CONNECTIONS);
            myDataBase.execSQL(UserCursor.QUERY_CREATE_TABLE_ALERTTYPES);
            myDataBase.execSQL(UserCursor.QUERY_CREATE_TABLE_ALERTS);
            myDataBase.execSQL(UserCursor.QUERY_CREATE_TABLE_ALERTS_STATUS);
            myDataBase.execSQL(UserCursor.QUERY_CREATE_TABLE_TRANSLATIONS);
            myDataBase.execSQL(UserCursor.QUERY_CREATE_TABLE_SETTING);
            myDataBase.execSQL(UserCursor.QUERY_CREATE_TABLE_LOGIN_INFO);
            myDataBase.execSQL(UserCursor.QUERY_CREATE_TABLE_SHAKE_THRESHOLD_INFO);
            myDataBase.execSQL(UserCursor.QUERY_CREATE_TABLE_STATUS);
            myDataBase.execSQL(UserCursor.QUERY_CREATE_TABLE_USER_STATUS);

            return myDataBase;
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "SQLiteDatabase::createDataBase()", ex.getMessage());
        }

        return null;
    }

}
