package com.softpos.crm.pos.core.modal;

import java.util.Date;

/**
 *
 * @author nathee
 */
public class MTranBean {

    private Date Service_Date;
    private String Member_Code;
    private String Branch_Code;
    private String Receipt_No;
    private String Sale_Type;
    private double GrossAmount;
    private double DiscountAmount;
    private double NetAmount;
    private String Mechine_Code;
    private String Employee_Code;
    private String Service_Time;
    private double Score;
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

    public String getSale_Type() {
        return Sale_Type;
    }

    public void setSale_Type(String Sale_Type) {
        this.Sale_Type = Sale_Type;
    }

    public double getGrossAmount() {
        return GrossAmount;
    }

    public void setGrossAmount(double GrossAmount) {
        this.GrossAmount = GrossAmount;
    }

    public double getDiscountAmount() {
        return DiscountAmount;
    }

    public void setDiscountAmount(double DiscountAmount) {
        this.DiscountAmount = DiscountAmount;
    }

    public double getNetAmount() {
        return NetAmount;
    }

    public void setNetAmount(double NetAmount) {
        this.NetAmount = NetAmount;
    }

    public String getMechine_Code() {
        return Mechine_Code;
    }

    public void setMechine_Code(String Mechine_Code) {
        this.Mechine_Code = Mechine_Code;
    }

    public String getEmployee_Code() {
        return Employee_Code;
    }

    public void setEmployee_Code(String Employee_Code) {
        this.Employee_Code = Employee_Code;
    }

    public String getService_Time() {
        return Service_Time;
    }

    public void setService_Time(String Service_Time) {
        this.Service_Time = Service_Time;
    }

    public double getScore() {
        return Score;
    }

    public void setScore(double Score) {
        this.Score = Score;
    }

    public String getTranferFlag() {
        return TranferFlag;
    }

    public void setTranferFlag(String TranferFlag) {
        this.TranferFlag = TranferFlag;
    }

}
