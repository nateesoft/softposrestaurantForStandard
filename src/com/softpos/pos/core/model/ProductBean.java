package com.softpos.pos.core.model;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductBean {

    private String PCode;
    private String PFix = "F";
    private String PReferent;
    private String PAccNo;
    private String PGroup;
    private String PVender;
    private String PType = "1";
    private String PNormal = "C";
    private String PRemark;
    private String PDiscount = "Y";
    private String PService = "Y";
    private String PStatus = "P";
    private String PStock = "Y";
    private String PSet = "N";
    private String PVat = "V";
    private String PDesc;
    private String PUnit1;
    private int PPack1 = 0;
    private String PArea;
    private String PKic = "0";
    private double PPrice11 = 0.00;
    private double PPrice12 = 0.00;
    private double PPrice13 = 0.00;
    private double PPrice14 = 0.00;
    private double PPrice15 = 0.00;
    private String PPromotion1;
    private String PPromotion2;
    private String PPromotion3;
    private double PMax = 0.0000;
    private double PMin = 0.0000;
    private String PBUnit;
    private double PBPack = 0.0000;
    private double PLCost = 0.0000;
    private double PSCost = 0.0000;
    private double PACost = 0.0000;
    private String PLink1;
    private String PLink2;
    private Date PLastUpdate;
    private String PLastTime = "00:00:00";
    private String PUserUpdate;
    private Date PLastSale;
    private String PBarCode;
    private String PActive = "Y";
    private String PSPVat = "N";
    private double PSPVatAmt = 0.00;
    private String POSStk = "0";
    private String MSStk;
    private double PTimeCharge = 0.00;
    private String POrder = "0";
    private String PFoodType = "0";
    private Date PChkDate;
    private int PPackOld = 0;
    private String PDesc2;
    private String PselectItem;
    private double PselectNum;
    private String PShowOption = "N";
    private String PChkTime = "N";
    private double PTime = 0;
    private String PCashCardPay = "N";
    private String PSelectShow = "N";
    private String PEDesc = "";
    public static final String FOOD = "1";
    public static final String DRINK = "2";
    public static final String PRODUCT = "3";
    
    public String PPathName;

}
