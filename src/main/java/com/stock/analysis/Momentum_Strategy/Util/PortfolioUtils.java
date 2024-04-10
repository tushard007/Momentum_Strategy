package com.stock.analysis.Momentum_Strategy.Util;

import java.security.PublicKey;

public class PortfolioUtils {
    public static int getNumberOfStocks(Float flatAmount,Float stockPrice){
        int numberOfStock=0;
        numberOfStock= (int) (flatAmount/stockPrice);
        return numberOfStock;
    }

    public static Float getAmountBasedOnStockNumber(int stockNumber,Float price){
        float amount=0.2f;
        amount=stockNumber*price;
        return amount;
    }
}
