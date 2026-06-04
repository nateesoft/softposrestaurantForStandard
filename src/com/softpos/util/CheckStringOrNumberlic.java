package com.softpos.util;

/**
 *
 * @author Dell-Softpos
 */
public class CheckStringOrNumberlic {

    public static boolean CheckStringOrNumberlic(String text) {
        boolean numeric = true;
        try {
            Double.parseDouble(text);
        } catch (NumberFormatException e) {
            numeric = false;
        }

        return numeric;
    }
}
