package com.softpos.crm.pos.core.modal;

import java.util.Date;
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
public class MPluBean {

    private Date Service_Date;
    private String Member_Code;
    private String Branch_Code;
    private String Receipt_No;
    private String PLU_Group;
    private String Sale_Type;
    private String PLU_GroupName;
    private String PLU_Code;
    private String PLU_Name;
    private double PLU_Amount;
    private double PLU_Quantity;
    private double PLU_Price;
    private String TranferFlag;
}
