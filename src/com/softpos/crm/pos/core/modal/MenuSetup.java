package com.softpos.crm.pos.core.modal;

import com.softpos.pos.core.model.ProductBean;
import java.util.ArrayList;

public class MenuSetup {
    
    private String Code_ID;
    private String Code_Type;
    private String PCode;
    private String ShortName;
    private String PPathName;
    private ArrayList<ProductBean> productList = new ArrayList<>();
    
    public MenuSetup(){}

    public MenuSetup(String Code_ID, String Code_Type, String PCode, String ShortName, String PPathName) {
        this.Code_ID = Code_ID;
        this.Code_Type = Code_Type;
        this.PCode = PCode;
        this.ShortName = ShortName;
        this.PPathName = PPathName;
    }

    public String getCode_ID() {
        return Code_ID;
    }

    public void setCode_ID(String Code_ID) {
        this.Code_ID = Code_ID;
    }

    public String getCode_Type() {
        return Code_Type;
    }

    public void setCode_Type(String Code_Type) {
        this.Code_Type = Code_Type;
    }

    public String getPCode() {
        return PCode;
    }

    public void setPCode(String PCode) {
        this.PCode = PCode;
    }

    public String getShortName() {
        return ShortName;
    }

    public void setShortName(String ShortName) {
        this.ShortName = ShortName;
    }

    public String getPPathName() {
        return PPathName;
    }

    public void setPPathName(String PPathName) {
        this.PPathName = PPathName;
    }

    public ArrayList<ProductBean> getProductList() {
        return productList;
    }

    public void setProductList(ArrayList<ProductBean> productList) {
        this.productList = productList;
    }
    
    
    public void addProduct(ProductBean product){
        productList.add(product);
    }
}
