package com.softpos.pos.core.controller;

public class CompanyConfig {

    public CompanyConfig() {
    }

    public boolean includeVat() {
        POSConfigSetup posconfigSetup = PosControl.getData();
        return posconfigSetup.getP_VatType().equals("I");
    }

    public boolean excludeVat() {
        POSConfigSetup posconfigSetup = PosControl.getData();
        return posconfigSetup.getP_VatType().equals("E");
    }
}
