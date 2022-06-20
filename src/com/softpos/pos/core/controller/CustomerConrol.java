package com.softpos.pos.core.controller;

import com.softpos.pos.core.model.CustomerBean;
import database.MySQLConnect;
import java.sql.ResultSet;
import java.sql.SQLException;
import util.AppLogUtil;
import util.MSG;

/**
 *
 * @author Dell
 */
public class CustomerConrol {

    public CustomerBean getCustomer(String custCode) {
        CustomerBean bean = new CustomerBean();
        String sql = "select * from customer where sp_code='" + custCode + "' limit 1";
        MySQLConnect mysql = new MySQLConnect();
        mysql.close();

        try {
            mysql.open();
            ResultSet rs = mysql.getConnection().createStatement().executeQuery(sql);
            if (rs.next()) {
                bean.setSp_code(rs.getString("sp_code"));
                bean.setSp_Desc(ThaiUtil.ASCII2Unicode(rs.getString("sp_Desc")));
                bean.setSp_Addr1(ThaiUtil.ASCII2Unicode(rs.getString("sp_Addr1")));
                bean.setSp_Addr2(ThaiUtil.ASCII2Unicode(rs.getString("sp_Addr2")));
                bean.setSp_zip(rs.getString("sp_zip"));
                bean.setTel(rs.getString("tel"));
                bean.setFax(rs.getString("fax"));
                bean.setContack(ThaiUtil.ASCII2Unicode(rs.getString("Contack")));
                bean.setRemark(ThaiUtil.ASCII2Unicode(rs.getString("Remark")));
                bean.setRemark2(ThaiUtil.ASCII2Unicode(rs.getString("Remark2")));
            }
            mysql.close();
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            AppLogUtil.log(CustomerConrol.class, "error", e);
        } finally {
            mysql.close();
        }
        
        return bean;

    }
}
