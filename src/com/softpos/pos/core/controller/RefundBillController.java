package com.softpos.pos.core.controller;

import com.softpos.pos.core.model.BillNoBean;
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
public class RefundBillController extends DatabaseConnection {
    
    public BillNoBean checkBillByRefno(String macno, String billNo) {
        BillNoBean bean = null;
        MySQLConnect mysql = new MySQLConnect();
        mysql.open(this.getClass());
        try {
            String sql = "select * from billno "
                    + "where (b_macno='" + macno + "') and (b_refno='" + billNo + "')";
            Statement stmt = mysql.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){
                bean = new BillNoBean();
                bean.setB_Void(rs.getString("b_void"));
                bean.setB_InvNo(rs.getString("b_invno"));
                bean.setB_MacNo(rs.getString("b_macno"));
                bean.setB_MemCode(rs.getString("b_memcode"));
            }
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            AppLogUtil.log(RefundBillController.class, "error", e);
        } finally {
            mysql.close();
        }
        
        return bean;
    }
}
