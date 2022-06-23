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
        boolean isValid = false;
        
        MySQLConnect mysql = new MySQLConnect();
        mysql.open(DatabaseConnection.class);
        try {
            int result = mysql.getConnection().createStatement().executeUpdate(sql);
            isValid = result > 0;
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            AppLogUtil.log(DatabaseConnection.class, "error", e);
        } finally {
            mysql.close();
        }
        
        return isValid;
    }

}
