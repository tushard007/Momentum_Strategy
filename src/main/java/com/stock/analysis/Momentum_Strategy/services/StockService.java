package com.stock.analysis.Momentum_Strategy.services;

import com.opencsv.exceptions.CsvException;
import com.stock.analysis.Momentum_Strategy.dao.StockRepository;
import com.stock.analysis.Momentum_Strategy.model.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private CsvReaderService csvReaderService;

    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    public Optional<Stock> getStockById(int id) {
        return stockRepository.findById(id);
    }

    public Stock saveOrUpdateStock(Stock stock) {
        return stockRepository.save(stock);
    }

    public void deleteStock(int id) {
        stockRepository.deleteById(id);
    }

    public int CSVStockUpload() throws IOException, CsvException {
        File file = ResourceUtils.getFile("classpath:DataUpload/bhavCopy.csv");

        List<Stock> stockList=new ArrayList<>();
        Stock stock=null;
        List<String[]> csvData = csvReaderService.readCsvFile(file.getPath());
        for (String[] stringArray : csvData) {
            stock=new Stock();

            stock.setNseSymbol(stringArray[1]);
            stock.setSeries(stringArray[2]);
            stock.setCompanyName(stringArray[3]);
            if(!stringArray[6].isBlank())
            stock.setStockFaceValue(Float.parseFloat(stringArray[6]));
            stock.setPrice(Float.parseFloat(stringArray[8]));
            Float mCap=Float.parseFloat(stringArray[9])/10000000;
            stock.setMarketCapInCr(mCap);
            stockList.add(stock);
        }
        stockRepository.saveAll(stockList);
        return stockList.size();
    }
}
