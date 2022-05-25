package com.softpos.pos.core.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TPromotionBean {

    private String R_Index;
    private String R_RefNo;
    private String Terminal;
    private String Cashier;
    private String PrCode;
    private String PrType;
    private String PCode;
    private double PDisc = 0.00;
    private double PDiscBath = 0.00;
    private double PPrice = 0.00;
    private double PQty = 0.00;
    private double PrTotalAmt = 0.00;
    private double PrAmt = 0.00;
    private String Flage = "-";
}
