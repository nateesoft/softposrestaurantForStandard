package com.softpos.pos.core.model;

import com.softpos.pos.core.controller.PosControl;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class POSConfigSetup {

    private String P_Terminal;
    private double P_Vat = 0.00;
    private double P_Service = 0.00;
    private String P_ServiceType = "N";
    private String P_VatPrn = "Y";
    private String P_VatType;
    private int P_BillCopy = 0;
    private String P_BillCopyOne = "N";
    private String P_DefaultSaleType = "E";
    private String P_EmpUse = "Y";
    private String P_CodePrn = "N";
    private String P_CheckBillBeforCash = "N";
    private String P_PrintDetailOnRecp = "Y";
    private String P_PrintSum = "N";
    private String P_PrintRecpMessage;
    private String P_MemDisc = "00/00/00";
    private String P_MemDiscChk = "N/N/N";
    private String P_MemDiscGet = "Y";
    private String P_MemDiscMax = "00/00/00";
    private String P_FastDisc = "00/00/00";
    private String P_FastDiscChk = "N/N/N";
    private String P_FastDiscGet = "Y";
    private String P_FastDiscMax = "00/00/00";
    private String P_EmpDisc = "00/00/00";
    private String P_EmpDiscChk = "N/N/N";
    private String P_EmpDiscGet = "Y";
    private String P_EmpDiscMax = "00/00/00";
    private String P_TrainDisc = "00/00/00";
    private String P_TrainDiscChk = "N/N/N";
    private String P_TrainDiscGet = "Y";
    private String P_TrainDiscMax = "00/00/00";
    private String P_SubDisc = "00/00/00";
    private String P_SubDiscChk = "N/N/N";
    private String P_SubDiscGet = "Y";
    private String P_SubDiscMax = "00/00/00";
    private String P_DiscBathChk = "N";
    private int P_DiscBathMax = 0;
    private String P_PromotionChk = "Y/Y/Y";
    private String P_SpacialChk = "Y/Y/Y";
    private String P_DiscRound = "F";
    private String P_ServiceRound = "F";
    private String P_SerChkBySaleType = "Y/Y/Y/Y/Y";
    private String P_DiscChkBySaleType = "Y/Y/Y/Y/Y";
    private String P_MemberSystem = "N";
    private String KicSum = "N";
    private String KicCopy = "1";
    private String P_PrintByItemType = "N";
    private String P_PrintTotalSumItemType = "N";
    private String P_PrintTotalSumNormalType = "N";
    private String P_PrintTotalSumGroup = "N";
    private String WTime = "00:04";
    private String LTime = "00:08";
    private String P_PrintProductValue = "N";
    private int P_LimitTime = 0;
    private int P_RefreshTime = 1;
    private String P_SaleDecimal = "N";
    private String P_PayBahtRound = "F";
    private String P_PrintAdjust = "N";
    private String P_PrintNetAdj = "N";
    private String P_ShowKicQue = "N";
    private int P_DefAddTime = 0;
    private String P_BillLang = "T";
    
    public static POSConfigSetup Bean(){
        return PosControl.getData();
    }
}
