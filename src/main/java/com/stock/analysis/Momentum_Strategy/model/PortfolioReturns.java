package com.stock.analysis.Momentum_Strategy.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
@Entity
@Table(name="portfolio_returns_calculation")
@Getter
@Setter
public class PortfolioReturns {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String strategyType;
    private String stockName;
    private Date monthStartDate;
    private float startingPrice;
    private Date monthEndDate;
    private float endingPrice;
    private int numberOfStocks;
    private float investedAmount;
    private float actualAmount;
    private float percentageReturn;
    private float flatAmountAllocated;


}
