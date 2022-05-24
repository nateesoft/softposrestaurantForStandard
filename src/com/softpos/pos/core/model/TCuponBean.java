package com.softpos.pos.core.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TCuponBean {

    private String R_Index;//
    private String R_Refno;//
    private String Terminal;//
    private String Cashier;//
    private String Time;//
    private String CuCode;//
    private int CuQuan = 0;//
    private double CuAmt;//
    private String Refund;//
    private String CuTextCode;//
    private String CuTextComment;//
}
