package com.softpos.pos.core.controller;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import util.MSG;

public class Value {

    public static String[] OPT = new String[]{"", "", "", "", "", "", "", "", ""};
    public static final double PRICE_BY_USER = -9999999.99;
    public static int countConnectDB = 0;
    public static String MACNO = "";
    public static String FILE_CONFIG = "connect.ini";
    public static String FILE_CONFIGOnline = "webconnect.ini";
    public static String FILE_LOG = "log.txt";
    public static String FILE_BG = "img/bg.jpg";
    public static String DATABASE = "";
    public static String CopyRight = "SoftRestaurant by SOFTPOS ©2014";
    public static String TABLE;
    public static String LANG = "";
    public static String CASHIER;
    public static String BTN_FLOORPLAN;
    public static String USERCODE;
    public static String TableSelected = "";
    public static String TEMP_TABLE_REFUND = "999";
    public static String db_member = "";
    public static boolean printdriver = true;
    public static String printerDriverName = "SoftPrint";
    public static String printerDriverKitChenName = "";
    public static String takeorder = "N";
    public static boolean useprint = false;
    public static boolean printkic = false;
    public static boolean autoqty = false;
    public static boolean MemberAlready = false;

    public static String driverNotSupport = "ยังไม่ Support การพิมพ์ผ่าน Driver !";
    public static String EMP_CODE = "";
//    public static String MemberCodeSelected = "";
//    public static String MemberNameSelected = "";

    public static String getDateDefault() {
        SimpleDateFormat simp = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Calendar c = Calendar.getInstance(Locale.ENGLISH);
        c.set(2000, 1, 1);

        return simp.format(c.getTime());
    }

    public static void ClearOPT() {
        OPT = new String[]{"", "", "", "", "", "", "", "", ""};
    }

    public static String getComPort() {
        return POSHWSetup.Bean(Value.MACNO).getPRNPort();
    }
}
