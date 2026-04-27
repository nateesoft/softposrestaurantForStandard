package com.softpos.pos.core.controller;

import com.softpos.crm.pos.core.modal.MenuSetup;
import java.util.ArrayList;
import java.util.List;

public class CompanyMenu {
    
    private String headName;
    private final List<MenuSetup> menuSetupData = new ArrayList<>();
    public static final String TYPE_PRODUCT = "P";
    public static final String TYPE_GROUP = "S";

    public String getHeadName() {
        return headName;
    }

    public void setHeadName(String headName) {
        this.headName = headName;
    }
    
    

    public void addMenuSetup(MenuSetup menu){
        menuSetupData.add(menu);
    }
}
