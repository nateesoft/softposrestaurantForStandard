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
public class MTranBean {

    private Date Service_Date;
    private String Member_Code;
    private String Branch_Code;
    private String Receipt_No;
    private String Sale_Type;
    private double GrossAmount;
    private double DiscountAmount;
    private double NetAmount;
    private String Mechine_Code;
    private String Employee_Code;
    private String Service_Time;
    private double Score;
    private String TranferFlag;
}
