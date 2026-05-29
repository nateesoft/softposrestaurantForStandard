package com.softpos.pos.core.controller;

import database.MySQLConnect;
import util.AppLogUtil;
import util.MSG;

public class DatabaseConnection {

    public boolean execUpdate(String sql) {
        MySQLConnect mysql = new MySQLConnect();
        mysql.open(DatabaseConnection.class);
        try {
            return mysql.executeUpdate(sql) > 0;
        } catch (Exception e) {
            MSG.ERR(e.getMessage());
            AppLogUtil.log(DatabaseConnection.class, "error", e);
            return false;
        } finally {
            mysql.close();
        }
    }

}
