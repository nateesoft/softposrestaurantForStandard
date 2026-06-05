package com.softpos.pos.core.controller;

import com.softpos.constants.PublicVar;



public class PropControl {

    public static String PATH_CONFIG = "softpos/";
    public static String FILE_CONFIG = "softpos/config.properties";

    public String getMacNo() {
        return PublicVar.MACNO;
    }

}
