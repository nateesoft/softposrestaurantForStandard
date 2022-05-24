package com.softpos.pos.core.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import com.softpos.crm.pos.core.modal.ButtonBean;

@Getter
@Setter
@ToString
public class PluButtonBean extends ButtonBean {
    protected String pcode;
    protected String pdesc;
    protected double pprice;
}
