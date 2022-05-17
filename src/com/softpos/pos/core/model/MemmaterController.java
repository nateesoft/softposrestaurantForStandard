package com.softpos.pos.core.model;

import com.softpos.crm.pos.core.controller.BranchFileController;
import com.softpos.crm.pos.core.controller.MPluController;
import com.softpos.crm.pos.core.controller.MTranController;
import com.softpos.crm.pos.core.controller.PointTypeController;
import com.softpos.crm.pos.core.modal.BranchFileBean;
import com.softpos.crm.pos.core.modal.MPluBean;
import com.softpos.crm.pos.core.modal.MTranBean;
import com.softpos.crm.pos.core.modal.PointTypeBean;
import com.softpos.pos.core.controller.BillControl;
import com.softpos.pos.core.controller.PublicVar;
import com.softpos.pos.core.controller.ThaiUtil;
import com.softpos.pos.core.controller.Value;
import database.MySQLConnect;
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

    void updateMemberPoint(String memberCode, Date lastDateService, double totalScore) {
        MySQLConnect mysql = new MySQLConnect();
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
