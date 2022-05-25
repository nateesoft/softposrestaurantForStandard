package com.softpos.pos.core.model;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class STCardBean {

    private Date S_Date;
    private String S_No;
    private String S_SubNo;
    private int S_Que = 0;
    private String S_PCode;
    private String S_Stk;
    private double S_In = 0.000;
    private double S_Out = 0.000;
    private double S_InCost = 0.00;
    private double S_OutCost = 0.00;
    private double S_ACost = 0.00;
    private String S_Rem;
    private String S_User;
    private Date S_EntryDate;
    private String S_EntryTime;
    private String S_Link;
}
