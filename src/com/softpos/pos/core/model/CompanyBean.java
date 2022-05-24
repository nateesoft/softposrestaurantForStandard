package com.softpos.pos.core.model;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CompanyBean {

    private String Code;
    private String Name;
    private String Address;
    private String Subprovince;
    private String Province;
    private String City;
    private String POST;
    private String Tel;
    private String Fax;
    private String emailaddress;
    private String Tax;
    private Date Accterm;
    private String PosStock = "XX";
    private String RecCost = "1";
    private String TriCost = "1";
    private String TroCost = "1";
    private String LosCost = "1";
    private String FreCost = "1";
    private String Tri_Cost = "1";
    private String AdjCost = "1";
    private String RecAvgCost = "N";
    private String TriAvgCost = "N";
    private String TroAvgCost = "N";
    private String LosAvgCost = "N";
    private String FreAvgCost = "N";
    private String Tri_AvgCost = "N";
    private String AdjAvgCost = "N";
    private String UsePSetCost = "N";
    private String UsePIngredentCost = "N";
    private String Head1;
    private String Head2;
    private String Head3;
    private String Head4;
    private String pdahead1 = "pdahead1";
    private String pdahead2 = "pdahead2";
    private String DisplayTextInfo;
    private String FloorTab1 = "Zone 1";
    private String FloorTab2 = "Zone 2";
    private String FloorTab3 = "Zone 3";
    private String FloorTab4 = "Zone 4";
    private String FloorTab5 = "Zone 5";
    private String FloorTab6 = "Zone 6";
    private String FloorTab7 = "Zone 7";
    private String pdahead3 = "pdahead3";
    private String pdahead4 = "pdahead4";
}
