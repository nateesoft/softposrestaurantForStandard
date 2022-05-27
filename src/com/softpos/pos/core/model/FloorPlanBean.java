package com.softpos.pos.core.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author nathee
 */

@Getter
@Setter
@ToString
public class FloorPlanBean {

    private String codeId;
    private int index;
    private String tableNo;
    private String loginTime;
    private int customer;
    private String status;
    private boolean IsActive;
    private String zone;
    private double TAmount;
    private String PrintChkBill;
    private String rTime;
    private int item;
}
