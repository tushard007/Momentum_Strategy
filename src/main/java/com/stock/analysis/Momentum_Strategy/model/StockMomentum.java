package com.stock.analysis.Momentum_Strategy.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class StockMomentum {
    private String stockName;
    private Date startDate;
    private float startDatePrice;
    private Date endDate;
    private float endDatePrice;
    private String timePeriod;
    private float percentageReturn;
}
