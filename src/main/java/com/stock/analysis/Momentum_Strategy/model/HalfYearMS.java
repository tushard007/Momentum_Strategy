package com.stock.analysis.Momentum_Strategy.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
@Entity
@Table(name="half_year_momentum")
@Getter
@Setter
public class HalfYearMS {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        private String stockName;
        private Date endDate;
        private int totalRank;

        @Transient
        private boolean availableSixMonth;
        @Transient
        private boolean availableTwelveMonth;

        private float sixMonthReturn;
        private float twelveMonthReturn;

        private int sixMonthRank;
        private int twelveMonthRank;


}
