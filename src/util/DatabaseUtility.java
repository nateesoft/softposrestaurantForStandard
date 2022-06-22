package util;

import database.MySQLConnect;
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

    public int insert(String sql, Object... args) {
        MySQLConnect mysql = new MySQLConnect();
        try {
            mysql.open(this.getClass());
            PreparedStatement pstmt = mysql.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            for (int i = 0; i < args.length; i++) {
                pstmt.setObject(i + 1, args[i]);
            }

            pstmt.executeUpdate();

            //Find Auto Increment Id
            ResultSet rs = pstmt.getGeneratedKeys();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            MSG.ERR(null, e.getMessage());
            AppLogUtil.log(DatabaseUtility.class, "error", e);
            
            return -1;
        } finally {
            mysql.close();
        }
    }

    public int executeUpdate(String sql, Object... args) {
        MySQLConnect mysql = new MySQLConnect();
        try {
            mysql.open(this.getClass());
            PreparedStatement pstmt = mysql.getConnection().prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                pstmt.setObject(i + 1, args[i]);
            }
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            MSG.ERR(null, e.getMessage());
            AppLogUtil.log(DatabaseUtility.class, "error", e);
            
            return -1;
        } finally {
            mysql.close();
        }
    }

    public Map<String, Object> querySingle(String sql, Object... args) {
        Map<String, Object> map = new HashMap<>();
        MySQLConnect mysql = new MySQLConnect();
        try {
            mysql.open(this.getClass());
            PreparedStatement pstmt = mysql.getConnection().prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                pstmt.setObject(i + 1, args[i]);
            }
            ResultSet rs = pstmt.executeQuery();
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
            rs.close();
        } catch (SQLException | RuntimeException e) {
            MSG.ERR(null, e.getMessage());
            AppLogUtil.log(DatabaseUtility.class, "error", e);
        } finally {
            mysql.close();
        }

        return map;
    }

    public List<Map<String, Object>> queryList(String sql, Object... args) {
        List<Map<String, Object>> list = new ArrayList<>();
        MySQLConnect mysql = new MySQLConnect();
        try {
            mysql.open(this.getClass());
            PreparedStatement pstmt = mysql.getConnection().prepareStatement(sql);

            for (int i = 0; i < args.length; i++) {
                pstmt.setObject(i + 1, args[i]);
            }

            ResultSet rs = pstmt.executeQuery();
            ResultSetMetaData md = rs.getMetaData();

            while (rs.next()) {
                Map<String, Object> map;
                map = new HashMap<>();

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

                list.add(map);
            }

            rs.close();
        } catch (SQLException | RuntimeException e) {
            MSG.ERR(null, e.getMessage());
            AppLogUtil.log(DatabaseUtility.class, "error", e);
        } finally {
            mysql.close();
        }

        return list;
    }

}
