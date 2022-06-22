package com.softpos.crm.pos.core.controller;

import com.softpos.crm.pos.core.modal.MTranBean;
import com.softpos.pos.core.controller.Value;
import database.MySQLConnect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import util.AppLogUtil;
import util.DateUtil;
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
            mysql.open(MTranController.class);
            String sql = "select * from " + Value.db_member + ".mtran where Branch_Code='" + branchCode + "' limit 1";
            try (ResultSet rs = mysql.getConnection().createStatement().executeQuery(sql)) {
                if (rs.next()) {
                    bean = mappingBean(rs);
                }
            }
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            AppLogUtil.log(MTranController.class, "error", e);
        } finally {
            mysql.close();
        }

        return bean;
    }

    public boolean checkReceiptNoExist(String receiptNo) {
        boolean isNoExist = true;
        MySQLConnect mysql = new MySQLConnect();
        try {
            mysql.open(this.getClass());
            String sql = "select Receipt_No from " + Value.db_member + ".mtran "
                    + "where Receipt_No='" + receiptNo + "' limit 1";
            try (ResultSet rs = mysql.getConnection().createStatement().executeQuery(sql)) {
                if (rs.next()) {
                    isNoExist = false;
                }
            }
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            AppLogUtil.log(MTranController.class, "error", e);
        } finally {
            mysql.close();
        }
        return isNoExist;
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

    public int create(MTranBean bean) {
        int resultCreate = 0;

        MySQLConnect mysql = new MySQLConnect();
        mysql.open(this.getClass());

        try {
            String sql = "insert into " + Value.db_member + ".mtran "
                    + "(Service_Date, Member_Code, Branch_Code, Receipt_No, Sale_Type, GrossAmount, "
                    + "DiscountAmount, NetAmount, Mechine_Code, Employee_Code, Service_Time, Score, TranferFlag) "
                    + "values ('" + DateUtil.getDateFormat(bean.getService_Date(), "yyyy-MM-dd") + "', "
                    + "'" + bean.getMember_Code() + "', '" + bean.getBranch_Code() + "', "
                    + "'" + bean.getReceipt_No() + "', '" + bean.getSale_Type() + "', '" + bean.getGrossAmount() + "', "
                    + "'" + bean.getDiscountAmount() + "', '" + bean.getNetAmount() + "', '" + bean.getMechine_Code() + "', "
                    + "'" + bean.getEmployee_Code() + "', '" + bean.getService_Time() + "', '" + bean.getScore() + "', "
                    + "'" + bean.getTranferFlag() + "')";
            try (Statement stmt = mysql.getConnection().createStatement()) {
                resultCreate = stmt.executeUpdate(sql);
            }
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            AppLogUtil.log(MTranController.class, "error", e);
        } finally {
            mysql.close();
        }

        return resultCreate;
    }

    public void refundBill(String receiptNo) {
        MySQLConnect mysql = new MySQLConnect();
        mysql.open(this.getClass());

        try {
            String sql = "delete from " + Value.db_member + ".mtran "
                    + "where receipt_no='" + receiptNo + "'";
            try (Statement stmt = mysql.getConnection().createStatement()) {
                stmt.executeUpdate(sql);
            }
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            AppLogUtil.log(MTranController.class, "error", e);
        } finally {
            mysql.close();
        }
    }
}
