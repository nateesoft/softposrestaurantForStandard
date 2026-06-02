package com.softpos.pos.core.controller;

import com.softpos.util.ThaiUtil;
import database.MySQLConnect;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EmployControl {
    private final MySQLConnect mysqlConnect = new MySQLConnect();

    public boolean checkEmployUse() {        
        try {
            mysqlConnect.open();
            String sql = "select P_EmpUse from posconfigsetup where P_EmpUse='Y';";
            ResultSet rs = mysqlConnect.executeQuery(sql);
            if (rs.next()) {
                return true;
            }
            rs.close();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            mysqlConnect.close();
        }
        return false;
    }

    public boolean isEmployExists(String empCode) {
        try {
            mysqlConnect.open();
            String sql = "select * from employ where code='" + empCode + "'";
            ResultSet rs = mysqlConnect.executeQuery(sql);
            if (rs.next()) {
                return true;
            }
            rs.close();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            mysqlConnect.close();
        }
        return false;
    }

    public String empName(String empCode) {
        String empName = "";
        try {
            mysqlConnect.open();
            String sql = "select name from employ where code='" + empCode + "'";
            ResultSet rs = mysqlConnect.executeQuery(sql);
            if (rs.next() && !rs.wasNull()) {
                empName = ThaiUtil.ASCII2Unicode(rs.getString("name"));
            } else {
                empName = "";
            }
            rs.close();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            mysqlConnect.close();
        }

        return empName;
    }

    public List<String[]> getAllEmployees() {
        List<String[]> employees = new ArrayList<>();
        try {
            mysqlConnect.open();
            String sql = "select * from employ order by code";
            ResultSet rs = mysqlConnect.executeQuery(sql);
            while (rs.next()) {
                String code = rs.getString("code");
                String name = ThaiUtil.ASCII2Unicode(rs.getString("name"));
                employees.add(new String[]{code, name});
            }
            rs.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            mysqlConnect.close();
        }
        return employees;
    }

}
