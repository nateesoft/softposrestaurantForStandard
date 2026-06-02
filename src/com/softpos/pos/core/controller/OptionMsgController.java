package com.softpos.pos.core.controller;

import com.softpos.util.ThaiUtil;
import database.MySQLConnect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.softpos.util.AppLogUtil;

public class OptionMsgController {

    private final MySQLConnect mysqlConnect = new MySQLConnect();
    
    public List<String> loadOptionsByGroup(String pGroup) {
        List<String> list = new ArrayList<>();
        
        mysqlConnect.open(OptionMsgController.class);
        try {
            String sql = "select * from optionfile where pgroup='" + pGroup + "'";
            try (Statement stmt = mysqlConnect.getConnection().createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    list.add(ThaiUtil.ASCII2Unicode(rs.getString("optionname")));
                }
            }
        } catch (SQLException e) {
            AppLogUtil.log(OptionMsgController.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(OptionMsgController.class);
        }
        return list;
    }

    public void updateBalanceOptions(String rIndex, String tableNo, String[] opts) {
        String[] o = new String[]{"", "", "", "", "", "", "", ""};
        for (int i = 0; i < opts.length && i < 8; i++) {
            o[i] = opts[i];
        }
        mysqlConnect.open(OptionMsgController.class);
        try {
            String sql = "update balance set "
                    + "r_opt1='" + ThaiUtil.Unicode2ASCII(o[0]) + "',"
                    + "r_opt2='" + ThaiUtil.Unicode2ASCII(o[1]) + "',"
                    + "r_opt3='" + ThaiUtil.Unicode2ASCII(o[2]) + "',"
                    + "r_opt4='" + ThaiUtil.Unicode2ASCII(o[3]) + "',"
                    + "r_opt5='" + ThaiUtil.Unicode2ASCII(o[4]) + "',"
                    + "r_opt6='" + ThaiUtil.Unicode2ASCII(o[5]) + "',"
                    + "r_opt7='" + ThaiUtil.Unicode2ASCII(o[6]) + "',"
                    + "r_opt8='" + ThaiUtil.Unicode2ASCII(o[7]) + "' "
                    + "where r_index='" + rIndex + "' "
                    + "and r_table='" + tableNo + "'";
            try (Statement stmt = mysqlConnect.getConnection().createStatement()) {
                stmt.executeUpdate(sql);
            }
        } catch (SQLException e) {
            AppLogUtil.log(OptionMsgController.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(OptionMsgController.class);
        }
    }

    public void addOption(String pGroup, String optionName) {
        mysqlConnect.open(OptionMsgController.class);
        try {
            String sqlDel = "delete from optionfile "
                    + "where PGroup='" + pGroup + "' "
                    + "and OptionName='" + ThaiUtil.Unicode2ASCII(optionName) + "'";
            String sqlIns = "insert into optionfile(PGroup, OptionName) "
                    + "values('" + pGroup + "','" + ThaiUtil.Unicode2ASCII(optionName) + "');";
            try (Statement stmt = mysqlConnect.getConnection().createStatement()) {
                stmt.executeUpdate(sqlDel);
                stmt.executeUpdate(sqlIns);
            }
        } catch (SQLException e) {
            AppLogUtil.log(OptionMsgController.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(OptionMsgController.class);
        }
    }
}
