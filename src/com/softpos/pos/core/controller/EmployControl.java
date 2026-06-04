package com.softpos.pos.core.controller;

import com.softpos.util.AppLogUtil;
import com.softpos.util.ThaiUtil;
import database.MySQLConnect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployControl {
    private final MySQLConnect mysqlConnect = new MySQLConnect();

    public String empName(String empCode) {
        String empName = "";
        try {
            mysqlConnect.open();
            String sql = "select name from employ where code='" + empCode + "'";
            try (ResultSet rs = mysqlConnect.executeQuery(sql)) {
                if (rs.next() && !rs.wasNull()) {
                    empName = ThaiUtil.ASCII2Unicode(rs.getString("name"));
                } else {
                    empName = "";
                }
            }

        } catch (SQLException e) {
            AppLogUtil.log(EmployControl.class, "error", e);
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
            try (ResultSet rs = mysqlConnect.executeQuery(sql)) {
                while (rs.next()) {
                    String code = rs.getString("code");
                    String name = ThaiUtil.ASCII2Unicode(rs.getString("name"));
                    employees.add(new String[]{code, name});
                }
            }
        } catch (SQLException e) {
            AppLogUtil.log(EmployControl.class, "error", e);
        } finally {
            mysqlConnect.close();
        }
        return employees;
    }

}
