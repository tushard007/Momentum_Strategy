package com.stock.analysis.Momentum_Strategy.services;

import com.stock.analysis.Momentum_Strategy.Util.DateUtils;
import com.stock.analysis.Momentum_Strategy.Util.ReturnCalculationUtils;
import com.stock.analysis.Momentum_Strategy.dao.StockPriceDataRepository;
import com.stock.analysis.Momentum_Strategy.model.StockMomentum;
import com.stock.analysis.Momentum_Strategy.model.StockPriceData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Slf4j
public class StockPriceDataService {

    @Autowired
    private StockPriceDataRepository repository;

    public List<StockPriceData> getAllStockPriceData() {
        return repository.findAll();
    }


    public Optional<StockPriceData> getStockPriceDataById(int id) {
        return repository.findById(id);
    }

    public StockPriceData saveOrUpdateStockPriceData(StockPriceData stockPriceData) {
        return repository.save(stockPriceData);
    }

    public void deleteStockPriceData(int id) {
        repository.deleteById(id);
    }
    public List<StockMomentum> getAllStockPriceReturnForYear() {
        LocalDate endDate= LocalDate.parse("2024-03-01");
        LocalDate startDate= DateUtils.getDateBeforeYear(endDate,1);
        List<Date> startEndDate=Arrays.asList(Date.valueOf(startDate),Date.valueOf(endDate));
        List<StockPriceData> stockPriceListByStartDate=repository.findByPriceDate(startEndDate);
        log.info("all start end date size:"+stockPriceListByStartDate.size());

        // Group the StockPriceData by Date
        Map<String, List<StockPriceData>> StockPriceDataByName = stockPriceListByStartDate.stream()
                .collect(Collectors.groupingBy(StockPriceData::getStockName));

        List<StockMomentum> objStockMomentumList=new ArrayList<>();
        // Print the results
        for (String stockName : StockPriceDataByName.keySet()) {
            StockMomentum stockMomentum=new StockMomentum();
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
                stockMomentum.setTimePeriod("1 Year");
                objStockMomentumList.add(stockMomentum);
            }
        }
        List<StockMomentum> highestReturnSMList=new ArrayList<>();
                highestReturnSMList=objStockMomentumList
                .stream()
                .sorted(Comparator.comparing(StockMomentum::getPercentageReturn).reversed())
                .toList();

                highestReturnSMList.stream()
                .forEach(stockMomentum -> System.out.println("Stock Name:"+stockMomentum.getStockName()+" return:"+stockMomentum.getPercentageReturn()+"%"));
         return highestReturnSMList;
    }

}
