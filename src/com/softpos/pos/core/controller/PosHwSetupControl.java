package com.softpos.pos.core.controller;

import com.softpos.connection.database.MySQLConnect;
import com.softpos.util.AppLogUtil;

/**
 *
 * @author nateelive
 */
public class PosHwSetupControl {
    private final MySQLConnect mysqlConnect = new MySQLConnect();

    public boolean canUpdateOnActByTerminal(String macno) {
        mysqlConnect.open();
        try {
            String sql = "update poshwsetup set onact='N' "
                        + "where(terminal='" + macno + "')";
            return mysqlConnect.executeUpdate(sql) > 0;
        } catch (Exception e) {
            AppLogUtil.log(PosHwSetupControl.class, "error", e);
        } finally {
            mysqlConnect.close();
        }
        
        return false;
    }
}
