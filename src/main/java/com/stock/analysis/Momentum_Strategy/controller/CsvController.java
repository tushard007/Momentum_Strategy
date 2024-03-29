package com.stock.analysis.Momentum_Strategy.controller;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@Controller
public class CsvController {

    @GetMapping("/")
    public String index() {
        return "upload";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model) {
       System.out.println("Filename:"+file.getOriginalFilename());

        try (Reader reader = new InputStreamReader(file.getInputStream());
             CSVReader csvReader = new CSVReader(reader)) {
            List<String[]> csvData = csvReader.readAll();
            for (String[] stringArray : csvData) {
               // System.out.println(Arrays.toString(new String[]{stringArray[3]}));
            }
            model.addAttribute("csvData", csvData);
        } catch (IOException | CsvException e) {
            // Handle file read exception
            e.printStackTrace();
        }
        return "index";
    }
}
