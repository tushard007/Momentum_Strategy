package com.stock.analysis.Momentum_Strategy.Util;

public class ReturnCalculationUtils {

    public static float  percentReturn(Float startingPrice,Float closingPrice){
        return (closingPrice/startingPrice)*100;
    }
}
