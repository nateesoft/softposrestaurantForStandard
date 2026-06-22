package com.softpos.pos.core.controller;


import com.softpos.util.ThaiUtil;
import com.softpos.main.login.Login;
import com.softpos.pos.core.model.LoginBean;
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
public class LoginControl {
    
    private final MySQLConnect mysqlConnect = new MySQLConnect();

    public LoginBean validateLogin(String username, String password) {
        LoginBean loginBean = new LoginBean();
        loginBean.setValidLogin(false);

        mysqlConnect.open(this.getClass());
        try {
            String sql = "select username,password,name,onact,macno from posuser "
                    + "where username= '" + ThaiUtil.Unicode2ASCII(username) + "' "
                    + "and password='" + ThaiUtil.Unicode2ASCII(password) + "' limit 1";
            try (Statement stmt = mysqlConnect.getConnection().createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
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
            mysqlConnect.closeConnection(this.getClass());
        }

        return loginBean;
    }

    public void clearTablefileIfNoBalance() {
        mysqlConnect.open(this.getClass());
        try {
            String sql = "select * from balance limit 1";
            try (ResultSet rs = mysqlConnect.executeQuery(sql)) {
                if (!rs.next()) {
                    String sqlTablefile = "update tablefile "
                            + "set tonact='N',tpause='Y',titem='0',tamount='0',tcustomer='0',nettotal='0';";
                    mysqlConnect.executeUpdate(sqlTablefile);
                }
            }
        } catch (SQLException e) {
            AppLogUtil.log(this.getClass(), "error", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }
    }

    public void updateLogin(String UserCode) {

        
        mysqlConnect.open(this.getClass());
        try {
            String SQLQuery = "update posuser set "
                    + "onact='Y',"
                    + "macno='" + PublicVar.MACNO + "' "
                    + "where username='" + UserCode + "'";
            Statement stmt = mysqlConnect.getConnection().createStatement();
            stmt.executeUpdate(SQLQuery);
            PublicVar.CASHIER = UserCode;
            String sql = "update posuser set "
                    + "onact='N' "
                    + "where username<>'" + UserCode + "' "
                    + "and macno='" + PublicVar.MACNO + "';";
            mysqlConnect.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }
    }
}
