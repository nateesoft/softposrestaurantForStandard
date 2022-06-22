package com.softpos.pos.core.controller;

import com.softpos.login.Login;
import com.softpos.pos.core.model.LoginBean;
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
public class LoginController {

    public LoginBean validateLogin(String username, String password) {
        LoginBean loginBean = new LoginBean();
        loginBean.setValidLogin(false);

        MySQLConnect mysql = new MySQLConnect();
        mysql.open(this.getClass());
        try {
            String sql = "select username,password,name,onact,macno from posuser "
                    + "where username= '" + ThaiUtil.Unicode2ASCII(username) + "' "
                    + "and password='" + ThaiUtil.Unicode2ASCII(password) + "' limit 1";
            try (Statement stmt = mysql.getConnection().createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                if (rs.next()) {
                    loginBean.setValidLogin(true);
                    loginBean.setUsername(rs.getString("username"));
                    loginBean.setPassword(rs.getString("password"));
                    loginBean.setOnact(rs.getString("onact"));
                    loginBean.setMacno(rs.getString("macno"));
                    loginBean.setName(rs.getString("name"));
                }
            }
        } catch (SQLException e) {
            MSG.ERR(null, e.getMessage());
            AppLogUtil.log(Login.class, "error", e);
        } finally {
            mysql.close();
        }

        return loginBean;
    }
    
    public void updateLogin(String UserCode) {
        /**
         * * OPEN CONNECTION **
         */
        MySQLConnect mysql = new MySQLConnect();
        mysql.open(this.getClass());
        try {
            String SQLQuery = "update posuser set "
                    + "onact='Y',"
                    + "macno='" + Value.MACNO + "' "
                    + "where username='" + UserCode + "'";
            Statement stmt = mysql.getConnection().createStatement();
            stmt.executeUpdate(SQLQuery);
            Value.CASHIER = UserCode;
            String sql = "update posuser set "
                    + "onact='N' "
                    + "where username<>'" + UserCode + "' "
                    + "and macno='" + Value.MACNO + "';";
            mysql.getConnection().createStatement().executeUpdate(sql);
        } catch (SQLException e) {
            MSG.ERR(null, e.getMessage());
        } finally {
            mysql.close();
        }
    }
}
