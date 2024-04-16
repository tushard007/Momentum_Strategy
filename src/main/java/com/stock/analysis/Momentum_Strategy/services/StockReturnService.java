package com.stock.analysis.Momentum_Strategy.services;

import com.stock.analysis.Momentum_Strategy.Util.DateUtils;
import com.stock.analysis.Momentum_Strategy.Util.ReturnCalculationUtils;
import com.stock.analysis.Momentum_Strategy.model.RawPortfolioStockData;
import com.stock.analysis.Momentum_Strategy.repository.StockPriceDataRepository;
import com.stock.analysis.Momentum_Strategy.repository.StockReturnRepository;
import com.stock.analysis.Momentum_Strategy.model.StockPriceData;
import com.stock.analysis.Momentum_Strategy.model.StockReturn;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Service
@Slf4j
public class StockReturnService {

    @Autowired
    private StockReturnRepository stockReturnRepository;
    @Autowired
    private StockPriceDataRepository StockDataRepository;

    public List<StockReturn> getAllStocks() {
        return stockReturnRepository.findAll();
    }

    public StockReturn getStockById(int id) {
        return stockReturnRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stock with id " + id + " not found"));
    }

    public StockReturn saveStockReturn(StockReturn stockReturn) {
        return stockReturnRepository.save(stockReturn);
    }

    public StockReturn updateStockReturn(int id, StockReturn newStockReturn) {
        StockReturn existingStockReturn = getStockById(id);
        existingStockReturn.setStockName(newStockReturn.getStockName());
        existingStockReturn.setStartDate(newStockReturn.getStartDate());
        existingStockReturn.setStartDatePrice(newStockReturn.getStartDatePrice());
        existingStockReturn.setEndDate(newStockReturn.getEndDate());
        existingStockReturn.setEndDatePrice(newStockReturn.getEndDatePrice());
        existingStockReturn.setMonthTimePeriod(newStockReturn.getMonthTimePeriod());
        existingStockReturn.setPercentageReturn(newStockReturn.getPercentageReturn());
        existingStockReturn.setRank(newStockReturn.getRank());
        return stockReturnRepository.save(existingStockReturn);
    }

    public void deleteStockReturn(int id) {
        stockReturnRepository.deleteById(id);
    }

    public void getBeforeMonthlyStockReturn(int startYear,int endYear, int month){
        log.trace("Inside StockReturnService::getBeforeMonthlyStockReturn");
        List<LocalDate> firstDateList=DateUtils.getFirstDateOfMonthBetweenYears(startYear,endYear);
        for(LocalDate firstMontDate:firstDateList) {
            LocalDate endDate = firstMontDate;
            int cnt = StockDataRepository.countByPriceDate(Date.valueOf(endDate));
            if (cnt == 0)
                endDate = getStartDateCount(endDate, cnt);
            LocalDate startDate = DateUtils.getDateBeforeMonth(endDate, month);
            log.info("First start date initially:" + startDate);
            int count = StockDataRepository.countByPriceDate(Date.valueOf(startDate));
            if (count == 0)
                startDate = getStartDateCount(startDate, count);
            log.info("After calculation startDate:" + startDate + " endDate:" + endDate);
            List<Date> startEndDate = Arrays.asList(Date.valueOf(startDate), Date.valueOf(endDate));
            List<StockPriceData> stockPriceListByStartDate = StockDataRepository.findByPriceDate(startEndDate);
            log.info("all start end date size:" + stockPriceListByStartDate.size());

            // Group the StockPriceData by Stock Name
            Map<String, List<StockPriceData>> StockPriceDataByName = stockPriceListByStartDate.stream()
                    .collect(Collectors.groupingBy(StockPriceData::getStockName));

            List<StockReturn> objStockMomentumList = new ArrayList<>();
            // Print the results
            for (String stockName : StockPriceDataByName.keySet()) {
                StockReturn stockReturn = new StockReturn();
                stockReturn.setStockName(stockName);
                for (StockPriceData sPData : StockPriceDataByName.get(stockName)) {
                    if (sPData.getPriceDate().equals(Date.valueOf(startDate))) {
                        log.info("\n=====Stock name:" + sPData.getStockName() + "\n Start date" + sPData.getPriceDate() + "\n start date stock Price:" + sPData.getPrice());
                        stockReturn.setStartDate(sPData.getPriceDate());
                        stockReturn.setStartDatePrice(sPData.getPrice());
                    }
                    if (sPData.getPriceDate().equals(Date.valueOf(endDate)) && stockReturn.getStockName().equals(sPData.getStockName())) {
                        log.info("\n-----Stock name:" + sPData.getStockName() + "\n end date" + sPData.getPriceDate() + "\nend date stock Price:" + sPData.getPrice());
                        stockReturn.setEndDate(sPData.getPriceDate());
                        stockReturn.setEndDatePrice(sPData.getPrice());
                    }
                }
                if (stockReturn.getStartDatePrice() > 0 && stockReturn.getEndDatePrice() > 0) {
                    stockReturn.setPercentageReturn(ReturnCalculationUtils.
                            percentReturn(stockReturn.getStartDatePrice(), stockReturn.getEndDatePrice()));
                    log.info("Stock Name:" + stockReturn.getStockName() + " Stock Return:" + stockReturn.getPercentageReturn() + "%");
                    stockReturn.setMonthTimePeriod(month);
                    objStockMomentumList.add(stockReturn);
                }
            }
            List<StockReturn> highestReturnSMList = new ArrayList<>();
            highestReturnSMList = objStockMomentumList
                    .stream()
                    .sorted(Comparator.comparing(StockReturn::getPercentageReturn).reversed())
                    .limit(250)
                    .toList();
            List<StockReturn> finalHighestReturnSMList = highestReturnSMList;
            IntStream.range(0, highestReturnSMList.size())
                    .forEach(i -> finalHighestReturnSMList.get(i).setRank(i + 1));

            stockReturnRepository.saveAll(finalHighestReturnSMList);
        }
    }
//days are plus if stock data price not found for starting month date
    public LocalDate getStartDateCount(LocalDate startDate, int count){
       if (count>0)return startDate;
       if(count==0) {
            startDate= startDate.plusDays(1);
            count=  StockDataRepository.countByPriceDate(Date.valueOf(startDate));
        }
        return getStartDateCount(startDate,count);
    }
    //days are minus if stock data price not found for ending month date
    public LocalDate getEndDateCount(LocalDate endDate, int count){
        if (count>0)return endDate;
        if(count==0) {
            endDate= endDate.minusDays(1);
            count=  StockDataRepository.countByPriceDate(Date.valueOf(endDate));
        }
        return getStartDateCount(endDate,count);
    }

