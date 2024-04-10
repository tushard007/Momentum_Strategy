package com.stock.analysis.Momentum_Strategy.services;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import com.opencsv.exceptions.CsvException;
import org.springframework.stereotype.Service;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Service
public class CsvReaderService {

    public List<String[]> readCsvFile(String filePath) throws IOException, CsvException {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            return reader.readAll();
        }

        // Create a CSVReader object
//        CSVReader csvReader = new CSVReaderBuilder(new FileReader(filePath))
//                .withSkipLines(1) // Skip the header row
//                .build();
//
//        // Read the CSV file line by line
//        List<String[]> csvReaderArray=null;
//        String[] nextLine;
//        while ((nextLine = csvReader.readNext()) != null) {
//            // Do something with the data in the line
//         //   System.out.println(nextLine[0]); // Print the first column
//            csvReaderArray.add(nextLine);
//        }
//        // Close the CSVReader object
//            csvReader.close();
//            return csvReaderArray;
    }
}
