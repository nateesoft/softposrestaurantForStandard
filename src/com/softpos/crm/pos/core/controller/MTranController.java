/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softpos.crm.pos.core.controller;

import com.softpos.crm.pos.core.modal.MTranBean;
import com.softpos.main.program.Value;
import database.MySQLConnect;
import java.sql.ResultSet;
import java.sql.SQLException;
import util.MSG;

/**
 *
 * @author nathee
 */
public class MTranController {

    public static MTranBean getData(String branchCode) {
        MTranBean bean = null;
        MySQLConnect mysql = new MySQLConnect();

        try {
            mysql.open();
            String sql = "select * from " + Value.db_member + ".mtran where Branch_Code='" + branchCode + "'";
            try (ResultSet rs = mysql.getConnection().createStatement().executeQuery(sql)) {
                if (rs.next()) {
                    bean = mappingBean(rs);
                }
            }
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
        } finally {
            mysql.close();
        }

        return bean;
    }

    private static MTranBean mappingBean(ResultSet rs) throws SQLException {
        MTranBean bean = new MTranBean();
        bean.setService_Date(rs.getDate("Service_Date"));
        bean.setMember_Code(rs.getString("Member_Code"));
        bean.setBranch_Code(rs.getString("Branch_Code"));
        bean.setReceipt_No(rs.getString("Receipt_No"));
        bean.setSale_Type(rs.getString("Sale_Type"));
        bean.setGrossAmount(rs.getFloat("GrossAmount"));
        bean.setDiscountAmount(rs.getFloat("DiscountAmount"));
        bean.setNetAmount(rs.getFloat("NetAmount"));
        bean.setMechine_Code(rs.getString("Mechine_Code"));
        bean.setEmployee_Code(rs.getString("Employee_Code"));
        bean.setService_Time(rs.getString("Service_Time"));
        bean.setScore(rs.getFloat("Score"));
        bean.setTranferFlag(rs.getString("TranferFlag"));

        return bean;
    }
}