    public List<RawPortfolioStockData> getStockPriceStartEndMonthDate(int year,int monthValue,Set<String>stockNameSet){
        LocalDate monthStartDate=DateUtils.getStartMonthDate(year,monthValue);

        int count=  StockDataRepository.countByPriceDate(Date.valueOf(monthStartDate));
        if(count==0)
            monthStartDate= getStartDateCount(monthStartDate,count);

        LocalDate monthEndDate=DateUtils.getEndMonthDate(year,monthValue);
        int cnt=  StockDataRepository.countByPriceDate(Date.valueOf(monthEndDate));
        if(cnt==0)
            monthEndDate= getEndDateCount(monthEndDate,cnt);

        List<Date> startEndDate= Arrays.asList(Date.valueOf(monthStartDate),Date.valueOf(monthEndDate));
        List<StockPriceData> priceListByStartEndDate=StockDataRepository.findByPriceDate(startEndDate);

        log.info("Start Date:"+DateUtils.formatDate(monthStartDate)+" End Date:"+DateUtils.formatDate(monthEndDate));
        List<RawPortfolioStockData> rawPortfolioStockDataList=new ArrayList<>();
        Map<String, List<StockPriceData>> stockPriceDataByNameMap = priceListByStartEndDate.stream()
                .collect(Collectors.groupingBy(StockPriceData::getStockName));

            for(String stockName:stockNameSet) {
                RawPortfolioStockData rawPortfolioStock = new RawPortfolioStockData();
                for (StockPriceData stockPriceData : stockPriceDataByNameMap.get(stockName)) {
                    rawPortfolioStock.setStockName(stockName);
                    if (stockPriceData.getPriceDate().equals(Date.valueOf(monthStartDate))) {
                        rawPortfolioStock.setStartMonthDate(stockPriceData.getPriceDate());
                        rawPortfolioStock.setStartDatePrice(stockPriceData.getPrice());
                    }
                    if (stockPriceData.getPriceDate().equals(Date.valueOf(monthEndDate))) {
                        rawPortfolioStock.setEndMonthDate(stockPriceData.getPriceDate());
                        rawPortfolioStock.setEndDatePrice(stockPriceData.getPrice());
                    }
                }
                rawPortfolioStockDataList.add(rawPortfolioStock);
            }
        return rawPortfolioStockDataList;
    }
}
