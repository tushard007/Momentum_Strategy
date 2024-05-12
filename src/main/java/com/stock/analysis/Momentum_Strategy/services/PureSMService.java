package com.stock.analysis.Momentum_Strategy.services;

import com.stock.analysis.Momentum_Strategy.Util.DateUtils;
import com.stock.analysis.Momentum_Strategy.model.PureStockMomentum;
import com.stock.analysis.Momentum_Strategy.model.StockReturn;
import com.stock.analysis.Momentum_Strategy.repository.PureSMRepository;
import com.stock.analysis.Momentum_Strategy.repository.StockReturnRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class PureSMService {
    @Autowired
    private PureSMRepository pureSMRepository;
    @Autowired
    private StockReturnRepository StockReturnRepository;

    public List<PureStockMomentum> getAllStocks() {
        return pureSMRepository.findAll();
    }

    public Optional<PureStockMomentum> getStockById(int id) {
        return pureSMRepository.findById(id);
    }

    public PureStockMomentum saveOrUpdateStock(PureStockMomentum stock) {
        return pureSMRepository.save(stock);
    }

    public void deleteStock(int id) {
        pureSMRepository.deleteById(id);
    }

    public void getTopPureMomentumStocks(int startYear,int endYear) {
        List<LocalDate> firstMonthDateList = DateUtils.getFirstDateOfMonthBetweenYears(startYear, endYear);
        for (LocalDate statMonthDate : firstMonthDateList) {
            LocalDate endDate=statMonthDate;
            int cnt=StockReturnRepository.countByEndDate(Date.valueOf(endDate));
            if(cnt==0){
                endDate=getStartDateCount(endDate,cnt);
            }
            List<StockReturn> stockReturnList = StockReturnRepository.findByEndDateAndMonthTimePeriod(Date.valueOf(endDate), 12);
            log.info("Total size list:" + stockReturnList.size() + " for End Date:" + endDate + " time period for " + 12 + " month");
            List<PureStockMomentum> pureSMList = new ArrayList<>();
            for (StockReturn sr : stockReturnList) {
                PureStockMomentum pureSM = new PureStockMomentum();
                pureSM.setStockName(sr.getStockName());
                pureSM.setEndDate(sr.getEndDate());
                pureSM.setPercentageReturn(sr.getPercentageReturn());
                pureSM.setMonthTimePeriod(sr.getMonthTimePeriod());
                pureSMList.add(pureSM);
            }
            pureSMList = pureSMList
                    .stream()
                    .limit(20)
                    .toList();

            pureSMRepository.saveAll(pureSMList);
        }
    }
    public Set<String> getAllStockNameByDate(Date endDate) {
        return pureSMRepository.findStockNameByEndDate(endDate);
    }
    public List<Date> getAllDistinctDate() {
        return pureSMRepository.findDistinctByEndDate();
    }


    public LocalDate getStartDateCount(LocalDate startDate, int count){
        if (count>0)return startDate;
        if(count==0) {
            startDate= startDate.plusDays(1);
            count=  StockReturnRepository.countByEndDate(Date.valueOf(startDate));
        }
        return getStartDateCount(startDate,count);
    }
}
