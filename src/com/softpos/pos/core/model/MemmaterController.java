package com.softpos.pos.core.model;

import com.softpos.crm.pos.core.controller.BranchFileController;
import com.softpos.crm.pos.core.controller.PointTypeController;
import com.softpos.crm.pos.core.modal.BranchFileBean;
import com.softpos.crm.pos.core.modal.PointTypeBean;
import com.softpos.main.program.PublicVar;
import util.DateConvert;

/**
 *
 * @author nathee
 */
public class MemmaterController {
    
    public void paymentScoreExec(String Member_Code, BillNoBean billBean, MemberBean member) {
        DateConvert dc = new DateConvert();
        BranchFileBean branchFileBean = BranchFileController.getDataMemberPoint(PublicVar.Branch_Code);
        
        if(branchFileBean == null){
            return;
        }

        PointTypeBean pointTypeBean = PointTypeController.getDataBranchPoint();
        int memberExpire = dc.getCheckExpireDate(member.getMember_PointExpiredDate() + "");
        if(memberExpire < 0){
            return;
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
    }
}
