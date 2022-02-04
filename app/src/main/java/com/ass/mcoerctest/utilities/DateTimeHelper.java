package com.ass.mcoerctest.utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DateTimeHelper {

    public static final DateFormat DF_MEDIUM_UK = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.UK);

    public static String getMediumDateStrUK(Date date) {
        if (date != null) {
            return DF_MEDIUM_UK.format(date);
        }
        return null;
    }

    public static Date toDate(String dateString) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // here set the pattern as you date in string was containing like date/month/year
            date = sdf.parse(dateString);
        } catch (ParseException ex) {
            date = null;
        } finally {
            return date;
        }

    }

    public static Date toTime(String timeString) {
        DateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(timeString);
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            return date;
        }

    }

    public static long getSeconds(String timeString) {
        //Convert tinme string in format "HH:mm:00" to seconds
        if (timeString != null) {
            String[] time = timeString.split(":");
            if (time.length == 3) {
                return Integer.parseInt(time[0]) * 60 * 60 + Integer.parseInt(time[1]) * 60 + Integer.parseInt(time[2]);
            } else {
                return -1;
            }
        }
        return -1;
    }

    public static String getTimeString(long milliSeconds) {
        String timeString = String.format(Locale.getDefault(), "%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(milliSeconds) % 12,
                TimeUnit.MILLISECONDS.toMinutes(milliSeconds) % 60,
                TimeUnit.MILLISECONDS.toSeconds(milliSeconds) % 60);
        return timeString;
    }


}
