package com.stock.analysis.Momentum_Strategy.services;

import com.stock.analysis.Momentum_Strategy.Util.DateUtils;
import com.stock.analysis.Momentum_Strategy.model.StockMomentum;
import com.stock.analysis.Momentum_Strategy.model.StockPriceData;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Service
public class ExcelService {
    @Autowired
    StockPriceDataService stockPriceDataService;
    public ResponseEntity<String> getExcel1YearReturn(Workbook workbook) throws IOException {
        List<StockPriceData> smList = stockPriceDataService.getAllStockPriceData();
        Sheet sheet = workbook.createSheet("1 Year return");


        // Create header row
        Row headerRow = sheet.createRow(0);
        String[] headers = {"Stock Name","Percentage Return" ,"Start Date", "Start Date Price",
                "End Date","End date Price","Time Period"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // Add data rows
        int rowNum = 1;
        for (StockPriceData sm : smList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(sm.getStockName());


        }

        // Write workbook to a file within the resources folder
        ClassPathResource resource = new ClassPathResource("");
        File resourcesFolder = resource.getFile();
        File excelFile = new File(resourcesFolder, "stockMarket.xlsx");
        try (OutputStream fileOut = new FileOutputStream(excelFile)) {
            workbook.write(fileOut);
        }
        try {
            workbook.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return ResponseEntity.ok().body("Excel file generated at: "+excelFile.getAbsolutePath());

    }
}
