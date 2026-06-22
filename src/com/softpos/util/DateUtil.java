package com.softpos.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateUtil {

    private static final SimpleDateFormat LOCAL_FMT = new SimpleDateFormat("dd/MM/yyyy");
    private static final SimpleDateFormat EN_FMT = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    public static final SimpleDateFormat F1 = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
    public static final SimpleDateFormat F2 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    public static final SimpleDateFormat F3 = new SimpleDateFormat("E", Locale.ENGLISH);
    public static final SimpleDateFormat T1 = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
    public static final SimpleDateFormat T2 = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
    static SimpleDateFormat locale_ddMMyyyy = new SimpleDateFormat("dd/MM/yyyy");
    static SimpleDateFormat locale_yyyyMMdd = new SimpleDateFormat("yyyy/MM/dd");
    static SimpleDateFormat english_ddMMyyyy = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
    static SimpleDateFormat english_yyyyMMdd = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
    static SimpleDateFormat mysql_date_yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    static SimpleDateFormat mysql_datetime_yyyyMMddHHmmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);


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

    public String dateDatabase(String date) {
        date = date.replace("/", "");
        String dd = date.substring(0, 2);
        String mm = date.substring(2, 4);
        String yyyy = date.substring(4, 8);
        date = yyyy + "-" + mm + "-" + dd;
        return date;
    }

    public String dateGetToShow(String date) {
        date = date.replace("-", "");
        String yyyy = date.substring(0, 4);
        String mm = date.substring(4, 6);
        String dd = date.substring(6, 8);
        date = dd + " / " + mm + " / " + yyyy;
        return date;
    }

    public String GetCurrentDate() {
        SimpleDateFormat GetLocalDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        SimpleDateFormat ShowDatefmt = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        Calendar c = new GregorianCalendar();
        int MM = c.get(Calendar.MONTH);
        int yyyy = c.get(Calendar.YEAR);
        int dd = c.get(Calendar.DATE);
        String dateString = "";
        String DateOrder = "";
        Calendar current = Calendar.getInstance();
        current.add(Calendar.DATE, +2);
        dateString += ShowDatefmt.format(current.getTime());
        Calendar current1 = Calendar.getInstance();
        current1.add(Calendar.DATE, +0);
        DateOrder += GetLocalDate.format(current1.getTime());
        return DateOrder;
    }

    public String GetCurrentDateFM(String FM) {
        SimpleDateFormat GetLocalDate = new SimpleDateFormat(FM, Locale.ENGLISH);
        Calendar c = new GregorianCalendar();
        int MM = c.get(Calendar.MONTH);
        int yyyy = c.get(Calendar.YEAR);
        int dd = c.get(Calendar.DATE);
        String dateString = "";
        String DateOrder = "";
        Calendar current = Calendar.getInstance();
        current.add(Calendar.DATE, +2);
        Calendar current1 = Calendar.getInstance();
        current1.add(Calendar.DATE, +0);
        DateOrder += GetLocalDate.format(current1.getTime());

        return DateOrder;
    }

    public String minusDate(String dateInput, int i) {
        String[] dateStr = dateInput.split("-");//2016-12-31
        int yyyy = Integer.parseInt(dateStr[0]);//2016
        int MM = Integer.parseInt(dateStr[1]);//12
        int dd = Integer.parseInt(dateStr[2]);//31
        Calendar c = Calendar.getInstance(Locale.ENGLISH);
//        c.set(yyyy, MM - 1, dd - 1);//set back date
        c.set(yyyy, MM - 1, dd - i);//set back date
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        String dateUse = s.format(c.getTime());//use date time (format: yyyy-MM-dd);
        return dateUse;
    }

    public String GetCurrentTime() {
        SimpleDateFormat ShowDatefmt = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
        Calendar c = new GregorianCalendar();
        String TimeString = "";
        Calendar current = Calendar.getInstance();
        current.add(Calendar.DATE, 0);
        TimeString += ShowDatefmt.format(current.getTime());

        return TimeString;
    }

    public int getCheckExpireDate(String date) {
        date = date.replace("-", "");
        String cur_Date = GetCurrentDate().replace("-", "");
        int dd = Integer.parseInt(date.substring(6, 8));
        int MM = Integer.parseInt(date.substring(4, 6));
        int yyyy = Integer.parseInt(date.substring(0, 4));

        int cur_dd = Integer.parseInt(cur_Date.substring(6, 8));
        int cur_MM = Integer.parseInt(cur_Date.substring(4, 6));
        int cur_yyyy = Integer.parseInt(cur_Date.substring(0, 4));

        dd = dd - cur_dd;
        MM = MM - cur_MM;
        yyyy = yyyy - cur_yyyy;
        int sum = dd + MM + yyyy;
        return sum;
    }

    public int getCheckExpireTime(String timeStart, String timeExpire) {
        timeStart = timeStart.replace(":", "");
        timeExpire = timeExpire.replace(":", "");
        String cur_Time = GetCurrentTime().replace(":", "");
        int valueReturn = 0;
        int start = Integer.parseInt(timeStart);
        int expire = Integer.parseInt(timeExpire);
        int now = Integer.parseInt(cur_Time);
        if (now > start && now < expire) {
            valueReturn = 1;
        } else {
            valueReturn = -1;
        }
        return valueReturn;

    }
    
    public static Date dateFromJSON(String d) {
        Date d1;
        SimpleDateFormat s = new SimpleDateFormat("MMM d, y", Locale.ENGLISH);
        try {
            d1 = s.parse(d);
        } catch (ParseException e) {
            AppLogUtil.log(DateUtil.class, "error", e);
            Calendar c = Calendar.getInstance(Locale.ENGLISH);
            c.set(2015, 0, 1);
            d1 = c.getTime();
        }

        return d1;
    }

    public static Date dateFromJSONThai(String d) {
        Date d1;
        SimpleDateFormat s = new SimpleDateFormat("MMM d, y");
        try {
            d1 = s.parse(d);
        } catch (ParseException e) {
            AppLogUtil.log(DateUtil.class, "error", e);
            Calendar c = Calendar.getInstance();
            c.set(2015, 0, 1);
            d1 = c.getTime();
        }

        return d1;
    }

    public static String getLocale_ddMMyyyy(Date d) {
        if (d != null) {
            return locale_ddMMyyyy.format(d);
        } else {
            return null;
        }
    }

    public static String getEnglish_ddMMyyyy(Date d) {
        if (d != null) {
            return english_ddMMyyyy.format(d);
        } else {
            return null;
        }
    }

    public static String getMySQL_yyyyMMdd(Date d) {
        if (d != null) {
            return mysql_date_yyyyMMdd.format(d);
        } else {
            return null;
        }
    }

    public static String getMySQL_Date(Date d) {
        if (d != null) {
            return mysql_date_yyyyMMdd.format(d);
        } else {
            return "0000-00-00";
        }
    }

    public static String getMySQL_DateTime(Date d) {
        if (d != null) {
            return mysql_datetime_yyyyMMddHHmmss.format(d);
        } else {
            return null;
        }
    }

    public static String getMyFormat(SimpleDateFormat simp, Date d) {
        if (d != null) {
            return simp.format(d);
        } else {
            return null;
        }
    }

    public static Date getLocal_ddMMyyyy(String s) {
        try {
            return locale_ddMMyyyy.parse(s);
        } catch (ParseException e) {
            AppLogUtil.log(DateUtil.class, "error", e);
            return null;
        }
    }

    public static Date getLocal_yyyyMMdd(String s) {
        try {
            return locale_yyyyMMdd.parse(s);
        } catch (ParseException e) {
            AppLogUtil.log(DateUtil.class, "error", e);
            return null;
        }
    }

    public static Date getEnglish_ddMMyyyy(String s) {
        try {
            return english_ddMMyyyy.parse(s);
        } catch (ParseException e) {
            AppLogUtil.log(DateUtil.class, "error", e);
            return null;
        }
    }

    public static Date getEnglish_yyyyMMdd(String s) {
        try {
            return english_yyyyMMdd.parse(s);
        } catch (ParseException e) {
            AppLogUtil.log(DateUtil.class, "error", e);
            return null;
        }
    }

    public static String sample() {
        return ""
                + "getLocale_ddMMyyyy = 31/12/2557\n"
                + "getEnglish_ddMMyyyy = 31/12/2014\n"
                + "getMySQL_Date = 2014-12-31\n"
                + "getMySQL_DateTime = 2014-12-31 09:09:09\n"
                + "---------------------------------------\n"
                + "getLocal_ddMMyyyy(01/01/2558)\n"
                + "getLocal_yyyyMMdd(2558/01/01)\n";
    }

}
