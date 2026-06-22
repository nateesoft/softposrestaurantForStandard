package com.softpos.pos.core.controller;

import com.softpos.pos.core.model.PosUserBean;
import com.softpos.connection.database.MySQLConnect;
import com.softpos.constants.PublicVar;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.softpos.util.AppLogUtil;

/**
 *
 * @author nathee
 */
public class PosUserController {

    private final MySQLConnect mysqlConnect = new MySQLConnect();

    public PosUserBean authenticate(String username, String password) {
        PosUserBean bean = null;

        mysqlConnect.open(PosUserController.class);
        try {
            String sql = "select username, sale2 from posuser where (username='" + username + "') and (password='" + password + "') limit 1";
            try (Statement stmt = mysqlConnect.getConnection().createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                if (rs.next()) {
                    bean = new PosUserBean();
                    bean.setUserName(rs.getString("username"));
                    bean.setSale2(rs.getString("sale2"));
                }
                rs.close();
                stmt.close();
            }
        } catch (SQLException e) {
            AppLogUtil.log(PosUserController.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }

        return bean;
    }

    public PosUserBean getPosUser() {
        PosUserBean bean = null;

        mysqlConnect.open(PosUserController.class);
        try {
            String sql = "select Username, Sale2, Sale3 from posuser where username='" + PublicVar.USERCODE + "' limit 1";
            try (Statement stmt = mysqlConnect.getConnection().createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
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
            mysqlConnect.closeConnection(this.getClass());
        }

        return bean;
    }

    public void updateLogout(String username) {
        String sql1 = "update posuser set onact='N',macno='' where (username='" + username + "')";
        mysqlConnect.executeUpdate(sql1);
    }

    public void updateOnActIsNByUsername(String UserCode) {
        String sql = "update posuser set onact='N', macno='' where username='" + UserCode + "'";
        mysqlConnect.executeUpdate(sql);
    }
}
