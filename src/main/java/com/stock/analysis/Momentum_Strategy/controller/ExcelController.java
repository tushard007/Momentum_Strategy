package com.stock.analysis.Momentum_Strategy.controller;
import com.stock.analysis.Momentum_Strategy.services.ExcelService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/excel")
public class ExcelController {
    @Autowired
    ExcelService excelService;

    @GetMapping("/download")
    public ResponseEntity<String> downloadExcel() {
        try {

            // Create workbook and sheet
            Workbook  workbook = new XSSFWorkbook();
            ResponseEntity<String> re=  excelService.getExcel1YearReturn(workbook);

        return re;
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }

}
