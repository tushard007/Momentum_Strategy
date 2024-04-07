package com.stock.analysis.Momentum_Strategy.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class StockMomentum {
    private String stockName;
    private Date endDate;
    private int totalRank;
    private String strategyName;
}
