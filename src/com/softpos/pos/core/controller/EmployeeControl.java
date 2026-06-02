package com.softpos.pos.core.controller;

import com.softpos.util.ThaiUtil;
import com.softpos.pos.core.model.EmployeeBean;
import database.MySQLConnect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nateesun
 */
public class EmployeeControl {

    private final MySQLConnect mysqlConnect = new MySQLConnect();
    private List<EmployeeBean> listAll = null;

    public List<EmployeeBean> initLoadEmployeeList() {
        if (listAll == null) {
            listAll = new ArrayList<>();
            
            try {
                mysqlConnect.open(this.getClass());
                ResultSet rs = mysqlConnect.executeQuery("select * from employ");
                while (rs.next()) {
                    EmployeeBean bean = new EmployeeBean();
                    bean.setCode(rs.getString("code"));
                    bean.setName(ThaiUtil.ASCII2Unicode(rs.getString("name")));
                    bean.setSalary(rs.getFloat("salary"));
                    bean.setPosition(rs.getString("position"));

                    listAll.add(bean);
                }
                rs.close();
            } catch (SQLException e) {

            } finally {
                mysqlConnect.closeConnection(this.getClass());
            }
        }

        return listAll;
    }

    public EmployeeBean getEmployeeArray(String code) {
        if (listAll != null) {
            for (int i = 0; i < listAll.size(); i++) {
                EmployeeBean bean = listAll.get(i);
                if (code.equals(bean.getCode())) {
                    return bean;
                }
            }
        }

        return new EmployeeBean("", "");
    }

    public boolean getEmployeeByCode(String code) {
        boolean isValid = false;
        mysqlConnect.open(EmployeeControl.class);
        try {
            String sql = "select code,name from employ "
                    + "where Code='" + code + "' limit 1;";
            Statement stmt = mysqlConnect.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                isValid = true;
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {

        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }

        return isValid;
    }
}
