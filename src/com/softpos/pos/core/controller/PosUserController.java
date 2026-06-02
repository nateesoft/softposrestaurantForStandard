package com.softpos.pos.core.controller;

import com.softpos.pos.core.model.PosUserBean;
import database.MySQLConnect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.softpos.util.AppLogUtil;

/**
 *
 * @author nathee
 */
public class PosUserController {
    
    private final MySQLConnect mysql = new MySQLConnect();

    public PosUserBean getPosUser() {
        PosUserBean bean = null;
        
        mysql.open(PosUserController.class);
        try {
            String sql = "select Username, Sale2, Sale3 from posuser where username='" + Value.USERCODE + "' limit 1";
            try (Statement stmt = mysql.getConnection().createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                if (rs.next()) {
                    bean = new PosUserBean();
                    bean.setSale2(rs.getString("Sale2"));
                    bean.setSale3(rs.getString("Sale3"));
                    bean.setUserName(rs.getString("Username"));
                }
                rs.close();
                stmt.close();
            }
        } catch (SQLException e) {
            AppLogUtil.log(PosUserController.class, "error", e);
        } finally {
            mysql.closeConnection(this.getClass());
        }

        return bean;
    }
}
