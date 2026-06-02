package com.softpos.pos.core.controller;

import com.softpos.util.ThaiUtil;
import com.softpos.main.login.Login;
import com.softpos.pos.core.model.LoginBean;
import database.MySQLConnect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.softpos.util.AppLogUtil;

/**
 *
 * @author nathee
 */
public class LoginController {
    
    private final MySQLConnect mysql = new MySQLConnect();

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
                stmt.close();
                rs.close();
            }
        } catch (SQLException e) {
            AppLogUtil.log(Login.class, "error", e);
        } finally {
            mysql.closeConnection(this.getClass());
        }

        return loginBean;
    }

    public void updateLogin(String UserCode) {
        /**
         * * OPEN CONNECTION **
         */
        
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
            mysql.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
        } finally {
            mysql.closeConnection(this.getClass());
        }
    }
}
