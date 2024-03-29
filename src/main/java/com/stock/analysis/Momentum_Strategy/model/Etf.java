package com.stock.analysis.Momentum_Strategy.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="ETF")
@Getter
@Setter
public class Etf {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String series;
    private String security;
    private String symbol;
    private String underlyingIndex;

}
