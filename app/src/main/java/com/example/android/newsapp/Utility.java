package com.example.android.newsapp;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class Utility {

    /**
     * Creates a Date starting from a formatted String
     *
     * @param dateString the Sting to be converted to Date. The String must be coherent with the format specified in the strDateFormat variable
     * @param dateFormat dd-MM-yyyy H:mm
     * @return The Date resulting from the String
     */
    public static Date convertStringToDate(String dateString, String dateFormat) {
        Date date = null;
        SimpleDateFormat objSDF = new SimpleDateFormat(dateFormat); //Date format string is passed as an argument to the Date format object
        try {
            date = objSDF.parse(dateString);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return date;
    }
}
