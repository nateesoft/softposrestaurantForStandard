package com.softpos.pos.core.controller;


import com.softpos.pos.core.model.MPluBean;
import com.softpos.util.ThaiUtil;
import com.softpos.connection.database.MySQLConnect;
import com.softpos.constants.PublicVar;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import com.softpos.util.AppLogUtil;

/**
 *
 * @author nathee
 */
public class MPluController {
    
    private final MySQLConnect mysqlConnect = new MySQLConnect();

    public MPluBean getData(String branchCode) {
        MPluBean bean = null;
        
        try {
            mysqlConnect.open(MPluController.class);
            String sql = "select * from " + PublicVar.db_member + ".mplu where Branch_Code='" + branchCode + "' limit 1";
            try (ResultSet rs = mysqlConnect.executeQuery(sql)) {
                if (rs.next()) {
                    bean = mappingBean(rs);
                }
                rs.close();
            }
        } catch (SQLException e) {
            AppLogUtil.log(MPluController.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(MPluController.class);
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
        int[] resultCreate = new int[0];
        mysqlConnect.open(MPluController.class);
        try {
            String sql = "insert into " + PublicVar.db_member + ".mplu "
                    + "(Service_Date, Member_Code, Branch_Code, Receipt_No, PLU_Group, Sale_Type, "
                    + "PLU_GroupName, PLU_Code, PLU_Name, PLU_Amount, PLU_Quantity, PLU_Price, TranferFlag) "
                    + "values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement prm = mysqlConnect.getConnection().prepareStatement(sql);
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
            prm.close();
        } catch (SQLException e) {
            AppLogUtil.log(MPluController.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(MPluController.class);
        }

        return resultCreate.length;
    }

    public void refundBill(String receiptNo) {
        mysqlConnect.open(MPluController.class);

        try {
            String sql = "delete from " + PublicVar.db_member + ".mplu "
                    + "where receipt_no='" + receiptNo + "'";
            try (Statement stmt = mysqlConnect.getConnection().createStatement()) {
                stmt.executeUpdate(sql);
                stmt.close();
            }
        } catch (SQLException e) {
            AppLogUtil.log(MPluController.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(MPluController.class);
        }
    }
}
