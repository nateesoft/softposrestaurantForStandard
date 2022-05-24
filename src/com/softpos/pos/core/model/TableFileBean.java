package com.softpos.pos.core.model;

import java.util.ArrayList;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TableFileBean {

    private String Tcode;
    private String SoneCode="";
    private Date TLoginDate;
    private String MacNo;
    private String Cashier;
    private String TLoginTime;
    private String TCurTime;
    private int TCustomer = 1;
    private int TItem = 0;
    private double TAmount = 0.00;
    private String TOnAct = "N";
    private double Service = 0.00;
    private double ServiceAmt = 0.00;
    private String EmpDisc;
    private double EmpDiscAmt = 0.00;
    private String FastDisc;
    private double FastDiscAmt = 0.00;
    private String TrainDisc;
    private double TrainDiscAmt = 0.00;
    private String MemDisc;
    private double MemDiscAmt = 0.00;
    private String SubDisc;
    private double SubDiscAmt = 0.00;
    private double DiscBath = 0.00;
    private double ProDiscAmt = 0.00;
    private double SpaDiscAmt = 0.00;
    private double CuponDiscAmt = 0.00;
    private double ItemDiscAmt = 0.00;
    private String MemCode;
    private double MemCurAmt = 0.00;
    private String MemName="";
    private Date MemBegin;
    private Date MemEnd;
    private double Food = 0.00;
    private double Drink = 0.00;
    private double Product = 0.00;
    private double NetTotal = 0.00;
    private double PrintTotal = 0.00;
    private String PrintChkBill = "N";
    private int PrintCnt = 0;
    private String PrintTime1;
    private String PrintTime2;
    private String ChkBill = "N";
    private String ChkBillTime = "00:00:00";
    private String StkCode1="";
    private String StkCode2="";
    private int TDesk = 0;
    private String TUser;
    private String TPause = "N";
    private String TType = "0";
    private String TActive = "Y";
    private String TFinishTime;
    private String TTableIsOn = "N";
    private String TAutoClose = "Y";
    private ArrayList<BalanceBean> dataBalance = new ArrayList<BalanceBean>();
}