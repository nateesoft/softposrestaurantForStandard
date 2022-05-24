package com.softpos.pos.core.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TempCuponBean {

    private String R_Index;
    private String R_Table;
    private String Terminal;
    private String Cashier;
    private String Time;
    private String CuCode;
    private int CuQuan;
    private double CuAmt;
    private double CuTotal;
    private double CuDisc;
    private double CuRedule;
    private double CuPayment;
    private String CuTextCode;
    private String CuTextComment;
}
