package com.android.socialalert.logger;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;

import com.android.socialalert.settings.ApplicationSettings;

import android.util.Log;

public class PMWF_Log {
    private static String logDirectory = null;

    public static final int INFO = 0;
    public static final int DEBUG = 1;
    public static final int ERROR = 2;

    /** Sets path to store log files */
    public static void InitializeLogSettings(String logDir) {
        logDirectory = logDir;
    }

    public static void fnlog(int msg_type, String function, String msg) {

        String message_type;
        function = (function != null) ? function : "UNDEFINED";
        msg = (msg != null) ? msg : "PMWF: Message is NULL";

        if (msg_type == PMWF_Log.INFO) {
            message_type = "INFO";
            Log.i(function, msg);
        } else if (msg_type == PMWF_Log.ERROR) {
            message_type = "ERROR";
            Log.e(function, msg);
        } else {
            message_type = "DEBUG";
            Log.d(function, msg);
        }

        if (logDirectory == null) {
            Log.w("PMWF", "Log directory not set !! loggin to default directory");

        }

        File logDir = new File(ApplicationSettings.SDCARD_APP_PATH + "/Logs");

        if (!logDir.exists()) {
            logDir.mkdirs();
            logDir = null;
        }

        String logdata = message_type + "\t" + function + "\t" + msg;
        LogWriter.writeLogFile(null, ApplicationSettings.SDCARD_APP_PATH + "/Logs", "Log", logdata);
        // LogWriter.writeLogFile(generalEncryptKey, (logDirectory != null) ?
        // logDirectory : "/mnt/sdcard/Procit/logs", "Log", logdata);
    }

    public static String getStringFromStackTrace(Exception ex) {
        try {
            if (ex == null) {
                return "getStringFromStackTrace()..ex is null.";
            }
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            return "EXCEPTION!!!" + sw.toString();
        } catch (Exception e) {

        } catch (Error err) {

        }
        return "Cannot print exception stacktrace..";
    }

    public static String getStringFromError(Error er) {
        try {
            if (er == null) {
                return "getStringFromError()..er is null.";
            }
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            er.printStackTrace(pw);
            return "ERROR!!!" + sw.toString();
        } catch (Exception ex) {

        } catch (Error err) {

        }
        return "Cannot print error stacktrace..";
    }

    public static String getStackTrace(Throwable t) {

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        return sw.toString(); // stack trace as a string

    }

}
