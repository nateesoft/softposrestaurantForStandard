package com.softpos.pos.core.controller;

import com.softpos.crm.pos.core.modal.MTranBean;
import database.MySQLConnect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.softpos.util.AppLogUtil;
import com.softpos.util.DateUtil;

/**
 *
 * @author nathee
 */
public class MTranController {
    
    private final MySQLConnect mysqlConnect = new MySQLConnect();

    public MTranBean getData(String branchCode) {
        MTranBean bean = null;
        

        try {
            mysqlConnect.open(MTranController.class);
            String sql = "select * from " + Value.db_member + ".mtran where Branch_Code='" + branchCode + "' limit 1";
            try (ResultSet rs = mysqlConnect.executeQuery(sql)) {
                if (rs.next()) {
                    bean = mappingBean(rs);
                }
                rs.close();
            }
        } catch (SQLException e) {
            AppLogUtil.log(MTranController.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(MTranController.class);
        }

        return bean;
    }

    public boolean checkReceiptNoExist(String receiptNo) {
        boolean isNoExist = true;
        try {
            mysqlConnect.open(MTranController.class);
            String sql = "select Receipt_No from " + Value.db_member + ".mtran "
                    + "where Receipt_No='" + receiptNo + "' limit 1";
            try (ResultSet rs = mysqlConnect.executeQuery(sql)) {
                if (rs.next()) {
                    isNoExist = false;
                }
                rs.close();
            }
        } catch (SQLException e) {
            AppLogUtil.log(MTranController.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(MTranController.class);
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

        mysqlConnect.open(MTranController.class);

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
            try (Statement stmt = mysqlConnect.getConnection().createStatement()) {
                resultCreate = stmt.executeUpdate(sql);
                stmt.close();
            }
        } catch (SQLException e) {
            AppLogUtil.log(MTranController.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(MTranController.class);
        }

        return resultCreate;
    }

    public void refundBill(String receiptNo) {
        mysqlConnect.open(MTranController.class);

        try {
            String sql = "delete from " + Value.db_member + ".mtran "
                    + "where receipt_no='" + receiptNo + "'";
            try (Statement stmt = mysqlConnect.getConnection().createStatement()) {
                stmt.executeUpdate(sql);
                stmt.close();
            }
        } catch (SQLException e) {
            AppLogUtil.log(MTranController.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(MTranController.class);
        }
    }
}
