package com.softpos.pos.core.controller;

import com.softpos.crm.pos.core.modal.MenuSetup;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CompanyMenu {
    
    private String headName;
    private final List<MenuSetup> menuSetupData = new ArrayList<>();
    public static final String TYPE_PRODUCT = "P";
    public static final String TYPE_GROUP = "S";

    public void addMenuSetup(MenuSetup menu){
        menuSetupData.add(menu);
    }
}
