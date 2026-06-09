package com.softpos.pos.core.controller;

import com.softpos.util.AppLogUtil;
import com.softpos.connection.database.MySQLConnect;

/**
 *
 * @author Dell-Softpos
 */
public class ControlPrintCheckBill {

    private final MySQLConnect mysqlConnect = new MySQLConnect();

    public void setPrintCheckBillItemAfterSendKic(String tableNO) {
        try {
            String sql = "update balance "
                    + "set PDAPrintChekItemStation='Y' "
                    + "where PDAPrintChekItemStation='N' "
                    + "and r_table='" + tableNO + "'";
            mysqlConnect.open();
            mysqlConnect.executeUpdate(sql);
        } catch (Exception e) {
            AppLogUtil.log(ControlPrintCheckBill.class, "error", e);
        } finally {
            mysqlConnect.close();
        }
    }

}
