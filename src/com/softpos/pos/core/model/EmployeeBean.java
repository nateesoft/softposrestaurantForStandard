package com.softpos.pos.core.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author nateesun
 */
@Getter
@Setter
@ToString
public class EmployeeBean {

    private String code;
    private String name;
    private double salary;
    private String position;
    
    public EmployeeBean() {}

    public EmployeeBean(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
