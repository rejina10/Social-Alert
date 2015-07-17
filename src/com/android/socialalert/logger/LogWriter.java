package com.android.socialalert.logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import com.android.socialalert.utilities.DateTimeProcessor;

public class LogWriter {
    /**
     * Writes log to file encrypted with the encryption key
     * 
     * @param encriptionKey
     *            - Key used for encryption
     * @param path
     *            - Path to store log file
     * @param logtype
     *            - Type of log to write
     * @param log
     *            - Log message to write to file
     */
    public static void writeLogFile(String encriptionKey, String logPath, String logType, String logMessage) {
        BufferedWriter bw = null;
        // Log.i("test", "I am here3");
        try {
            File logfile = new File(logPath, DateTimeProcessor.GetCurrentFormattedDateTime("yyyyMMdd") + "_" + logType
                    + ".txt");
            bw = new BufferedWriter(new FileWriter(logfile, true), 8192);
            String logdata = DateTimeProcessor.GetCurrentFormattedDateTime("yyyyMMdd_HHmmss") + "\t" + logMessage;
            if (encriptionKey != null) {
                // bw.write(Security.encrypt(encriptionKey,
                // logdata).replace("\n", "$$$"));
                bw.write("\n");
            } else {
                bw.write(logdata);
                bw.write("\n");
            }
            bw.flush();
        } catch (Exception e) {

        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (Exception ie) {

            }
        }
    }

}
