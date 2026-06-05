package com.softpos.pos.core.controller.test;

import com.softpos.pos.core.controller.BalanceControl;

/**
 *
 * @author nateelive
 */
public class TestBalanceControl {
    
    public static void main(String[] args) {
        BalanceControl control = new BalanceControl();
        control.getLastIndex("206");
        control.getCurrentBalance();
        control.getAllBalance("206");
        control.getAllBalanceSum("206");
        control.getAllBalanceNoVoid("206");
        control.getAllBalanceNoVoidSum("206");
        control.getBalanceIndex("206/001");
        control.getAllBalancePromotion("206");
        control.getIndexBalance("206");
        control.getProduct("234234234", "206/001");
        control.getBalanceIndex("206", "206/001");
        control.getBalanceIndexVoid("206");
        control.GetDiscount("206");
        control.getBalanceForPDA();
        control.loadTableBalance("206");
        control.getBalanceByRLinkIndex("206/001");
    }
}
