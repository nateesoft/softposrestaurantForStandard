/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softpos.bean;

/**
 *
 * @author Administrator
 */

public class S_TranT_SaleBean {

    private String R_Refno = "";
    private String R_Date = "";
    private String R_Time = "";
    private String Macno = "";
    private String Cashier = "";
    private String R_EMP = "";
    private String R_PluCode = "";
    private double R_Total = 0;
    private double R_Nettotal = 0;
    private String R_Refund = "";
    private double Discount = 0;

    public String getR_Refno() {
        return R_Refno;
    }

    public void setR_Refno(String R_Refno) {
        this.R_Refno = R_Refno;
    }

    public String getR_Date() {
        return R_Date;
    }

    public void setR_Date(String R_Date) {
        this.R_Date = R_Date;
    }

    public String getR_Time() {
        return R_Time;
    }

    public void setR_Time(String R_Time) {
        this.R_Time = R_Time;
    }

    public String getMacno() {
        return Macno;
    }

    public void setMacno(String Macno) {
        this.Macno = Macno;
    }

    public String getCashier() {
        return Cashier;
    }

    public void setCashier(String Cashier) {
        this.Cashier = Cashier;
    }

    public String getR_EMP() {
        return R_EMP;
    }

    public void setR_EMP(String R_EMP) {
        this.R_EMP = R_EMP;
    }

    public String getR_PluCode() {
        return R_PluCode;
    }

    public void setR_PluCode(String R_PluCode) {
        this.R_PluCode = R_PluCode;
    }

    public double getR_Total() {
        return R_Total;
    }

    public void setR_Total(double R_Total) {
        this.R_Total = R_Total;
    }

    public double getR_Nettotal() {
        return R_Nettotal;
    }

    public void setR_Nettotal(double R_Nettotal) {
        this.R_Nettotal = R_Nettotal;
    }

    public String getR_Refund() {
        return R_Refund;
    }

    public void setR_Refund(String R_Refund) {
        this.R_Refund = R_Refund;
    }

    public double getDiscount() {
        return Discount;
    }

    public void setDiscount(double Discount) {
        this.Discount = Discount;
    }

}
