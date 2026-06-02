package com.softpos.pos.core.controller;

import database.MySQLConnect;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import com.softpos.util.AppLogUtil;

public class DatabaseConnection {

    private final MySQLConnect mysqlConnect = new MySQLConnect();
    
    public boolean execUpdate(String sql) {
        
        mysqlConnect.open(DatabaseConnection.class);
        try {
            return mysqlConnect.executeUpdate(sql) > 0;
        } catch (Exception e) {
            AppLogUtil.log(DatabaseConnection.class, "error", e);
            return false;
        } finally {
            mysqlConnect.close();
        }
    }

    public boolean execBatch(List<String> sqls) {
        if (sqls == null || sqls.isEmpty()) {
            return true;
        }
        mysqlConnect.open(DatabaseConnection.class);
        try (Statement stmt = mysqlConnect.getConnection().createStatement()) {
            for (String sql : sqls) {
                stmt.addBatch(sql);
            }
            stmt.executeBatch();
            return true;
        } catch (SQLException e) {
            AppLogUtil.log(DatabaseConnection.class, "error", e);
            return false;
        } finally {
            mysqlConnect.close();
        }
    }

}
