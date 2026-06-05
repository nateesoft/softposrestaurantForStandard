package com.softpos.pos.core.controller;

import com.softpos.constants.Value;

public class PropControl {

    public static String PATH_CONFIG = "softpos/";
    public static String FILE_CONFIG = "softpos/config.properties";

    public String getMacNo() {
        return Value.MACNO;
    }

}
