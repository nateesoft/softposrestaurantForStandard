package com.softpos.pos.core.controller;

import com.softpos.pos.core.model.EmployeeBean;
import database.MySQLConnect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.MSG;

/**
 *
 * @author nateesun
 */
public class EmployeeControl {

    private List<EmployeeBean> listAll = null;

    public List<EmployeeBean> initLoadEmployeeList() {
        if (listAll == null) {
            listAll = new ArrayList<>();
            MySQLConnect mysql = new MySQLConnect();
            try {
                mysql.open(this.getClass());
                ResultSet rs = mysql.getConnection().createStatement().executeQuery("select * from employ");
                while (rs.next()) {
                    EmployeeBean bean = new EmployeeBean();
                    bean.setCode(rs.getString("code"));
                    bean.setName(ThaiUtil.ASCII2Unicode(rs.getString("name")));
                    bean.setSalary(rs.getFloat("salary"));
                    bean.setPosition(rs.getString("position"));

                    listAll.add(bean);
                }
            } catch (SQLException e) {
                MSG.ERR(e.getMessage());
            } finally {
                mysql.close();
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
}
