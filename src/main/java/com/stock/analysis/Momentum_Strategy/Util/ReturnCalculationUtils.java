package com.stock.analysis.Momentum_Strategy.Util;

import lombok.extern.slf4j.Slf4j;

import java.text.DecimalFormat;
@Slf4j
public class ReturnCalculationUtils {

    public static float percentReturn(Float startingPrice,Float closingPrice) {
        float priceReturn = 0.001f;
        try {
            priceReturn = ((closingPrice - startingPrice) / startingPrice) * 100;
        } catch (NumberFormatException e) {
            log.error("Error for start Price: " + startingPrice + " close price:" + closingPrice +
                    " \nMessage:" + e.getMessage());
        }

        return priceReturn;
    }
}
