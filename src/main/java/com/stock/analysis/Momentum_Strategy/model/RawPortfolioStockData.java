package com.stock.analysis.Momentum_Strategy.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class RawPortfolioStockData {
    private String stockName;
    private Date startMonthDate;
    private float startDatePrice;
    private Date endMonthDate;
    private float endDatePrice;

}
