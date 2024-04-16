package com.stock.analysis.Momentum_Strategy.services;

import com.stock.analysis.Momentum_Strategy.Constants.PortfolioConstants;
import com.stock.analysis.Momentum_Strategy.Util.PortfolioUtils;
import com.stock.analysis.Momentum_Strategy.Util.ReturnCalculationUtils;
import com.stock.analysis.Momentum_Strategy.model.PortfolioReturns;
import com.stock.analysis.Momentum_Strategy.model.RawPortfolioStockData;
import com.stock.analysis.Momentum_Strategy.repository.PortfolioReturnsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class PortfolioReturnsService {
    @Autowired
    private StockReturnService stockReturnService;
    @Autowired
    private ModifiedSMService modifiedSMService;
    @Autowired
    private PureSMService pureSMService;
    @Autowired
    private PortfolioReturnsRepository portfolioReturnsRepository;

    public void getMonthlyPortfolioReturns(String strategyName, Date startDate) {
        if (PortfolioConstants.MODIFIED_MOMENTUM_STRATEGY.equals(strategyName)) {


            int year = startDate.toLocalDate().getYear();
            int month = startDate.toLocalDate().getMonthValue();

            Set<String> stockNameSet=modifiedSMService.getAllStockNameByDate(startDate);
            log.info("StockName for Modified Momentum:"+stockNameSet);
            List<PortfolioReturns> portfolioReturnsList=new ArrayList<>();

            List<RawPortfolioStockData> rawPortfolioStockDataList=stockReturnService.getStockPriceStartEndMonthDate(year,month,stockNameSet);
                log.info("MODIFIED_MOMENTUM_STRATEGY rawPortfolioStockDataList size"+rawPortfolioStockDataList.size());
            rawPortfolioStockDataList.forEach(rawPortfolioStockData->{
                PortfolioReturns portfolioReturns = new PortfolioReturns();
                portfolioReturns.setStrategyType(PortfolioConstants.MODIFIED_MOMENTUM_STRATEGY.toUpperCase());
                portfolioReturns.setStockName(rawPortfolioStockData.getStockName());
                portfolioReturns.setMonthStartDate(rawPortfolioStockData.getStartMonthDate());
                portfolioReturns.setStartingPrice(rawPortfolioStockData.getStartDatePrice());
                portfolioReturns.setMonthEndDate(rawPortfolioStockData.getEndMonthDate());
                portfolioReturns.setEndingPrice(rawPortfolioStockData.getEndDatePrice());
                portfolioReturns.setFlatAmountAllocated(PortfolioConstants.FLAT_AMOUNT_ALLOCATION);
                portfolioReturns.setNumberOfStocks(PortfolioUtils.getNumberOfStocks(portfolioReturns.getFlatAmountAllocated(),portfolioReturns.getStartingPrice()));
                portfolioReturns.setInvestedAmount(PortfolioUtils.getAmountBasedOnStockNumber(portfolioReturns.getNumberOfStocks(),portfolioReturns.getStartingPrice()));
                portfolioReturns.setActualAmount(PortfolioUtils.getAmountBasedOnStockNumber(portfolioReturns.getNumberOfStocks(),portfolioReturns.getEndingPrice()));
                portfolioReturns.setPercentageReturn(ReturnCalculationUtils.percentReturn(portfolioReturns.getStartingPrice(),portfolioReturns.getEndingPrice()));

                portfolioReturnsList.add(portfolioReturns);
            });
            portfolioReturnsRepository.saveAll(portfolioReturnsList);
        }
        else if(PortfolioConstants.PURE_MOMENTUM_STRATEGY.equals(strategyName)){


            int year = startDate.toLocalDate().getYear();
            int month = startDate.toLocalDate().getMonthValue();

            Set<String> stockNameSet=pureSMService.getAllStockNameByDate(startDate);
            log.info("StockName for Modified Momentum:"+stockNameSet);
            List<PortfolioReturns> portfolioReturnsList=new ArrayList<>();

            List<RawPortfolioStockData> rawPortfolioStockDataList=stockReturnService.getStockPriceStartEndMonthDate(year,month,stockNameSet);
            log.info("PURE_MOMENTUM_STRATEGY rawPortfolioStockDataList size"+rawPortfolioStockDataList.size());
            rawPortfolioStockDataList.forEach(rawPortfolioStockData->{
                PortfolioReturns portfolioReturns = new PortfolioReturns();
                portfolioReturns.setStrategyType(PortfolioConstants.PURE_MOMENTUM_STRATEGY.toUpperCase());
                portfolioReturns.setStockName(rawPortfolioStockData.getStockName());
                portfolioReturns.setMonthStartDate(rawPortfolioStockData.getStartMonthDate());
                portfolioReturns.setStartingPrice(rawPortfolioStockData.getStartDatePrice());
                portfolioReturns.setMonthEndDate(rawPortfolioStockData.getEndMonthDate());
                portfolioReturns.setEndingPrice(rawPortfolioStockData.getEndDatePrice());
                portfolioReturns.setFlatAmountAllocated(PortfolioConstants.FLAT_AMOUNT_ALLOCATION);
                portfolioReturns.setNumberOfStocks(PortfolioUtils.getNumberOfStocks(portfolioReturns.getFlatAmountAllocated(),portfolioReturns.getStartingPrice()));
                portfolioReturns.setInvestedAmount(PortfolioUtils.getAmountBasedOnStockNumber(portfolioReturns.getNumberOfStocks(),portfolioReturns.getStartingPrice()));
                portfolioReturns.setActualAmount(PortfolioUtils.getAmountBasedOnStockNumber(portfolioReturns.getNumberOfStocks(),portfolioReturns.getEndingPrice()));
                portfolioReturns.setPercentageReturn(ReturnCalculationUtils.percentReturn(portfolioReturns.getStartingPrice(),portfolioReturns.getEndingPrice()));
            //    portfolioReturns.setProfitLoss(portfolioReturns.getActualAmount()-portfolioReturns.getInvestedAmount());
                portfolioReturnsList.add(portfolioReturns);
            });
            portfolioReturnsRepository.saveAll(portfolioReturnsList);
        }
    }
}
