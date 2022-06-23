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
public class MgrButtonSetupBean {
    private String autu_pcode;
    private String auto_pdesc;
    private String PCode;
    private String Check_before;
    private String PDesc;
    
    private String sd_pcode;
    private String sd_pdesc;
    private String ex_pcode;
    private String ex_pdesc;
}
