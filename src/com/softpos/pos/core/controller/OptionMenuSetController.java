package com.softpos.pos.core.controller;

import com.softpos.util.ThaiUtil;
import com.softpos.connection.database.MySQLConnect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.softpos.util.AppLogUtil;

public class OptionMenuSetController {

    private final MySQLConnect mysqlConnect = new MySQLConnect();

    public void saveOption(String pcode, String pdesc, String opcode, String opname) {
        mysqlConnect.open(OptionMenuSetController.class);
        try {
            String sql = "INSERT INTO optionset (PCode, PDesc, OptionCode, OptionName) "
                    + "VALUES ('" + pcode + "', '" + ThaiUtil.Unicode2ASCII(pdesc) + "', "
                    + "'" + opcode + "', '" + ThaiUtil.Unicode2ASCII(opname) + "');";
            try (Statement stmt = mysqlConnect.getConnection().createStatement()) {
                stmt.executeUpdate(sql);
            }
        } catch (SQLException e) {
            AppLogUtil.log(OptionMenuSetController.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(OptionMenuSetController.class);
        }
    }

    public void deleteOption(String pcode, String optionname) {
        mysqlConnect.open(OptionMenuSetController.class);
        try {
            String sql = "delete from optionset "
                    + "where pcode='" + pcode + "' "
                    + "and optionname='" + ThaiUtil.Unicode2ASCII(optionname) + "'";
            try (Statement stmt = mysqlConnect.getConnection().createStatement()) {
                stmt.executeUpdate(sql);
            }
        } catch (SQLException e) {
            AppLogUtil.log(OptionMenuSetController.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(OptionMenuSetController.class);
        }
    }

    /** Returns rows from optionset for pCode. Each row: {PCode(unicode), PDesc(unicode), OptionName(unicode)} */
    public List<Object[]> loadOptions(String pcode) {
        List<Object[]> list = new ArrayList<>();
        mysqlConnect.open(OptionMenuSetController.class);
        try {
            String sql = "select * from optionset where PCode = '" + pcode + "'";
            try (Statement stmt = mysqlConnect.getConnection().createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    list.add(new Object[]{
                        ThaiUtil.ASCII2Unicode(rs.getString("PCode")),
                        ThaiUtil.ASCII2Unicode(rs.getString("PDesc")),
                        ThaiUtil.ASCII2Unicode(rs.getString("OptionName"))
                    });
                }
            }
        } catch (SQLException e) {
            AppLogUtil.log(OptionMenuSetController.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(OptionMenuSetController.class);
        }
        return list;
    }
}
