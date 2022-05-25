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
public class PointTypeBean {

    private String Point_TypeCode;
    private String Point_TypeDateService;
    private Date Point_StartDateService;
    private Date Point_FinishDateService;
    private String Point_StartTimeService1;
    private String Point_FinishTimeService1;
    private float BahtRatePerPoint1;
    private float point1;
    private String Point_StartTimeService2;
    private String Point_FinishTimeService2;
    private float BahtRatePerPoint2;
    private float point2;
    private String Point_StartTimeService3;
    private String Point_FinishTimeService3;
    private float BahtRatePerPoint3;
    private float point3;
    private String Point_TypeName;
}
