package com.android.socialalert.utilities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.android.socialalert.logger.PMWF_Log;

/** Class to manage date time functionality */
public class DateTimeProcessor {

    /** Defines private constructor to mark as utility class */
    private DateTimeProcessor() {
    }

    /**
     * Generates date in format as dd-MM-yyyy form Eg. 23-05-2011
     * 
     * @param date
     *            - String containing date in format yyyyMMdd
     * @return Returns formatted date as string or null on error
     */
    public static String getFormattedDate(String date) {

        try {
            if (date != null && date.length() > 0) {
                return date.substring(6, 8) + "-" + date.substring(4, 6) + "-" + date.substring(0, 4);
            }
        } catch (Exception ex) {
            return null;
        }
        return null;
    }

    /**
     * Generates time in format as HH:mm form Eg. 13:39
     * 
     * @param time
     *            - String containing time in format HHmm
     * @return Returns formatted time as string
     * @throws Exception
     */
    public static String getFormattedTime(String time) {
        try {
            if (time != null && time.length() > 0) {
                return time.substring(0, 2) + ":" + time.substring(2, 4);
            }
        } catch (Exception ex) {
            return null;
        }
        return null;
    }

    /**
     * Compare dates
     * 
     * @param firstDate
     *            - First date string
     * @param secondDate
     *            - Second date string
     * @return Returns +ve if firstDate is latest or -ve if secondDate is latest
     *         or 0 if both same
     * @throws Exception
     */
    public static int compareDates_NoTime(String firstDate, String secondDate) {
        int returnValue = 1;
        try {

            SimpleDateFormat formatter;

            if (firstDate == null || firstDate.length() == 0) {
                return -1;
            }

            if (secondDate == null || secondDate.length() == 0) {
                return 1;
            }

            if (firstDate.split("_").length > 1 && secondDate.split("_").length > 1) {
                formatter = new SimpleDateFormat("yyyyMMdd");
            } else if (firstDate.split(" ").length > 1 && secondDate.split(" ").length > 1) {
                formatter = new SimpleDateFormat("dd-MM-yyyy");
            } else {
                formatter = new SimpleDateFormat("yyyyMMdd");
            }

            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(formatter.parse(firstDate));
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(formatter.parse(secondDate));
            if (cal1.after(cal2)) {
                returnValue = 1; // firstdate > seconddate
            } else if (cal1.before(cal2)) {
                returnValue = -1; // firstdate < seconddate
            } else if (cal1.equals(cal2)) {
                returnValue = 0;
            }
        } catch (Exception ex) {

        }
        return returnValue;
    }

    public static int compareDates(String firstDate, String secondDate) {
        int returnValue = 1;
        try {

            SimpleDateFormat formatter;

            if (firstDate == null || firstDate.length() == 0) {
                return -1;
            }

            if (secondDate == null || secondDate.length() == 0) {
                return 1;
            }

            if (firstDate.split("_").length > 1 && secondDate.split("_").length > 1) {
                formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
            } else if (firstDate.split(" ").length > 1 && secondDate.split(" ").length > 1) {
                formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            } else {
                formatter = new SimpleDateFormat("yyyyMMdd");
            }

            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(formatter.parse(firstDate));
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(formatter.parse(secondDate));
            if (cal1.after(cal2)) {
                returnValue = 1;
            } else if (cal1.before(cal2)) {
                returnValue = -1;
            } else if (cal1.equals(cal2)) {
                returnValue = 0;
            }
        } catch (Exception ex) {

        }
        return returnValue;
    }

    public static String GetCurrentFormattedDateTime(final String format) {
        // yyyyMMdd_HHmmss
        return (new SimpleDateFormat(format)).format(Calendar.getInstance().getTime());
    }

    public static long GetDateDifferenceInSeconds(String firstDate, String secondDate) {
        try {
            SimpleDateFormat formatter;
            if (firstDate == null || firstDate.length() == 0 || secondDate == null || secondDate.length() == 0) {
                return 0;
            }

            if (firstDate.split("_").length > 1 && secondDate.split("_").length > 1) {
                formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
            } else {
                formatter = new SimpleDateFormat("yyyyMMdd");
            }
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(formatter.parse(firstDate));
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(formatter.parse(secondDate));
            long milliseconds1 = cal1.getTimeInMillis();
            long milliseconds2 = cal2.getTimeInMillis();
            long diff = milliseconds2 - milliseconds1;
            long diffSeconds = diff / 1000;
            return diffSeconds;
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "dateDifference()", PMWF_Log.getStringFromStackTrace(ex));
            return 0;
        }
    }

    public static String getAddedDateValue(String fullDate) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Date date = formatter.parse(fullDate);

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date);

        cal1.add(Calendar.MINUTE, 1);

        return formatter.format(cal1.getTime());
    }

    public static String getFormattedDateTimeValue(String fullDate) {
        String dateValue = "";
        try {
            if (fullDate != null && fullDate.length() > 0) {
                if (fullDate.contains("_")) {
                    String[] datetime = fullDate.split("_");
                    if (datetime != null && datetime.length >= 2) {
                        String date = getFormattedDate(datetime[0]);
                        String time = getFormattedTime(datetime[1]);
                        dateValue = date + " " + time;
                    }
                } else {
                    dateValue = fullDate;
                }
            }
        } catch (Exception ex) {
            PMWF_Log.fnlog(PMWF_Log.ERROR, "getFormattedDateTimeValue()", PMWF_Log.getStringFromStackTrace(ex));
        }
        return dateValue;
    }

    public static String ConvertDateFormat(String date, String oldFormat, String newFormat) {
        try {
            SimpleDateFormat df1 = new SimpleDateFormat(oldFormat);// yyyy-MM-dd'T'HH:mm:ss'Z'
            SimpleDateFormat df2 = new SimpleDateFormat(newFormat);// dd-MM-yyyy
            return df2.format(df1.parse(date));
        } catch (Exception e) {
        }
        return null;
    }

    public static String getFormattedDateValue(String fullDate, String format) throws Exception {
        SimpleDateFormat formatter;
        if (format == null || !(format.length() > 0)) {
            return fullDate;
        }

        if (fullDate.indexOf("_") > 0) {
            formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
        } else {
            formatter = new SimpleDateFormat("yyyyMMdd");
        }
        Date date = formatter.parse(fullDate);

        formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }

}
