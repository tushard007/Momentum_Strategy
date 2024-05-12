package com.stock.analysis.Momentum_Strategy.services;

import com.stock.analysis.Momentum_Strategy.Util.DateUtils;
import com.stock.analysis.Momentum_Strategy.repository.ModifiedSMRepository;
import com.stock.analysis.Momentum_Strategy.repository.StockReturnRepository;
import com.stock.analysis.Momentum_Strategy.model.ModifiedStockMomentum;
import com.stock.analysis.Momentum_Strategy.model.StockReturn;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Slf4j
public class ModifiedSMService {

    @Autowired
    private ModifiedSMRepository modifiedSMRepository;
    @Autowired
    private StockReturnRepository returnRepository;

    public List<ModifiedStockMomentum> getAllStocks() {
        return modifiedSMRepository.findAll();
    }

    public Optional<ModifiedStockMomentum> getStockById(int id) {
        return modifiedSMRepository.findById(id);
    }

    public ModifiedStockMomentum saveOrUpdateStock(ModifiedStockMomentum stock) {
        return modifiedSMRepository.save(stock);
    }

    public void deleteStock(int id) {
        modifiedSMRepository.deleteById(id);
    }

    public void getSMTotalRanking(int startYear,int endYear){
        List<LocalDate> firstMonthDateList = DateUtils.getFirstDateOfMonthBetweenYears(startYear, endYear);
        for (LocalDate statMonthDate : firstMonthDateList) {
            LocalDate endDate=statMonthDate;
            int cnt=returnRepository.countByEndDate(Date.valueOf(endDate));
            if(cnt==0){
                endDate=getStartDateCount(endDate,cnt);
            }

        List<StockReturn> stockReturnList= returnRepository.findByEndDate(Date.valueOf(endDate));
        log.info("Total size list:"+stockReturnList.size()+" for End Date:"+endDate);

        Map<String, List<StockReturn>> StockreturnByNameMap = stockReturnList.stream()
                .collect(Collectors.groupingBy(StockReturn::getStockName));
        List<ModifiedStockMomentum> smList=new ArrayList<>();
        for (Map.Entry<String, List<StockReturn>> entry : StockreturnByNameMap.entrySet()) {
            ModifiedStockMomentum modifiedStockMomentum = getModifiedStockMomentum(entry);
            if(modifiedStockMomentum.isAvailableThreeMonth() && modifiedStockMomentum.isAvailableSixMonth() && modifiedStockMomentum.isAvailableNineMonth() && modifiedStockMomentum.isAvailableTwelveMonth()) {
                smList.add(modifiedStockMomentum);
                log.info("Stock Ticker:"+modifiedStockMomentum.getStockName()+" Total Rank:"+modifiedStockMomentum.getTotalRank()+" end Date:"+modifiedStockMomentum.getEndDate());
            }
        }


        List<ModifiedStockMomentum> highestRank=new ArrayList<>();
        highestRank=smList
                .stream()
                .sorted(Comparator.comparing(ModifiedStockMomentum::getTotalRank))
                .limit(20)
                .toList();
        modifiedSMRepository.saveAll(highestRank);
        }
    }

    private static ModifiedStockMomentum getModifiedStockMomentum(Map.Entry<String, List<StockReturn>> entry) {
        ModifiedStockMomentum sm=new ModifiedStockMomentum();
        sm.setStockName(entry.getKey());

        int calculateTotalRank=0;
        for (StockReturn sr : entry.getValue()) {
            if(sr.getMonthTimePeriod()==3) {
                sm.setAvailableThreeMonth(true);
                calculateTotalRank += sr.getRank();
                sm.setThreeMonthRank(sr.getRank());
                sm.setThreeMonthReturn(sr.getPercentageReturn());
            }
            if(sr.getMonthTimePeriod()==6) {
                sm.setAvailableSixMonth(true);
                calculateTotalRank += sr.getRank();
                sm.setSixMonthRank(sr.getRank());
                sm.setSixMonthReturn(sr.getPercentageReturn());
            }
            if(sr.getMonthTimePeriod()==9) {
                sm.setAvailableNineMonth(true);
                calculateTotalRank += sr.getRank();
                sm.setNineMonthRank(sr.getRank());
                sm.setNineMonthReturn(sr.getPercentageReturn());
            }
            if(sr.getMonthTimePeriod()==12) {
                sm.setAvailableTwelveMonth(true);
                calculateTotalRank += sr.getRank();
                sm.setTwelveMonthRank(sr.getRank());
                sm.setTwelveMonthReturn(sr.getPercentageReturn());
            }
            sm.setEndDate(sr.getEndDate());
        }
        sm.setTotalRank(calculateTotalRank);
        return sm;
    }
    public  Set<String> getAllStockNameByDate(Date endDate){
        return modifiedSMRepository.findStockNameByEndDate(endDate);
    }
    public LocalDate getStartDateCount(LocalDate startDate, int count){
        if (count>0)return startDate;
        if(count==0) {
            startDate= startDate.plusDays(1);
            count=  returnRepository.countByEndDate(Date.valueOf(startDate));
        }
        return getStartDateCount(startDate,count);
    }

    public List<Date> getAllDistinctDate() {
        return modifiedSMRepository.findDistinctByEndDate();
    }
}
