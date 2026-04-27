package com.softpos.pos.core.model;

public class FloorPlanBean {

    private String codeId;
    private int index;
    private String tableNo;
    private String loginTime;
    private int customer;
    private String status;
    private boolean IsActive;
    private String zone;
    private double TAmount;
    private String PrintChkBill;
    private String rTime;
    private int item;

    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getTableNo() {
        return tableNo;
    }

    public void setTableNo(String tableNo) {
        this.tableNo = tableNo;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public int getCustomer() {
        return customer;
    }

    public void setCustomer(int customer) {
        this.customer = customer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isIsActive() {
        return IsActive;
    }

    public void setIsActive(boolean IsActive) {
        this.IsActive = IsActive;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public double getTAmount() {
        return TAmount;
    }

    public void setTAmount(double TAmount) {
        this.TAmount = TAmount;
    }

    public String getPrintChkBill() {
        return PrintChkBill;
    }

    public void setPrintChkBill(String PrintChkBill) {
        this.PrintChkBill = PrintChkBill;
    }

    public String getrTime() {
        return rTime;
    }

    public void setrTime(String rTime) {
        this.rTime = rTime;
    }

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }
    
    
}
