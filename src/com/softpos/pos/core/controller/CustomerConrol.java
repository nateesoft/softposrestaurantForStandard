package com.softpos.pos.core.controller;

import com.softpos.util.ThaiUtil;
import com.softpos.pos.core.model.CustomerBean;
import database.MySQLConnect;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.softpos.util.AppLogUtil;

/**
 *
 * @author Dell
 */
public class CustomerConrol {

    private final MySQLConnect mysql = new MySQLConnect();
    
    public CustomerBean getCustomer(String custCode) {
        CustomerBean bean = new CustomerBean();
        String sql = "select * from customer where sp_code='" + custCode + "' limit 1";
        
        try {
            mysql.open(this.getClass());
            ResultSet rs = mysql.executeQuery(sql);
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
            rs.close();
        } catch (SQLException e) {
            AppLogUtil.log(CustomerConrol.class, "error", e);
        } finally {
            mysql.closeConnection(this.getClass());
        }

        return bean;

    }
}
