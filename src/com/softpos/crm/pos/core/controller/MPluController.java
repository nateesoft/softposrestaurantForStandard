package com.softpos.crm.pos.core.controller;

import com.softpos.crm.pos.core.modal.MPluBean;
import com.softpos.main.program.Value;
import database.MySQLConnect;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            String sql = "select * from " + Value.db_member + ".mplu where Branch_Code='" + branchCode + "'";
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
}
