package com.softpos.pos.core.controller;

import com.softpos.util.AppLogUtil;
import com.softpos.util.ThaiUtil;
import com.softpos.connection.database.MySQLConnect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CreditFileControl {

    private final MySQLConnect mysqlConnect = new MySQLConnect();

    public List<Object[]> findByBank(String bank) {
        List<Object[]> rows = new ArrayList<>();
        mysqlConnect.open(CreditFileControl.class);
        try {
            String sql;
            if (bank != null && !bank.isEmpty()) {
                sql = "Select * from creditfile where crbank='" + bank + "' order by crbank,crcode";
            } else {
                sql = "Select * from creditfile order by crbank,crcode";
            }
            Statement stmt = mysqlConnect.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                rows.add(new Object[]{
                    rs.getString("crbank"),
                    rs.getString("crcode"),
                    ThaiUtil.ASCII2Unicode(rs.getString("crname")),
                    rs.getFloat("crcharge"),
                    rs.getFloat("crredule"),
                    rs.getString("crgetcardno")
                });
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            AppLogUtil.log(CreditFileControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(CreditFileControl.class);
        }
        return rows;
    }
}
