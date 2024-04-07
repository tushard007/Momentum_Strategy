package com.stock.analysis.Momentum_Strategy.services;

import com.stock.analysis.Momentum_Strategy.Util.DateUtils;
import com.stock.analysis.Momentum_Strategy.Util.ReturnCalculationUtils;
import com.stock.analysis.Momentum_Strategy.dao.StockPriceDataRepository;
import com.stock.analysis.Momentum_Strategy.dao.StockReturnRepository;
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
    public List<StockReturn> getMonthlyStockReturn(Date stockEndDate,int month){

        LocalDate endDate= stockEndDate.toLocalDate();
        LocalDate startDate= DateUtils.getDateBeforeMonth(endDate,month);
        List<Date> startEndDate= Arrays.asList(Date.valueOf(startDate),Date.valueOf(endDate));
        List<StockPriceData> stockPriceListByStartDate=StockDataRepository.findByPriceDate(startEndDate);
        log.info("all start end date size:"+stockPriceListByStartDate.size());

        // Group the StockPriceData by Date
        Map<String, List<StockPriceData>> StockPriceDataByName = stockPriceListByStartDate.stream()
                .collect(Collectors.groupingBy(StockPriceData::getStockName));

        List<StockReturn> objStockMomentumList=new ArrayList<>();
        // Print the results
        for (String stockName : StockPriceDataByName.keySet()) {
            StockReturn stockMomentum=new StockReturn();
            stockMomentum.setStockName(stockName);
            for (StockPriceData sPData : StockPriceDataByName.get(stockName)) {
                if(sPData.getPriceDate().equals(Date.valueOf(startDate))){
                    log.debug("\n=====Stock name:"+sPData.getStockName()+"\n Start date"+sPData.getPriceDate()+"\n start date stock Price:"+sPData.getPrice());
                    stockMomentum.setStartDate(sPData.getPriceDate());
                    stockMomentum.setStartDatePrice(sPData.getPrice());
                }
                if(sPData.getPriceDate().equals(Date.valueOf(endDate))&&stockMomentum.getStockName().equals(sPData.getStockName())){
                    log.debug("\n-----Stock name:"+sPData.getStockName()+"\n end date"+sPData.getPriceDate()+"\nend date stock Price:"+sPData.getPrice());
                    stockMomentum.setEndDate(sPData.getPriceDate());
                    stockMomentum.setEndDatePrice(sPData.getPrice());
                }
            }
            if(stockMomentum.getStartDatePrice()>0 && stockMomentum.getEndDatePrice()>0) {
                stockMomentum.setPercentageReturn(ReturnCalculationUtils.
                        percentReturn(stockMomentum.getStartDatePrice(),stockMomentum.getEndDatePrice()));
                log.debug("Stock Name:"+stockMomentum.getStockName()+" Stock Return:"+stockMomentum.getPercentageReturn()+"%");
                stockMomentum.setMonthTimePeriod(month);
                objStockMomentumList.add(stockMomentum);
            }
        }
        List<StockReturn> highestReturnSMList=new ArrayList<>();
        highestReturnSMList=objStockMomentumList
                .stream()
                .sorted(Comparator.comparing(StockReturn::getPercentageReturn).reversed())
                .toList();

        List<StockReturn> finalHighestReturnSMList = highestReturnSMList;
        IntStream.range(0, highestReturnSMList.size())
                .forEach(i -> finalHighestReturnSMList.get(i).setRank(i + 1));

         stockReturnRepository.saveAll(finalHighestReturnSMList);
        return finalHighestReturnSMList;
    }
}
