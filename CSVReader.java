package oracledb;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

public class CSVReader {
    public void readCSV(String filePath, Consumer<Gmarket> consumer) {
        try (InputStream inputStream = new FileInputStream(filePath);
             Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader bufferedReader = new BufferedReader(reader)) {

            String line;
            boolean isFirstLine = true;

            while ((line = bufferedReader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] columns = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                if (columns.length == 4) {
                    try {
                        String productName = columns[0].replace("\"", "").trim();
                        int productPrice = Integer.parseInt(columns[1].replace("\"", "").replace(",", "").trim());
                        int productBuyCount = Integer.parseInt(columns[2].replace("\"", "").replace(",", "").trim());
                        int productReviewCount = Integer.parseInt(columns[3].replace("\"", "").replace(",", "").trim());
                        consumer.accept(new Gmarket(productName, productPrice, productBuyCount, productReviewCount));
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid number format in line: " + line);
                    }
                } else {
                    System.out.println("Invalid CSV format: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading CSV file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
