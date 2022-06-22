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
public class PIngredientBean {
    
    private String pstock;
    private String pactive;
    private String PingCode;
    private double PBPack;
    private double PingQty;
    
}
