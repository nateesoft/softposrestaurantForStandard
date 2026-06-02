package com.softpos.pos.core.controller;

import com.softpos.util.ThaiUtil;
import database.MySQLConnect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.softpos.util.AppLogUtil;

public class VoidMsgController {
    
    private final MySQLConnect mysqlConnect = new MySQLConnect();

    public List<String[]> getAll() {
        List<String[]> list = new ArrayList<>();
        
        mysqlConnect.open(VoidMsgController.class);
        try {
            String sql = "select * from voidmsg";
            Statement stmt = mysqlConnect.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                list.add(new String[]{
                    rs.getString("VCode"),
                    ThaiUtil.ASCII2Unicode(rs.getString("VName"))
                });
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            AppLogUtil.log(VoidMsgController.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(VoidMsgController.class);
        }
        return list;
    }
}
