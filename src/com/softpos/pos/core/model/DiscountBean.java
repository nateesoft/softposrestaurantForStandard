package com.softpos.pos.core.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DiscountBean {

    private double festDiscount = 0;
    private double empDiscount = 0;
    private double memDiscount = 0;
    private double trainDiscount = 0;
    private double cuponDiscount = 0;
    private double bahtDiscount = 0;
    private double cuponSpecialDiscount = 0;
    
    private String strFestDiscount = "00/00/00";
    private String strEmpDiscount = "00/00/00";
    private String strMemDiscount = "00/00/00";
    private String strTrainDiscount = "00/00/00";
    private String strCuponDiscount = "00/00/00";
    
    private CuponBean cuponBean = new CuponBean();
    
    public double getTotalDiscount() {
        return festDiscount + empDiscount + memDiscount + trainDiscount + cuponDiscount + bahtDiscount + cuponSpecialDiscount;
    }
}
