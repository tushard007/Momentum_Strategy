package com.stock.analysis.Momentum_Strategy.controller;

import com.stock.analysis.Momentum_Strategy.services.PortfolioReturnsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;

@RestController
@RequestMapping("/portfolioReturns")
@Slf4j
public class PortfolioReturnsController {
    @Autowired
    private PortfolioReturnsService portfolioReturnsService;

    @PostMapping("/{strategyName}")
    public void getPortfolioReturns(@PathVariable String strategyName){
        portfolioReturnsService.getMonthlyPortfolioReturns(strategyName);

    }
}
