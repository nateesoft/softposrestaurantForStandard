package com.softpos.pos.core.controller;

import com.softpos.connection.database.MySQLConnect;
import java.sql.SQLException;
import java.sql.Statement;
import com.softpos.util.AppLogUtil;

public class ItemEditQtyControl {

    private final MySQLConnect mysqlConnect = new MySQLConnect();

    /**
     * Executes all three SQL statements (update balance, update tablefile, insert tempeditqty)
     * under a single connection.
     */
    public void saveItemEdits(String sqlUpdateBalance, String sqlUpdateTableFile, String sqlInsertEditQty) {
        mysqlConnect.open(ItemEditQtyControl.class);
        try {
            Statement stmt = mysqlConnect.getConnection().createStatement();
            stmt.executeUpdate(sqlUpdateBalance);
            System.out.println(sqlUpdateBalance);
            stmt.executeUpdate(sqlUpdateTableFile);
            stmt.executeUpdate(sqlInsertEditQty);
            stmt.close();
        } catch (SQLException e) {
            AppLogUtil.log(ItemEditQtyControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(ItemEditQtyControl.class);
        }
    }
}
