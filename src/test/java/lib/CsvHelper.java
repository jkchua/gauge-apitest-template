package lib;

import java.io.FileReader;
import java.util.List;

import com.opencsv.CSVReader;

public class CsvHelper {

    public List<String[]> readCsvFile(String filePath) {

        try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {

            List<String[]> records = csvReader.readAll();

            return records;

        } catch (Exception exception) {

            exception.printStackTrace();

            return null;
        }
    }
}
