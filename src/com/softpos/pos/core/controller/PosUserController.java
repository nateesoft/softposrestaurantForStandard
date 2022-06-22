package com.softpos.pos.core.controller;

import com.softpos.pos.core.model.PosUserBean;
import database.MySQLConnect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import util.AppLogUtil;
import util.MSG;

/**
 *
 * @author nathee
 */
public class PosUserController {

    public PosUserBean getPosUser() {
        PosUserBean bean = null;
        MySQLConnect mysql = new MySQLConnect();
        mysql.open(PosUserController.class);
        try {
            String sql = "select Username, Sale3 from posuser "
                    + "where username='" + Value.USERCODE + "' limit 1";
            try (Statement stmt = mysql.getConnection().createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                if (rs.next()) {
                    bean = new PosUserBean();
                    bean.setSale2(rs.getString("Sale2"));
                    bean.setUserName(rs.getString("Username"));
                }
            }
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            AppLogUtil.log(PosUserController.class, "error", e);
        } finally {
            mysql.close();
        }

        return bean;
    }
}
