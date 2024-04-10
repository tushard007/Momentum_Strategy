package com.stock.analysis.Momentum_Strategy.controller;

import com.opencsv.exceptions.CsvException;
import com.stock.analysis.Momentum_Strategy.services.EtfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/Etf")
public class EtfController {
    @Autowired
    private EtfService etfService;
    @PostMapping("/CSVUpload")
    public void etfCsvUpload() throws IOException, CsvException {
        int dataUpdated=  etfService.CSVEtfUpload();
    }
}
