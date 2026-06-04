package com.softpos.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;

public class DateUtil {

    private static final SimpleDateFormat LOCAL_FMT = new SimpleDateFormat("dd/MM/yyyy");
    private static final SimpleDateFormat EN_FMT = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
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
            System.err.println("DateControl:getDate:" + e.getMessage());
        }

        return d;
    }

    public static String toMySqlInsert(Date date) {
        try {
            return EN_FMT.format(date);
        } catch (Exception e) {
            return EN_FMT.format(new Date());
        }
    }

    public static String toDateLocal(Date date) {
        try {
            return LOCAL_FMT.format(date);
        } catch (Exception e) {
            return LOCAL_FMT.format(new Date());
        }
    }

    public static Date getDateFormat(String dateTimeString, String dateFormat) {
        SimpleDateFormat simp = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
        try {
            return simp.parse(dateTimeString);
        } catch (ParseException e) {
            AppLogUtil.log(DateUtil.class, "error", e);

        }
        return null;
    }

    public static String getDateFormat(Date date, String dateFormat) {
        SimpleDateFormat simp = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
        return simp.format(date);
    }

    private DateUtil() {
        throw new AssertionError();
    }

    public static int getMaxDay(GregorianCalendar month) {
        int nday;
        switch (month.get(Calendar.MONTH)) {
            case 0:
                nday = 31;
                break;
            case 1:
                nday = month.isLeapYear(month.get(Calendar.MONTH)) ? 29 : 28;
                break;
            case 2:
                nday = 31;
                break;
            case 3:
                nday = 30;
                break;
            case 4:
                nday = 31;
                break;
            case 5:
                nday = 30;
                break;
            case 6:
                nday = 31;
                break;
            case 7:
                nday = 31;
                break;
            case 8:
                nday = 30;
                break;
            case 9:
                nday = 31;
                break;
            case 10:
                nday = 30;
                break;
            case 11:
                nday = 31;
                break;
            default:
                nday = 0;
                break;
        }
        return nday;
    }

    public static int getDistanceDays(Calendar gc1, Calendar gc2) {
        int elapsed = 0;
        GregorianCalendar g1, g2;
        if (gc2.after(gc1)) {
            g2 = (GregorianCalendar) gc2.clone();
            g1 = (GregorianCalendar) gc1.clone();
        } else {
            g2 = (GregorianCalendar) gc1.clone();
            g1 = (GregorianCalendar) gc2.clone();
        }
        g1.clear(Calendar.MILLISECOND);
        g1.clear(Calendar.SECOND);
        g1.clear(Calendar.MINUTE);
        g1.clear(Calendar.HOUR_OF_DAY);
        g2.clear(Calendar.MILLISECOND);
        g2.clear(Calendar.SECOND);
        g2.clear(Calendar.MINUTE);
        g2.clear(Calendar.HOUR_OF_DAY);
        while (g1.before(g2)) {
            g1.add(Calendar.DATE, 1);
            elapsed++;
        }
        return elapsed;
    }

    public static int getDistanceMonths(Calendar gc1, Calendar gc2) {
        int elapsed = 0;
        GregorianCalendar g1, g2;
        if (gc2.after(gc1)) {
            g2 = (GregorianCalendar) gc2.clone();
            g1 = (GregorianCalendar) gc1.clone();
        } else {
            g2 = (GregorianCalendar) gc1.clone();
            g1 = (GregorianCalendar) gc2.clone();
        }
        g1.clear(Calendar.MILLISECOND);
        g1.clear(Calendar.SECOND);
        g1.clear(Calendar.MINUTE);
        g1.clear(Calendar.HOUR_OF_DAY);
        g1.clear(Calendar.DATE);
        g2.clear(Calendar.MILLISECOND);
        g2.clear(Calendar.SECOND);
        g2.clear(Calendar.MINUTE);
        g2.clear(Calendar.HOUR_OF_DAY);
        g2.clear(Calendar.DATE);
        while (g1.before(g2)) {
            g1.add(Calendar.MONTH, 1);
            elapsed++;
        }
        return elapsed;
    }

    public static long getDistanceMillis(Calendar gc1, Calendar gc2) {
        Date d1 = gc1.getTime();
        Date d2 = gc2.getTime();
        long l1 = d1.getTime();
        long l2 = d2.getTime();
        long difference = l2 - l1;
        return difference;
    }

    public static long getDistanceSeconds(Calendar gc1, Calendar gc2) {
        Date d1 = gc1.getTime();
        Date d2 = gc2.getTime();
        long l1 = d1.getTime();
        long l2 = d2.getTime();
        long difference = l2 - l1;
        return difference / 1000;
    }

    public static int getMaximumDay(Calendar gc1) {
        gc1.set(Calendar.DAY_OF_MONTH, 1);
        GregorianCalendar gc2 = (GregorianCalendar) gc1.clone();
        gc2.add(Calendar.MONTH, 1);
        int count = 0;
        while (gc1.compareTo(gc2) <= -1) {
            gc1.add(Calendar.DAY_OF_MONTH, 1);
            count++;
        }
        return count;
    }

    public static GregorianCalendar fusionDate(Date day, Date time) {
        SimpleDateFormat year = new SimpleDateFormat("yyyy", Locale.ENGLISH);
        SimpleDateFormat month = new SimpleDateFormat("M", Locale.ENGLISH);
        SimpleDateFormat date = new SimpleDateFormat("d", Locale.ENGLISH);
        SimpleDateFormat hour = new SimpleDateFormat("H", Locale.ENGLISH);
        SimpleDateFormat minute = new SimpleDateFormat("m", Locale.ENGLISH);
        SimpleDateFormat second = new SimpleDateFormat("s", Locale.ENGLISH);
        GregorianCalendar cal = new GregorianCalendar(
                Integer.parseInt(year.format(day)),
                Integer.parseInt(month.format(day)) - 1,
                Integer.parseInt(date.format(day)),
                Integer.parseInt(hour.format(time)),
                Integer.parseInt(minute.format(time)),
                Integer.parseInt(second.format(time)));

        return cal;
    }

    public static GregorianCalendar convertDateToCal(Date day) {
        SimpleDateFormat year = new SimpleDateFormat("yyyy", Locale.ENGLISH);
        SimpleDateFormat month = new SimpleDateFormat("M", Locale.ENGLISH);
        SimpleDateFormat date = new SimpleDateFormat("d", Locale.ENGLISH);
        SimpleDateFormat hour = new SimpleDateFormat("H", Locale.ENGLISH);
        SimpleDateFormat minute = new SimpleDateFormat("m", Locale.ENGLISH);
        SimpleDateFormat second = new SimpleDateFormat("s", Locale.ENGLISH);
        GregorianCalendar cal = null;
        try {
            cal = new GregorianCalendar(
                    Integer.parseInt(year.format(day)),
                    Integer.parseInt(month.format(day)) - 1,
                    Integer.parseInt(date.format(day)),
                    Integer.parseInt(hour.format(day)),
                    Integer.parseInt(minute.format(day)),
                    Integer.parseInt(second.format(day)));
        } catch (NumberFormatException ex) {
        }
        return cal;
    }

    public static Time lunarStay(Calendar lunarLanding, Calendar lunarDeparture) {

        long timeInMillis = getDistanceMillis(lunarLanding, lunarDeparture);
        long timeInSecons = getDistanceSeconds(lunarLanding, lunarDeparture);

        long day, hours, minutes, seconds, millis;

        hours = timeInSecons / 3600;
        timeInSecons = timeInSecons - (hours * 3600);
        minutes = timeInSecons / 60;
        timeInSecons = timeInSecons - (minutes * 60);
        seconds = timeInSecons;

        millis = timeInMillis % 1000;

        day = hours / 24;
        hours = hours % 24;

        return new Time(day, (int) hours, (int) minutes, (int) seconds, (int) millis);
    }

    public static String addZero(long i) {
        return (i >= 0 && i < 10) ? "0" + i : "" + i;
    }

    public static int getDistanceDays(GregorianCalendar gc1, GregorianCalendar gc2) {
        int elapsed = 0;
        GregorianCalendar g1, g2;
        if (gc2.after(gc1)) {
            g2 = (GregorianCalendar) gc2.clone();
            g1 = (GregorianCalendar) gc1.clone();
        } else {
            g2 = (GregorianCalendar) gc1.clone();
            g1 = (GregorianCalendar) gc2.clone();
        }
        g1.clear(Calendar.MILLISECOND);
        g1.clear(Calendar.SECOND);
        g1.clear(Calendar.MINUTE);
        g1.clear(Calendar.HOUR_OF_DAY);
        g2.clear(Calendar.MILLISECOND);
        g2.clear(Calendar.SECOND);
        g2.clear(Calendar.MINUTE);
        g2.clear(Calendar.HOUR_OF_DAY);
        while (g1.before(g2)) {
            g1.add(Calendar.DATE, 1);
            elapsed++;
        }
        return elapsed;
    }

    public static int getDistanceMonths(GregorianCalendar gc1, GregorianCalendar gc2) {
        int elapsed = 0;
        GregorianCalendar g1, g2;
        if (gc2.after(gc1)) {
            g2 = (GregorianCalendar) gc2.clone();
            g1 = (GregorianCalendar) gc1.clone();
        } else {
            g2 = (GregorianCalendar) gc1.clone();
            g1 = (GregorianCalendar) gc2.clone();
        }
        g1.clear(Calendar.MILLISECOND);
        g1.clear(Calendar.SECOND);
        g1.clear(Calendar.MINUTE);
        g1.clear(Calendar.HOUR_OF_DAY);
        g1.clear(Calendar.DATE);
        g2.clear(Calendar.MILLISECOND);
        g2.clear(Calendar.SECOND);
        g2.clear(Calendar.MINUTE);
        g2.clear(Calendar.HOUR_OF_DAY);
        g2.clear(Calendar.DATE);
        while (g1.before(g2)) {
            g1.add(Calendar.MONTH, 1);
            elapsed++;
        }
        return elapsed;
    }

    public static long getDistanceMillis(GregorianCalendar gc1, GregorianCalendar gc2) {
        Date d1 = gc1.getTime();
        Date d2 = gc2.getTime();
        long l1 = d1.getTime();
        long l2 = d2.getTime();
        long difference = l2 - l1;
        return difference;
    }

    public static long getDistanceSeconds(GregorianCalendar gc1, GregorianCalendar gc2) {
        Date d1 = gc1.getTime();
        Date d2 = gc2.getTime();
        long l1 = d1.getTime();
        long l2 = d2.getTime();
        long difference = l2 - l1;
        return difference / 1000;
    }

    public static String calcHMS(long timeInSeconds) {
        long hours, minutes, seconds;
        hours = timeInSeconds / 3600;
        timeInSeconds = timeInSeconds - (hours * 3600);
        minutes = timeInSeconds / 60;
        timeInSeconds = timeInSeconds - (minutes * 60);
        seconds = timeInSeconds;
        return addZero(hours) + ":" + addZero(minutes) + ":" + addZero(seconds) + "";
    }

    public static String lunarStay(GregorianCalendar lunarLanding, GregorianCalendar lunarDeparture) {
        long lunarStay = getDistanceSeconds(lunarLanding, lunarDeparture);
        return calcHMS(lunarStay);
    }

    public static int getMaximumDay(GregorianCalendar gc1) {
        gc1.set(Calendar.DAY_OF_MONTH, 1);
        GregorianCalendar gc2 = (GregorianCalendar) gc1.clone();
        gc2.add(Calendar.MONTH, 1);
        int count = 0;
        while (gc1.compareTo(gc2) <= -1) {
            gc1.add(Calendar.DAY_OF_MONTH, 1);
            count++;
        }
        return count;
    }

}
