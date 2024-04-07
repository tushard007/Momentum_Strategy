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
    private boolean availableThreeMonth;
    private boolean availableSixMonth;
    private boolean availableNineMonth;
    private boolean availableTwelveMonth;
}
