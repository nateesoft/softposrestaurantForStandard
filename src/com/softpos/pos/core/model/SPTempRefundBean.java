/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class SPTempRefundBean {

    private String R_PluCode;
    private String R_ETD;
    private Double R_Quan;
    private Double R_Price;
}
