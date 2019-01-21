package com.ujazdowski.client.ui.validator;

import java.util.Date;
import java.util.regex.Pattern;

public class Validator {
    // Only positive double and integer
    private static Pattern integerPattern = Pattern.compile("\\d+");
    private static Pattern doublePattern = Pattern.compile("\\d+(.\\d+)?");

    public static boolean notEmptyStringValidator(String string) {
        return string.isEmpty();
    }

    public static boolean isDouble(String dbl) {
        return doublePattern.matcher(dbl).matches();
    }

    public static boolean isLong(String dbl) {
        return integerPattern.matcher(dbl).matches();
    }

    public static boolean isAfterToday(Date date) {
        return date.after(new Date());
    }
}
