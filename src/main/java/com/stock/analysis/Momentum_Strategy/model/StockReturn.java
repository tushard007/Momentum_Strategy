package com.stock.analysis.Momentum_Strategy.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Entity
@Table(name="Stock_Return")
@Getter
@Setter
public class StockReturn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String stockName;
    private Date startDate;
    private float startDatePrice;
    private Date endDate;
    private float endDatePrice;
    private int monthTimePeriod;
    private float percentageReturn;
    private int rank;
}
