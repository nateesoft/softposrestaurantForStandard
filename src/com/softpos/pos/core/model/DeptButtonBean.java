package com.softpos.pos.core.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import com.softpos.crm.pos.core.modal.ButtonBean;

@Getter
@Setter
@ToString
public class DeptButtonBean extends ButtonBean {
    protected String groupcode;
    protected String groupname;
}
