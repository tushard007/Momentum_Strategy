package com.stock.analysis.Momentum_Strategy.Util;

public class ReturnCalculationUtils {

    public static float  percentReturn(Float closingPrice,Float startingPrice){
        return (closingPrice/startingPrice)*100;
    }
}
