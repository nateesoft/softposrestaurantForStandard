package com.softpos.pos.core.model;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberBean {

    private String Member_Code;
    private String Member_TypeCode = "00";
    private String Member_BranchCode = "000";
    private String Member_NameThai;
    private String Member_NameEng;
    private String Member_Gender = "M";
    private String Member_Status = "S";
    private String Member_NationCode = "00";
    private String Member_OccupationCode = "0";
    private String Member_IncomeCode = "0";
    private String Member_EducationCode = "000";
    private String Member_Company;
    private String Member_AddressNo;
    private String Member_Building;
    private String Member_AddressSoi;
    private String Member_AddressStreet;
    private String Member_AddressSubDistrict;
    private String Member_AddressDistrict;
    private String Member_Province;
    private String Member_PostalCode;
    private String Member_HomeTel;
    private String Member_Fax;
    private String Member_Email;
    private Date Member_Brithday;
    private Date Member_AppliedDate;
    private Date Member_ExpiredDate;
    private String Member_DiscountRate = "00/00/00";
    private String Member_SpouseName;
    private String Member_Food;
    private double Member_TotalPurchase = 0.00;
    private String Member_Remark1;
    private String Member_Remark2;
    private String Member_Mobile;
    private String Member_ReceiveInformation = "M";
    private String Member_HobbySetCode;
    private Date Member_LastDateService;
    private double Member_ServiceCount = 0;
    private Date Member_PointExpiredDate;
    private double Member_TotalScore = 0;
    private String Member_TitleNameThai;
    private String Member_SurnameThai;
    private String Member_CompanyAddressNo;
    private String Member_CompanyBuilding;
    private String Member_CompanySoi;
    private String Member_CompanyStreet;
    private String Member_CompanySubDistrict;
    private String Member_CompanyDistrict;
    private String Member_CompanyProvince;
    private String Member_CompanyPostalCode;
    private String Member_CompanyTel;
    private String Member_CompanyFax;
    private String Member_Active = "Y";
    private String Member_UsedTitle = "Y";
    private String Member_MailTo = "0";
    private String Member_PrintLabel = "N";
    private String Member_UnknowBirth = "N";
    private String Employee_CodeCreate = "000000";
    private Date Employee_CreateDate;
    private String Employee_CreateTime = "00:00:00";
    private String Employee_CodeModify = "000000";
    private Date Employee_ModifyDate;
    private String Employee_ModifyTime = "00:00:00";
    private String CardPro_Code;
    private String Member_SMSBirthDayCheck = "Y";
    private String Member_SMSPromotionCheck = "Y";
    private String Member_SMSActivityCheck = "Y";
    private String Member_SMSNewsCheck = "Y";
    private int Member_SMSBranchCheck = 3;
    private String Member_BranchNearHourse = "000";
    private String Member_BranchNearOffice = "000";
    private String Member_BranchReques = "000";
    private String Member_PriceChk = "1";
    private int Member_CurPoint = 0;
}
