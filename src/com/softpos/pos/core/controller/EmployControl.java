package com.softpos.pos.core.controller;

import com.softpos.pos.core.controller.ThaiUtil;
import database.MySQLConnect;
import java.sql.ResultSet;

public class EmployControl {
//    private  MySQLConnect c =new MySQLConnect();

    public boolean checkEmployUse() {
        MySQLConnect mysql = new MySQLConnect();
        try {
            mysql.open();
            String sql = "select P_EmpUse from posconfigsetup where P_EmpUse='Y';";
            ResultSet rs = mysql.getConnection().createStatement().executeQuery(sql);
            if (rs.next()) {
                return true;
            }
            rs.close();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            mysql.close();
        }
        return false;
    }

    public boolean isEmployExists(String empCode) {
        MySQLConnect mysql = new MySQLConnect();
        try {
            mysql.open();
            String sql = "select * from employ where code='" + empCode + "'";
            ResultSet rs = mysql.getConnection().createStatement().executeQuery(sql);
            if (rs.next()) {
                return true;
            }
            rs.close();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            mysql.close();
        }
        return false;
    }

    public String empName(String empCode) {
        String empName = "";
        MySQLConnect mysql = new MySQLConnect();
        try {
            mysql.open();
            String sql = "select name from employ where code='" + empCode + "'";
            ResultSet rs = mysql.getConnection().createStatement().executeQuery(sql);
            if (rs.next() && !rs.wasNull()) {
                empName = ThaiUtil.ASCII2Unicode(rs.getString("name"));
            } else {
                empName = "";
            }
            rs.close();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            mysql.close();
        }

        return empName;
    }

}
