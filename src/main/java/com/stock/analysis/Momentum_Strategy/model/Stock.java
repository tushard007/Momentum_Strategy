package com.stock.analysis.Momentum_Strategy.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name="Stock_Master_Data")
@Getter
@Setter
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nseSymbol;
    private String series;
    private String companyName;
    private float stockFaceValue;
    private float price;
    private float marketCapInCr;
    @UpdateTimestamp
    private Timestamp updatedAt;


}
