package com.ujazdowski.client.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public final static String INSTANT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    private final static SimpleDateFormat sdf = new SimpleDateFormat(INSTANT_DATE_FORMAT);
    private final static SimpleDateFormat printableFormat = new SimpleDateFormat("yyy-MM-dd");
    private final static SimpleDateFormat printableDateTimeFormat = new SimpleDateFormat("HH:mm dd-MM-yyy");

    public static String toInstantFormat(Date date) {
        return sdf.format(date);
    }

    public static String printableFormat(String string) {
        return printableFormat(toDate(string));
    }

    public static String printableFormat(Date date) {
        return printableFormat.format(date);
    }

    public static String printableDateTimeFormat(Date date) {
        return printableDateTimeFormat.format(date);
    }

    public static Date toDate(String date) {
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
