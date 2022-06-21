package com.softpos.pos.core.model;

import com.softpos.pos.core.controller.PosControl;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class POSHWSetup {

    private String Terminal;
    private String OnAct = "N";
    private String MacNo;
    private double ReceNo1 = 0;
    private String SaleType = "E";
    private String TStock;
    private String TSone;
    private String Heading1;
    private String Heading2;
    private String Heading3;
    private String Heading4;
    private String Footting1;
    private String Footting2;
    private String Footting3;
    private String DRPort = "NONE";
    private String DRType = "1";
    private String DRCOM;
    private String DISPort = "NONE";
    private String DISType = "1";
    private String DISCOM;
    private String PRNPort = "NONE";
    private String PRNTYPE = "1";
    private String PRNCOM;
    private String PRNThaiLevel = "Y";
    private String KIC1Port = "NONE";
    private String KIC1Type = "1";
    private String KIC1Com;
    private String KIC1ThaiLevel = "Y";
    private String KIC2Port = "NONE";
    private String KIC2Type = "1";
    private String KIC2Com;
    private String KIC2ThaiLevel = "Y";
    private String KIC3Port = "NONE";
    private String KIC3Type = "1";
    private String KIC3Com;
    private String KIC3ThaiLevel = "Y";
    private String KIC4Port = "NONE";
    private String KIC4Type = "1";
    private String KIC4Com;
    private String KIC4ThaiLevel = "Y";
    private String EJounal = "N";
    private String EJDailyPath;
    private String EJBackupPath;
    private int PrnRate = 0;
    private int DrRate = 0;
    private int DisRate = 0;
    private String EDCPort = "NONE";
    private String SMPBank;
    private String MenuItemList = "N";
    private String UseFloorPlan = "N";
    private String TakeOrderChk = "N";
    private String RFIDPort = "NONE";
    
    public static POSHWSetup Bean(String MacNo){
        return PosControl.getData(MacNo);
    }
}
