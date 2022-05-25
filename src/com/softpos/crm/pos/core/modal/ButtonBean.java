package com.softpos.crm.pos.core.modal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ButtonBean {
    protected String buttonName;
    protected String buttonType;
    protected String shortDesc;
    protected String picture;
    protected String pcolor;
    protected boolean isInsert; // insert or delete
}
