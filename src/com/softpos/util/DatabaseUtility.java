package com.softpos.util;

import com.softpos.connection.database.MySQLConnect;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseUtility {
    
    private final MySQLConnect mysqlConnect = new MySQLConnect();

    public int insert(String sql, Object... args) {
        
        mysqlConnect.open(this.getClass());
        try (PreparedStatement pstmt = mysqlConnect.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            for (int i = 0; i < args.length; i++) {
                pstmt.setObject(i + 1, args[i]);
            }
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
            return -1;
        } catch (SQLException e) {

            AppLogUtil.log(DatabaseUtility.class, "error", e);
            return -1;
        } finally {
            mysqlConnect.close();
        }
    }

    public int executeUpdate(String sql, Object... args) {
        mysqlConnect.open(this.getClass());
        try (PreparedStatement pstmt = mysqlConnect.getConnection().prepareStatement(sql)) {
            for (int i = 0; i < args.length; i++) {
                pstmt.setObject(i + 1, args[i]);
            }
            return pstmt.executeUpdate();
        } catch (SQLException e) {

            AppLogUtil.log(DatabaseUtility.class, "error", e);
            return -1;
        } finally {
            mysqlConnect.close();
        }
    }

    public Map<String, Object> querySingle(String sql, Object... args) {
        Map<String, Object> map = new HashMap<>();
        mysqlConnect.open(this.getClass());
        try (PreparedStatement pstmt = mysqlConnect.getConnection().prepareStatement(sql)) {
            for (int i = 0; i < args.length; i++) {
                pstmt.setObject(i + 1, args[i]);
            }
            try (ResultSet rs = pstmt.executeQuery()) {
                ResultSetMetaData md = rs.getMetaData();
                if (rs.next()) {
                    for (int i = 1; i <= md.getColumnCount(); i++) {
                        Object obj = null;
                        try {
                            obj = rs.getObject(i);
                        } catch (SQLException ex) {
                            if (ex.getMessage().contains("'0000-00-00'")) {
                                obj = null;
                            } else {
                                throw new RuntimeException(ex);
                            }
                        }
                        map.put(md.getColumnLabel(i).toLowerCase(), obj);
                    }
                }
            }
        } catch (SQLException | RuntimeException e) {

            AppLogUtil.log(DatabaseUtility.class, "error", e);
        } finally {
            mysqlConnect.close();
        }
        return map;
    }

    public List<Map<String, Object>> queryList(String sql, Object... args) {
        List<Map<String, Object>> list = new ArrayList<>();
        mysqlConnect.open(this.getClass());
        try (PreparedStatement pstmt = mysqlConnect.getConnection().prepareStatement(sql)) {
            for (int i = 0; i < args.length; i++) {
                pstmt.setObject(i + 1, args[i]);
            }
            try (ResultSet rs = pstmt.executeQuery()) {
                ResultSetMetaData md = rs.getMetaData();
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    for (int i = 1; i <= md.getColumnCount(); i++) {
                        Object obj = null;
                        try {
                            obj = rs.getObject(i);
                        } catch (SQLException ex) {
                            if (ex.getMessage().contains("'0000-00-00'")) {
                                obj = null;
                            } else {
                                throw new RuntimeException(ex);
                            }
                        }
                        row.put(md.getColumnLabel(i).toLowerCase(), obj);
                    }
                    list.add(row);
                }
            }
        } catch (SQLException | RuntimeException e) {

            AppLogUtil.log(DatabaseUtility.class, "error", e);
        } finally {
            mysqlConnect.close();
        }
        return list;
    }

}
