package com.softpos.pos.core.model;

import java.util.ArrayList;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CuponBean {

    private String CuCode;//
    private int setCuQTY;
    private String CuName;//
    private Date CuBegin;//
    private Date CuEnd;//
    private String CuStrDay;//
    private String CuType = "C";//
    private String CuADisc = "0";//
    private double CuADiscBath = 0.00;//
    private String CuBDisc = "0";//
    private double CuBDiscBath = 0.00;//
    private String CuPLUList;//
    private String CuPLU1;//
    private String CuPLU2;//
    private String CuPLU3;//
    private String CuPLU4;//
    private String CuPLU5;//
    private String CuPLU6;//
    private String CuPLU7;//
    private String CuPLU8;//
    private String CuPLU9;//
    private String CuPLU10;//
    private double CuDisc = 0.00;//
    private double CuDiscBath = 0.00;//
    private String ChkMember = "N";//
    private double CuDisc2 = 0.00;//
    private double CuDiscBath2 = 0.00;//
    private double CuDisc3 = 0.00;//
    private double CuDiscBath3 = 0.00;//
    private double CuDisc1;//
    private double CuDiscBath1;//
    private String CuSelectDisc;//
    private double CuEDiscount;//
    private double CuEPayment;//

    private ArrayList<CuponlistBean> listBean = new ArrayList<>();
}
