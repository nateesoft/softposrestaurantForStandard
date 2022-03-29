package com.softpos.pos.core.controller;

import java.util.ArrayList;
import java.util.List;

public class CompanyMenu {
    
    private String headName;
    private final List<MenuSetup> menuSetupData;
    public static final String TYPE_PRODUCT = "P";
    public static final String TYPE_GROUP = "S";
    
    public CompanyMenu(){
        menuSetupData = new ArrayList<>();
    }
    
    public void addMenuSetup(MenuSetup menu){
        menuSetupData.add(menu);
    }
    
    public List<MenuSetup> getAllMenuSetup(){
        return menuSetupData;
    }
    
    public MenuSetup getMenuSetup(int index){
        return menuSetupData.get(index);
    }

    public String getHeadName() {
        return headName;
    }

    public void setHeadName(String headName) {
        this.headName = headName;
    }
    
}
