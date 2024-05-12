package com.stock.analysis.Momentum_Strategy.services;

import com.stock.analysis.Momentum_Strategy.Util.DateUtils;
import com.stock.analysis.Momentum_Strategy.model.HalfYearMS;
import com.stock.analysis.Momentum_Strategy.model.StockReturn;
import com.stock.analysis.Momentum_Strategy.repository.HalfYearMSRepository;
import com.stock.analysis.Momentum_Strategy.repository.StockReturnRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class HalfYearMSService {

    @Autowired
    private StockReturnRepository stockReturnRepository;
    @Autowired
    private HalfYearMSRepository halfYearMSRepository;

    public void getHalfYearMSTotalRanking(int startYear,int endYear){
        List<LocalDate> firstMonthDateList = DateUtils.getFirstDateOfMonthBetweenYears(startYear, endYear);
        for (LocalDate statMonthDate : firstMonthDateList) {
            LocalDate endDate=statMonthDate;
            int cnt= stockReturnRepository.countByEndDate(Date.valueOf(endDate));
            if(cnt==0){
                endDate=getStartDateCount(endDate,cnt);
            }

            List<StockReturn> stockReturnList= stockReturnRepository.findByEndDate(Date.valueOf(endDate));
            log.info(STR."Total size list:\{stockReturnList.size()} for End Date:\{endDate}");

            Map<String, List<StockReturn>> StockreturnByNameMap = stockReturnList.stream()
                    .collect(Collectors.groupingBy(StockReturn::getStockName));
            List<HalfYearMS> smList=new ArrayList<>();
            for (Map.Entry<String, List<StockReturn>> entry : StockreturnByNameMap.entrySet()) {
                HalfYearMS halfYearMomentum = getHalfYearMomentum(entry);
                if( halfYearMomentum.isAvailableSixMonth() && halfYearMomentum.isAvailableTwelveMonth()) {
                    smList.add(halfYearMomentum);
                    log.info(STR."Stock Ticker:\{halfYearMomentum.getStockName()} Total Rank:\{halfYearMomentum.getTotalRank()} end Date:\{halfYearMomentum.getEndDate()}");
                }
            }


            List<HalfYearMS> highestRank=smList
                    .stream()
                    .sorted(Comparator.comparing(HalfYearMS::getTotalRank))
                    .limit(20)
                    .toList();
            halfYearMSRepository.saveAll(highestRank);
        }
    }

    private static HalfYearMS getHalfYearMomentum(Map.Entry<String, List<StockReturn>> entry) {
        HalfYearMS halfYearMS=new HalfYearMS();
        halfYearMS.setStockName(entry.getKey());

        int calculateTotalRank=0;
        for (StockReturn sr : entry.getValue()) {
            if(sr.getMonthTimePeriod()==6) {
                halfYearMS.setAvailableSixMonth(true);
                calculateTotalRank += sr.getRank();
                halfYearMS.setSixMonthRank(sr.getRank());
                halfYearMS.setSixMonthReturn(sr.getPercentageReturn());
            }

            if(sr.getMonthTimePeriod()==12) {
                halfYearMS.setAvailableTwelveMonth(true);
                calculateTotalRank += sr.getRank();
                halfYearMS.setTwelveMonthRank(sr.getRank());
                halfYearMS.setTwelveMonthReturn(sr.getPercentageReturn());
            }
            halfYearMS.setEndDate(sr.getEndDate());
        }
        halfYearMS.setTotalRank(calculateTotalRank);
        return halfYearMS;
    }

    public LocalDate getStartDateCount(LocalDate startDate, int count){
        if (count>0)return startDate;
        if(count==0) {
            startDate= startDate.plusDays(1);
            count=  stockReturnRepository.countByEndDate(Date.valueOf(startDate));
        }
        return getStartDateCount(startDate,count);
    }
    public Set<String> getAllStockNameByDate(Date endDate){
        return halfYearMSRepository.findStockNameByEndDate(endDate);
    }
    public List<Date> getAllDistinctDate() {
        return halfYearMSRepository.findDistinctByEndDate();
    }
}