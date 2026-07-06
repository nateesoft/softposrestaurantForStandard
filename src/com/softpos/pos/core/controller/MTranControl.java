package com.softpos.pos.core.controller;


import com.softpos.pos.core.model.MTranBean;
import com.softpos.connection.database.MySQLConnect;
import com.softpos.constants.PublicVar;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.softpos.util.AppLogUtil;
import com.softpos.util.DateUtil;

/**
 *
 * @author nathee
 */
public class MTranControl {
    private final MySQLConnect mysqlConnect = new MySQLConnect();

    public MTranBean getData(String branchCode) {
        MTranBean bean = null;
        
        try {
            mysqlConnect.open(MTranControl.class);
            String sql = "select * from " + PublicVar.db_member + ".mtran where Branch_Code='" + branchCode + "' limit 1";
            try (ResultSet rs = mysqlConnect.executeQuery(sql)) {
                if (rs.next()) {
                    bean = mappingBean(rs);
                }
                rs.close();
            }
        } catch (SQLException e) {
            AppLogUtil.log(MTranControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(MTranControl.class);
        }

        return bean;
    }

    public boolean checkReceiptNoExist(String receiptNo) {
        boolean isNoExist = true;
        try {
            mysqlConnect.open(MTranControl.class);
            String sql = "select Receipt_No from " + PublicVar.db_member + ".mtran "
                    + "where Receipt_No='" + receiptNo + "' limit 1";
            try (ResultSet rs = mysqlConnect.executeQuery(sql)) {
                if (rs.next()) {
                    isNoExist = false;
                }
                rs.close();
            }
        } catch (SQLException e) {
            AppLogUtil.log(MTranControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(MTranControl.class);
        }
        return isNoExist;
    }

    private static MTranBean mappingBean(ResultSet rs) throws SQLException {
        MTranBean mTranBean = new MTranBean();
        mTranBean.setService_Date(rs.getDate("Service_Date"));
        mTranBean.setMember_Code(rs.getString("Member_Code"));
        mTranBean.setBranch_Code(rs.getString("Branch_Code"));
        mTranBean.setReceipt_No(rs.getString("Receipt_No"));
        mTranBean.setSale_Type(rs.getString("Sale_Type"));
        mTranBean.setGrossAmount(rs.getFloat("GrossAmount"));
        mTranBean.setDiscountAmount(rs.getFloat("DiscountAmount"));
        mTranBean.setNetAmount(rs.getFloat("NetAmount"));
        mTranBean.setMechine_Code(rs.getString("Mechine_Code"));
        mTranBean.setEmployee_Code(rs.getString("Employee_Code"));
        mTranBean.setService_Time(rs.getString("Service_Time"));
        mTranBean.setScore(rs.getFloat("Score"));
        mTranBean.setTranferFlag(rs.getString("TranferFlag"));

        return mTranBean;
    }

    public int create(MTranBean bean) {
        int resultCreate = 0;

        mysqlConnect.open(MTranControl.class);

        try {
            String sql = "insert into " + PublicVar.db_member + ".mtran "
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
            AppLogUtil.log(MTranControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(MTranControl.class);
        }

        return resultCreate;
    }

    public void refundBill(String receiptNo) {
        mysqlConnect.open(MTranControl.class);

        try {
            String sql = "delete from " + PublicVar.db_member + ".mtran "
                    + "where receipt_no='" + receiptNo + "'";
            try (Statement stmt = mysqlConnect.getConnection().createStatement()) {
                stmt.executeUpdate(sql);
                stmt.close();
            }
        } catch (SQLException e) {
            AppLogUtil.log(MTranControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(MTranControl.class);
        }
    }
}
