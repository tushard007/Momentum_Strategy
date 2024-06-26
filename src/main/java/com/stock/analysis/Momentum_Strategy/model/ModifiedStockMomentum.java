package com.stock.analysis.Momentum_Strategy.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Entity
@Table(name="modified_stock_momentum")
@Getter
@Setter
public class ModifiedStockMomentum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String stockName;
    private Date endDate;
    private int totalRank;
    @Transient
    private boolean availableThreeMonth;
    @Transient
    private boolean availableSixMonth;
    @Transient
    private boolean availableNineMonth;
    @Transient
    private boolean availableTwelveMonth;
    private float threeMonthReturn;
    private float sixMonthReturn;
    private float nineMonthReturn;
    private float twelveMonthReturn;
    private int threeMonthRank;
    private int sixMonthRank;
    private int nineMonthRank;
    private int twelveMonthRank;
}
