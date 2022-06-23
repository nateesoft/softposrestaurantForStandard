package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateFormat {

    static SimpleDateFormat locale_ddMMyyyy = new SimpleDateFormat("dd/MM/yyyy");
    static SimpleDateFormat locale_yyyyMMdd = new SimpleDateFormat("yyyy/MM/dd");
    static SimpleDateFormat english_ddMMyyyy = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
    static SimpleDateFormat english_yyyyMMdd = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
    static SimpleDateFormat mysql_date_yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    static SimpleDateFormat mysql_datetime_yyyyMMddHHmmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

    public static Date dateFromJSON(String d) {
        Date d1;
        SimpleDateFormat s = new SimpleDateFormat("MMM d, y", Locale.ENGLISH);
        try {
            d1 = s.parse(d);
        } catch (ParseException e) {
            System.err.println(e.getMessage());
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
            System.err.println(e.getMessage());
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
            System.err.println(e.getMessage());
            return null;
        }
    }

    public static Date getLocal_yyyyMMdd(String s) {
        try {
            return locale_yyyyMMdd.parse(s);
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public static Date getEnglish_ddMMyyyy(String s) {
        try {
            return english_ddMMyyyy.parse(s);
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public static Date getEnglish_yyyyMMdd(String s) {
        try {
            return english_yyyyMMdd.parse(s);
        } catch (ParseException e) {
            System.err.println(e.getMessage());
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
