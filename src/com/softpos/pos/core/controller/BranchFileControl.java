package com.softpos.pos.core.controller;


import com.softpos.pos.core.model.BranchFileBean;
import com.softpos.util.ThaiUtil;
import com.softpos.connection.database.MySQLConnect;
import com.softpos.constants.PublicVar;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.softpos.util.AppLogUtil;

/**
 *
 * @author nathee
 */
public class BranchFileControl {
    
    private final MySQLConnect mysqlConnect = new MySQLConnect();

    public BranchFileBean getData(String branchCode) {
        BranchFileBean bean = null;
        
        try {
            mysqlConnect.open(BranchFileControl.class);
            String sql = "select * from " + PublicVar.db_member + ".branfile "
                    + "where Branch_Code='" + branchCode + "' limit 1";
            try (ResultSet rs = mysqlConnect.executeQuery(sql)) {
                if (rs.next()) {
                    bean = mappingBean(rs);
                }
                rs.close();
            }
        } catch (SQLException e) {
            AppLogUtil.log(BranchFileControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(BranchFileControl.class);
        }

        return bean;
    }

    public BranchFileBean getDataMemberPoint(String branchCode) {
        BranchFileBean bean = null;

        try {
            mysqlConnect.open(BranchFileControl.class);
            String sql = "select * from " + PublicVar.db_member + ".branfile "
                    + "where Branch_Code='" + branchCode + "' "
                    + "and PointCode_Active='Y' "
                    + "and (PointCode_Type1 <> '' "
                    + "or PointCode_Type2 <> '' "
                    + "or PointCode_Type3 <> '' "
                    + "or PointCode_Type4 <> '' "
                    + "or PointCode_Type5 <> '') limit 1";
            try (ResultSet rs = mysqlConnect.executeQuery(sql)) {
                if (rs.next()) {
                    bean = mappingBean(rs);
                }
                rs.close();
            }
        } catch (SQLException e) {
            AppLogUtil.log(BranchFileControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(BranchFileControl.class);
        }

        return bean;
    }

    private static BranchFileBean mappingBean(ResultSet rs) throws SQLException {
        BranchFileBean bean = new BranchFileBean();
        bean.setBranch_Code(rs.getString("Branch_Code"));
        bean.setBranch_Name(ThaiUtil.ASCII2Unicode(rs.getString("Branch_Name")));
        bean.setBranch_Type_Code(rs.getString("Branch_Type_Code"));
        bean.setBranch_Area_Code(rs.getString("Branch_Area_Code"));
        bean.setBranch_Size_Code(rs.getString("Branch_Size_Code"));
        bean.setBranch_Plane_Code(rs.getString("Branch_Plane_Code"));
        bean.setBranch_Store_Code(rs.getString("Branch_Store_Code"));
        bean.setBranch_AddressNo(rs.getString("Branch_AddressNo"));
        bean.setBranch_AddressSubDistrict(rs.getString("Branch_AddressSubDistrict"));
        bean.setBranch_AddressDistrict(rs.getString("Branch_AddressDistrict"));
        bean.setBranch_Province(rs.getString("Branch_Province"));
        bean.setBranch_PostalCode(rs.getString("Branch_PostalCode"));
        bean.setBranch_Telephone(rs.getString("Branch_Telephone"));
        bean.setBranch_Fax(rs.getString("Branch_Fax"));
        bean.setBranch_Email(rs.getString("Branch_Email"));
        bean.setBranch_Manager(rs.getString("Branch_Manager"));
        bean.setBranch_ServiceArea(rs.getFloat("Branch_ServiceArea"));
        bean.setBranch_ConfectArea(rs.getFloat("Branch_ConfectArea"));
        bean.setBranch_KitchenArea(rs.getFloat("Branch_KitchenArea"));
        bean.setBranch_TotalArea(rs.getFloat("Branch_TotalArea"));
        bean.setBranch_Rent(rs.getFloat("Branch_Rent"));
        bean.setBranch_ServiceCharge(rs.getFloat("Branch_ServiceCharge"));
        bean.setBranch_FlagRent(rs.getString("Branch_FlagRent"));
        bean.setBranch_GP(rs.getFloat("Branch_GP"));
        bean.setBranch_FlagGP(rs.getString("Branch_FlagGP"));
        bean.setBranch_Remark(ThaiUtil.ASCII2Unicode(rs.getString("Branch_Remark")));
        bean.setBranch_Group_Code(rs.getString("Branch_Group_Code"));
        bean.setPointCode_Active(rs.getString("PointCode_Active"));
        bean.setPointCode_Type1(rs.getString("PointCode_Type1"));
        bean.setPointCode_Type2(rs.getString("PointCode_Type2"));
        bean.setPointCode_Type3(rs.getString("PointCode_Type3"));
        bean.setPointCode_Type4(rs.getString("PointCode_Type4"));
        bean.setPointCode_Type5(rs.getString("PointCode_Type5"));

        return bean;
    }
}
