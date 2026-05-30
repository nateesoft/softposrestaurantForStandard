package com.softpos.pos.core.controller;

import database.MySQLConnect;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import com.softpos.util.AppLogUtil;

public class DatabaseConnection {

    public boolean execUpdate(String sql) {
        MySQLConnect mysql = new MySQLConnect();
        mysql.open(DatabaseConnection.class);
        try {
            return mysql.executeUpdate(sql) > 0;
        } catch (Exception e) {
            AppLogUtil.log(DatabaseConnection.class, "error", e);
            return false;
        } finally {
            mysql.close();
        }
    }

    public boolean execBatch(List<String> sqls) {
        if (sqls == null || sqls.isEmpty()) {
            return true;
        }
        MySQLConnect mysql = new MySQLConnect();
        mysql.open(DatabaseConnection.class);
        try (Statement stmt = mysql.getConnection().createStatement()) {
            for (String sql : sqls) {
                stmt.addBatch(sql);
            }
            stmt.executeBatch();
            return true;
        } catch (SQLException e) {
            AppLogUtil.log(DatabaseConnection.class, "error", e);
            return false;
        } finally {
            mysql.close();
        }
    }

}
