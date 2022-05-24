package com.softpos.pos.core.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CustomerBean {

    private String sp_code;
    private String sp_Desc;
    private String sp_Addr1;
    private String sp_Addr2;
    private String sp_zip;
    private String tel;
    private String fax;
    private String Contack;
    private String Remark;
    private String Remark2;
    private String Taxid;
    private String CustBranch;
}
