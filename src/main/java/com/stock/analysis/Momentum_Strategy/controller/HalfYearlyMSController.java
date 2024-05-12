package com.stock.analysis.Momentum_Strategy.controller;

import com.stock.analysis.Momentum_Strategy.services.HalfYearMSService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/halfYearlyMS")
@Slf4j
public class HalfYearlyMSController {
    @Autowired
    HalfYearMSService halfYearMSService;
    @PostMapping("/{startYear}/{endYear}")
    public ResponseEntity<Void> getTotalRanking(@PathVariable int startYear, @PathVariable int endYear){
        halfYearMSService.getHalfYearMSTotalRanking(startYear,endYear);
        return ResponseEntity.noContent().build();
    }
}
