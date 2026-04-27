package com.softpos.pos.core.model;

public class PIngredientBean {
    
    private String pstock;
    private String pactive;
    private String PingCode;
    private double PBPack;
    private double PingQty;

    public String getPstock() {
        return pstock;
    }

    public void setPstock(String pstock) {
        this.pstock = pstock;
    }

    public String getPactive() {
        return pactive;
    }

    public void setPactive(String pactive) {
        this.pactive = pactive;
    }

    public String getPingCode() {
        return PingCode;
    }

    public void setPingCode(String PingCode) {
        this.PingCode = PingCode;
    }

    public double getPBPack() {
        return PBPack;
    }

    public void setPBPack(double PBPack) {
        this.PBPack = PBPack;
    }

    public double getPingQty() {
        return PingQty;
    }

    public void setPingQty(double PingQty) {
        this.PingQty = PingQty;
    }
    
    
}
