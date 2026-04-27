package com.softpos.pos.core.model;

import com.softpos.crm.pos.core.modal.ButtonBean;

public class DeptButtonBean extends ButtonBean {
    protected String groupcode;
    protected String groupname;

    public String getGroupcode() {
        return groupcode;
    }

    public void setGroupcode(String groupcode) {
        this.groupcode = groupcode;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }
    
    
}
