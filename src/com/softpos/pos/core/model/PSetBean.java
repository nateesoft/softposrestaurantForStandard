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
public class PSetBean {
    
    private String pcode;
    private String psubcode;
    private Double psubQTY;
}
