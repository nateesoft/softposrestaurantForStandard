package com.softpos.pos.core.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import util.MSG;

public class DateControl {

    public static final SimpleDateFormat F1 = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
    public static final SimpleDateFormat F2 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    public static final SimpleDateFormat F3 = new SimpleDateFormat("E", Locale.ENGLISH);
    public static final SimpleDateFormat T1 = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
    public static final SimpleDateFormat T2 = new SimpleDateFormat("HH:mm", Locale.ENGLISH);

    public static String GET_CURRENT_NAME_DAY() {
        return F3.format(new Date());
    }

    public static Date getDate(String date, String pattern) {
        Date d = null;

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            d = sdf.parse(date);
        } catch (ParseException e) {
            MSG.ERR("DateControl:getDate:" + e.getMessage());
        }

        return d;
    }

}
