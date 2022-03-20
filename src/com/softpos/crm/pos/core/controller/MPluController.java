package com.softpos.crm.pos.core.controller;

import com.softpos.crm.pos.core.modal.MPluBean;
import com.softpos.main.program.Value;
import database.MySQLConnect;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import sun.natee.project.util.ThaiUtil;
import util.MSG;

/**
 *
 * @author nathee
 */
public class MPluController {

    public static MPluBean getData(String branchCode) {
        MPluBean bean = null;
        /**
         * * OPEN CONNECTION **
         */
        MySQLConnect mysql = new MySQLConnect();

        try {
            mysql.open();
            String sql = "select * from " + Value.db_member + ".mplu "
                    + "where Branch_Code='" + branchCode + "'";
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

    private static MPluBean mappingBean(ResultSet rs) throws SQLException {
        MPluBean bean = new MPluBean();
        bean.setService_Date(rs.getDate("Service_Date"));
        bean.setMember_Code(rs.getString("Member_Code"));
        bean.setBranch_Code(rs.getString("Branch_Code"));
        bean.setReceipt_No(rs.getString("Receipt_No"));
        bean.setPLU_Group(rs.getString("PLU_Group"));
        bean.setSale_Type(rs.getString("Sale_Type"));
        bean.setPLU_GroupName(ThaiUtil.ASCII2Unicode(rs.getString("PLU_GroupName")));
        bean.setPLU_Code(rs.getString("PLU_Code"));
        bean.setPLU_Name(ThaiUtil.ASCII2Unicode(rs.getString("PLU_Name")));
        bean.setPLU_Amount(rs.getFloat("PLU_Amount"));
        bean.setPLU_Quantity(rs.getFloat("PLU_Quantity"));
        bean.setPLU_Price(rs.getFloat("PLU_Price"));
        bean.setTranferFlag(rs.getString("TranferFlag"));

        return bean;
    }

    public int create(List<MPluBean> listMPlu) {
        int [] resultCreate = new int[0];
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        try {
            String sql = "insert into " + Value.db_member + ".mplu "
                    + "(Service_Date, Member_Code, Branch_Code, Receipt_No, PLU_Group, Sale_Type, "
                    + "PLU_GroupName, PLU_Code, PLU_Name, PLU_Amount, PLU_Quantity, PLU_Price, TranferFlag) "
                    + "values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement prm = mysql.getConnection().prepareStatement(sql);
            for (MPluBean bean : listMPlu) {
                prm.setDate(1, new Date(bean.getService_Date().getTime()));
                prm.setString(2, bean.getMember_Code());
                prm.setString(3, bean.getBranch_Code());
                prm.setString(4, bean.getReceipt_No());
                prm.setString(5, bean.getPLU_Group());
                prm.setString(6, bean.getSale_Type());
                prm.setString(7, ThaiUtil.Unicode2ASCII(bean.getPLU_GroupName()));
                prm.setString(8, bean.getPLU_Code());
                prm.setString(9, ThaiUtil.Unicode2ASCII(bean.getPLU_Name()));
                prm.setDouble(10, bean.getPLU_Amount());
                prm.setDouble(11, bean.getPLU_Quantity());
                prm.setDouble(12, bean.getPLU_Price());
                prm.setString(13, bean.getTranferFlag());
                prm.addBatch();
            }

            prm.clearParameters();
            resultCreate = prm.executeBatch();
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
        } finally {
            mysql.close();
        }

        return resultCreate.length;
    }

    public void refundBill(String receiptNo) {
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();

        try {
            String sql = "delete from " + Value.db_member + ".mplu "
                    + "where receipt_no='" + receiptNo + "'";
            try (Statement stmt = mysql.getConnection().createStatement()) {
                stmt.executeUpdate(sql);
            }
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
        } finally {
            mysql.close();
        }
    }
}
