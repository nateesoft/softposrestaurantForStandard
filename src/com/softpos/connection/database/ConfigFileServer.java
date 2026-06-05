package com.softpos.connection.database;

import com.softpos.util.AppLogUtil;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConfigFileServer {

    public static final String FILE_CONFIG = "C:/erp_conf.txt";

    public static String getProperties(String keyword) {
        String str = "";
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new DataInputStream(new FileInputStream(FILE_CONFIG))))) {
            String tmp;
            while ((tmp = br.readLine()) != null) {
                if (tmp.contains(keyword)) {
                    str = tmp.split(":", tmp.length())[1];
                    break;
                }
            }
        } catch (IOException e) {
            AppLogUtil.log(ConfigFileServer.class, "error", e);
        }
        return str.trim();
    }
}
