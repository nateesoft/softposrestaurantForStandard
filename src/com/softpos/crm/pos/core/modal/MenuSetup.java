package com.softpos.crm.pos.core.modal;

import com.softpos.pos.core.model.ProductBean;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
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
    
    
    public void addProduct(ProductBean product){
        productList.add(product);
    }
}
