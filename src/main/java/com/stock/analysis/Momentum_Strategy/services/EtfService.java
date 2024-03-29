package com.stock.analysis.Momentum_Strategy.services;

import com.opencsv.exceptions.CsvException;
import com.stock.analysis.Momentum_Strategy.dao.EtfRepository;
import com.stock.analysis.Momentum_Strategy.dao.StockRepository;
import com.stock.analysis.Momentum_Strategy.model.Etf;
import com.stock.analysis.Momentum_Strategy.model.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class EtfService {
    @Autowired
    private EtfRepository etfRepository;
    @Autowired
    private CsvReaderService csvReaderService;

    public int CSVEtfUpload() throws IOException, CsvException {
        File file = ResourceUtils.getFile("classpath:DataUpload/Etf.csv");

        List<Etf> etfList=new ArrayList<>();
        Etf etf=null;
        List<String[]> csvData = csvReaderService.readCsvFile(file.getPath());
        for (String[] stringArray : csvData) {
            etf=new Etf();

            etf.setSeries(stringArray[1]);
            etf.setSecurity(stringArray[3]);
            etf.setSymbol(stringArray[2]);
            etf.setUnderlyingIndex(stringArray[14]);

            etfList.add(etf);
        }
        etfRepository.saveAll(etfList);
        return etfList.size();
    }
    }
