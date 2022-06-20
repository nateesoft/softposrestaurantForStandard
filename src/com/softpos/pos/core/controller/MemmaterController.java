package com.softpos.pos.core.controller;

import com.softpos.crm.pos.core.modal.PublicVar;
import com.softpos.crm.pos.core.controller.BranchFileController;
import com.softpos.crm.pos.core.controller.MPluController;
import com.softpos.crm.pos.core.controller.MTranController;
import com.softpos.crm.pos.core.controller.PointTypeController;
import com.softpos.crm.pos.core.modal.BranchFileBean;
import com.softpos.crm.pos.core.modal.MPluBean;
import com.softpos.crm.pos.core.modal.MTranBean;
import com.softpos.crm.pos.core.modal.PointTypeBean;
import com.softpos.pos.core.model.BillNoBean;
import com.softpos.pos.core.model.MemberBean;
import com.softpos.pos.core.model.TSaleBean;
import database.MySQLConnect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import util.AppLogUtil;
import util.DateConvert;
import util.DateUtil;
import util.MSG;

/**
 *
 * @author nathee
 */
public class MemmaterController {
    
    
    public static MemberBean getMember(String MemberCode) {
        MemberBean bean = null;
        /**
         * * OPEN CONNECTION **
         */
        MySQLConnect mysql = new MySQLConnect();
        mysql.close();
        mysql.open();
        try {
            String sql = "select * from " + Value.db_member + ".memmaster where member_code='" + MemberCode + "' limit 1";
            Statement stmt = mysql.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                bean = new MemberBean();
                bean.setMember_Code(rs.getString("Member_Code"));
                bean.setMember_TypeCode(rs.getString("Member_TypeCode"));
                bean.setMember_BranchCode(rs.getString("Member_BranchCode"));
                bean.setMember_NameThai(ThaiUtil.ASCII2Unicode(rs.getString("Member_NameThai")));
                bean.setMember_NameEng(rs.getString("Member_NameEng"));
                bean.setMember_Gender(rs.getString("Member_Gender"));
                bean.setMember_Status(rs.getString("Member_Status"));
                bean.setMember_NationCode(rs.getString("Member_NationCode"));
                bean.setMember_OccupationCode(rs.getString("Member_OccupationCode"));
                bean.setMember_IncomeCode(rs.getString("Member_IncomeCode"));
                bean.setMember_EducationCode(rs.getString("Member_EducationCode"));
                bean.setMember_Company(rs.getString("Member_Company"));
                bean.setMember_AddressNo(rs.getString("Member_AddressNo"));
                bean.setMember_Building(rs.getString("Member_Building"));
                bean.setMember_AddressSoi(rs.getString("Member_AddressSoi"));
                bean.setMember_AddressStreet(rs.getString("Member_AddressStreet"));
                bean.setMember_AddressSubDistrict(rs.getString("Member_AddressSubDistrict"));
                bean.setMember_AddressDistrict(rs.getString("Member_AddressDistrict"));
                bean.setMember_Province(rs.getString("Member_Province"));
                bean.setMember_PostalCode(rs.getString("Member_PostalCode"));
                bean.setMember_HomeTel(ThaiUtil.ASCII2Unicode(rs.getString("Member_HomeTel")));
                bean.setMember_Fax(rs.getString("Member_Fax"));
                bean.setMember_Email(rs.getString("Member_Email"));
                bean.setMember_DiscountRate(rs.getString("Member_DiscountRate"));
                bean.setMember_SpouseName(rs.getString("Member_SpouseName"));
                bean.setMember_Food(rs.getString("Member_Food"));
                bean.setMember_TotalPurchase(rs.getFloat("Member_TotalPurchase"));
                bean.setMember_Remark1(rs.getString("Member_Remark1"));
                bean.setMember_Remark2(rs.getString("Member_Remark2"));
                bean.setMember_Mobile(rs.getString("Member_Mobile"));
                bean.setMember_ReceiveInformation(rs.getString("Member_ReceiveInformation"));
                bean.setMember_HobbySetCode(rs.getString("Member_HobbySetCode"));
                bean.setMember_ServiceCount(rs.getFloat("Member_ServiceCount"));
                bean.setMember_TotalScore(rs.getDouble("Member_TotalScore"));
                try {
                    bean.setMember_LastDateService(rs.getDate("Member_LastDateService"));
                } catch (SQLException e) {
                }

                try {
                    bean.setMember_PointExpiredDate(rs.getDate("Member_PointExpiredDate"));
                } catch (SQLException e) {
                }

                try {
                    bean.setMember_Brithday(rs.getDate("Member_Brithday"));
                } catch (SQLException e) {
                }

                try {
                    bean.setMember_AppliedDate(rs.getDate("Member_AppliedDate"));
                } catch (SQLException e) {
                }

                try {
                    bean.setMember_ExpiredDate(rs.getDate("Member_ExpiredDate"));
                } catch (SQLException e) {
                }

                try {
                    bean.setEmployee_CreateDate(rs.getDate("Employee_CreateDate"));
                } catch (SQLException e) {
                }

                try {
                    bean.setEmployee_ModifyDate(rs.getDate("Employee_ModifyDate"));
                } catch (SQLException e) {
                }

                bean.setMember_TotalScore(rs.getFloat("Member_TotalScore"));
                bean.setMember_TitleNameThai(ThaiUtil.ASCII2Unicode(rs.getString("Member_TitleNameThai")));
                bean.setMember_SurnameThai(ThaiUtil.ASCII2Unicode(rs.getString("Member_SurnameThai")));
                bean.setMember_CompanyAddressNo(rs.getString("Member_CompanyAddressNo"));
                bean.setMember_CompanyBuilding(rs.getString("Member_CompanyBuilding"));
                bean.setMember_CompanySoi(rs.getString("Member_CompanySoi"));
                bean.setMember_CompanyStreet(rs.getString("Member_CompanyStreet"));
                bean.setMember_CompanySubDistrict(rs.getString("Member_CompanySubDistrict"));
                bean.setMember_CompanyDistrict(rs.getString("Member_CompanyDistrict"));
                bean.setMember_CompanyProvince(rs.getString("Member_CompanyProvince"));
                bean.setMember_CompanyPostalCode(rs.getString("Member_CompanyPostalCode"));
                bean.setMember_CompanyTel(rs.getString("Member_CompanyTel"));
                bean.setMember_CompanyFax(rs.getString("Member_CompanyFax"));
                bean.setMember_Active(rs.getString("Member_Active"));
                bean.setMember_UsedTitle(rs.getString("Member_UsedTitle"));
                bean.setMember_MailTo(rs.getString("Member_MailTo"));
                bean.setMember_PrintLabel(rs.getString("Member_PrintLabel"));
                bean.setMember_UnknowBirth(rs.getString("Member_UnknowBirth"));
                bean.setEmployee_CodeCreate(rs.getString("Employee_CodeCreate"));
                bean.setEmployee_CreateTime(rs.getString("Employee_CreateTime"));
                bean.setEmployee_CodeModify(rs.getString("Employee_CodeModify"));
                bean.setEmployee_ModifyTime(rs.getString("Employee_ModifyTime"));
            } else {
                bean = null;
            }
            rs.close();
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            AppLogUtil.log(MemberBean.class, "error", e);
        } finally {
            mysql.close();
        }

