package com.stock.analysis.Momentum_Strategy.services;

import com.stock.analysis.Momentum_Strategy.Util.DateUtils;
import com.stock.analysis.Momentum_Strategy.model.StockMomentum;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.List;

@Service
public class ExcelService {
    @Autowired
    StockPriceDataService stockPriceDataService;
    public ResponseEntity<String> getExcel1YearReturn(Workbook workbook) throws IOException {
        List<StockMomentum> smList = stockPriceDataService.getAllStockPriceReturnForYear();
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
        for (StockMomentum sm : smList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(sm.getStockName());
            row.createCell(1).setCellValue(sm.getPercentageReturn());
            row.createCell(2).setCellValue(DateUtils.formatDate(sm.getStartDate().toLocalDate())) ;
            row.createCell(3).setCellValue(sm.getStartDatePrice());
            row.createCell(4).setCellValue(DateUtils.formatDate(sm.getEndDate().toLocalDate()));
            row.createCell(5).setCellValue(sm.getEndDatePrice());
            row.createCell(6).setCellValue(sm.getTimePeriod());

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
