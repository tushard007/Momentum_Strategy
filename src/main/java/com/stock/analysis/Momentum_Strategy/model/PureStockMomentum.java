package com.stock.analysis.Momentum_Strategy.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
@Entity
@Table(name="pure_stock_momentum")
@Getter
@Setter
public class PureStockMomentum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String stockName;
    private int monthTimePeriod;
    private Date endDate;
    private Float percentageReturn;

}
