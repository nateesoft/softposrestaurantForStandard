package com.softpos.pos.core.controller;

import database.MySQLConnect;
import java.sql.SQLException;
import util.AppLogUtil;
import util.MSG;

/**
 *
 * @author nathee
 */
public class DatabaseConnection {
    
    public boolean execUpdate(String sql) {
        MySQLConnect mysql = new MySQLConnect();
        mysql.open(TableFileControl.class);
        try {
            mysql.getConnection().createStatement().executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            AppLogUtil.log(TableFileControl.class, "error", e);
            return false;
        } finally {
            mysql.close();
        }
    }

}
