package com.softpos.pos.core.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TPromotion2 {

    private String TCode;
    private String PCode;
    private String ProCode;
    private double PQuan = 0.00;
    private double PPrice = 0.00;
    private String MacNo;
}
