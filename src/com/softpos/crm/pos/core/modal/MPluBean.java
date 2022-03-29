package com.softpos.crm.pos.core.modal;

import java.util.Date;

/**
 *
 * @author nathee
 */
public class MPluBean {

    private Date Service_Date;
    private String Member_Code;
    private String Branch_Code;
    private String Receipt_No;
    private String PLU_Group;
    private String Sale_Type;
    private String PLU_GroupName;
    private String PLU_Code;
    private String PLU_Name;
    private double PLU_Amount;
    private double PLU_Quantity;
    private double PLU_Price;
    private String TranferFlag;

    public Date getService_Date() {
        return Service_Date;
    }

    public void setService_Date(Date Service_Date) {
        this.Service_Date = Service_Date;
    }

    public String getMember_Code() {
        return Member_Code;
    }

    public void setMember_Code(String Member_Code) {
        this.Member_Code = Member_Code;
    }

    public String getBranch_Code() {
        return Branch_Code;
    }

    public void setBranch_Code(String Branch_Code) {
        this.Branch_Code = Branch_Code;
    }

    public String getReceipt_No() {
        return Receipt_No;
    }

    public void setReceipt_No(String Receipt_No) {
        this.Receipt_No = Receipt_No;
    }

    public String getPLU_Group() {
        return PLU_Group;
    }

    public void setPLU_Group(String PLU_Group) {
        this.PLU_Group = PLU_Group;
    }

    public String getSale_Type() {
        return Sale_Type;
    }

    public void setSale_Type(String Sale_Type) {
        this.Sale_Type = Sale_Type;
    }

    public String getPLU_GroupName() {
        return PLU_GroupName;
    }

    public void setPLU_GroupName(String PLU_GroupName) {
        this.PLU_GroupName = PLU_GroupName;
    }

    public String getPLU_Code() {
        return PLU_Code;
    }

    public void setPLU_Code(String PLU_Code) {
        this.PLU_Code = PLU_Code;
    }

    public String getPLU_Name() {
        return PLU_Name;
    }

    public void setPLU_Name(String PLU_Name) {
        this.PLU_Name = PLU_Name;
    }

    public double getPLU_Amount() {
        return PLU_Amount;
    }

    public void setPLU_Amount(double PLU_Amount) {
        this.PLU_Amount = PLU_Amount;
    }

    public double getPLU_Quantity() {
        return PLU_Quantity;
    }

    public void setPLU_Quantity(double PLU_Quantity) {
        this.PLU_Quantity = PLU_Quantity;
    }

    public double getPLU_Price() {
        return PLU_Price;
    }

    public void setPLU_Price(double PLU_Price) {
        this.PLU_Price = PLU_Price;
    }

    public String getTranferFlag() {
        return TranferFlag;
    }

    public void setTranferFlag(String TranferFlag) {
        this.TranferFlag = TranferFlag;
    }

}