        return bean;
    }

    void updateMemberPoint(String memberCode, Date lastDateService, double totalScore) {
        MySQLConnect mysql = new MySQLConnect();
        mysql.close();
        mysql.open();

        try {
            String sql = "update " + Value.db_member + ".memmaster "
                    + "set Member_LastDateService='" + DateUtil.getDateFormat(lastDateService, "yyyy-MM-dd") + "', "
                    + "Member_TotalScore=Member_TotalScore+'" + totalScore + "' "
                    + "where Member_Code='" + memberCode + "'";
            try (Statement stmt = mysql.getConnection().createStatement()) {
                stmt.executeUpdate(sql);
            }
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            AppLogUtil.log(MemmaterController.class, "error", e);
        } finally {
            mysql.close();
        }
    }

    public double paymentScoreExec(String memberCode, BillNoBean billBean, MemberBean member) {
        double pointTotal = computeMemberScore(billBean, member.getMember_PointExpiredDate());

        // update MTran vs Billno
        MTranController mTranCon = new MTranController();

        if (mTranCon.checkReceiptNoExist(billBean.getB_Refno())) {
            MTranBean mTranBean = new MTranBean();
            mTranBean.setService_Date(billBean.getB_OnDate());
            mTranBean.setMember_Code(memberCode);
            mTranBean.setBranch_Code(PublicVar.Branch_Code);
            mTranBean.setReceipt_No(billBean.getB_MacNo() + "/" + billBean.getB_Refno());
            mTranBean.setSale_Type(generateSaleType(billBean.getB_ETD()));
            mTranBean.setGrossAmount(billBean.getB_Total() + billBean.getB_ServiceAmt());
            mTranBean.setDiscountAmount(billBean.getB_MemDiscAmt());
            mTranBean.setNetAmount(billBean.getB_NetTotal());
            mTranBean.setMechine_Code(billBean.getB_MacNo());
            mTranBean.setEmployee_Code(Value.USERCODE);
            mTranBean.setService_Time(billBean.getB_Ontime());
            mTranBean.setScore(pointTotal);
            mTranBean.setTranferFlag("N");
            
            mTranCon.create(mTranBean);

            // Mplu VS T_Sale
            BillControl billControl = new BillControl();
            List<TSaleBean> listTSale = billControl.getAllTSale(billBean.getB_Refno());

            MPluController mPluCon = new MPluController();
            List<MPluBean> listMPlu = new ArrayList<>();
            for (TSaleBean tSaleBean : listTSale) {
                MPluBean pluBean = new MPluBean();
                pluBean.setService_Date(tSaleBean.getR_Date());
                pluBean.setMember_Code(mTranBean.getMember_Code());
                pluBean.setBranch_Code(PublicVar.Branch_Code);
                pluBean.setReceipt_No(mTranBean.getReceipt_No());
                pluBean.setPLU_Group(tSaleBean.getR_Group());
                pluBean.setSale_Type(tSaleBean.getR_ETD());
                pluBean.setPLU_GroupName("");
                pluBean.setPLU_Code(tSaleBean.getR_PluCode());
                pluBean.setPLU_Name(ThaiUtil.Unicode2ASCII(tSaleBean.getR_PName()));
                pluBean.setPLU_Amount(tSaleBean.getR_Total());
                pluBean.setPLU_Quantity(tSaleBean.getR_Quan());
                pluBean.setPLU_Price(tSaleBean.getR_Price());
                pluBean.setTranferFlag("N");

                listMPlu.add(pluBean);
            }
            mPluCon.create(listMPlu);

            // update memmaster
            updateMemberPoint(memberCode, mTranBean.getService_Date(), pointTotal);
        }

        return pointTotal;
    }

    private String generateSaleType(String etd) {
        if (null != etd) {
            switch (etd) {
                case "E":
                    return "1";
                case "T":
                    return "2";
                case "D":
                    return "3";
                case "P":
                    return "4";
                case "W":
                    return "5";
                default:
                    break;
            }
        }
        return "";
    }

    private double computeMemberScore(BillNoBean billBean, Date Member_PointExpiredDate) {
        DateConvert dc = new DateConvert();
        BranchFileBean branchFileBean = BranchFileController.getDataMemberPoint(PublicVar.Branch_Code);

        if (branchFileBean == null) {
            return 0.00;
        }

        PointTypeBean pointTypeBean = PointTypeController.getDataBranchPoint();
        int memberExpire = dc.getCheckExpireDate(Member_PointExpiredDate + "");
        if (memberExpire < 0) {
            return 0.00;
        }

        double pointTotal = 0;
        int timeActive1 = dc.getCheckExpireTime(pointTypeBean.getPoint_StartTimeService1(), pointTypeBean.getPoint_FinishTimeService1());
        int timeActive2 = dc.getCheckExpireTime(pointTypeBean.getPoint_StartTimeService2(), pointTypeBean.getPoint_FinishTimeService2());
        int timeActive3 = dc.getCheckExpireTime(pointTypeBean.getPoint_StartTimeService3(), pointTypeBean.getPoint_FinishTimeService3());

        if (timeActive1 >= 0) {
            pointTotal = Math.floor(billBean.getB_NetTotal() / pointTypeBean.getBahtRatePerPoint1()) * pointTypeBean.getPoint1();
        } else if (timeActive2 >= 0) {
            pointTotal = Math.floor(billBean.getB_NetTotal() / pointTypeBean.getBahtRatePerPoint2()) * pointTypeBean.getPoint2();
        } else if (timeActive3 >= 0) {
            pointTotal = Math.floor(billBean.getB_NetTotal() / pointTypeBean.getBahtRatePerPoint3()) * pointTypeBean.getPoint3();
        }

        return pointTotal;
    }

    public void updateScoreRefund(String memberCode, double scoreRemove) {
        MySQLConnect mysql = new MySQLConnect();
        mysql.close();
        mysql.open();
        try {
            String sql = "update " + Value.db_member + ".memmaster "
                    + "set Member_TotalScore=Member_TotalScore-" + (int) scoreRemove + " "
                    + "where Member_Code='" + memberCode + "'";
            try (Statement stmt = mysql.getConnection().createStatement()) {
                stmt.executeUpdate(sql);
            }
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            AppLogUtil.log(MemmaterController.class, "error", e);
        } finally {
            mysql.close();
        }
    }
}
