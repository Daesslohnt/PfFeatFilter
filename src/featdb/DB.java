package featdb;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DB {
    private static final String COMMA_DELIMITER = "\t";
    private List<List<String>> records;

    public DB(){
        records = readFile("src\\featdb\\Feats_OGL.txt");
    }

    public List<List<String>> getRecords() {
        return records;
    }

    private List<List<String>> readFile(String fileName){

        List<List<String>> r = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER, -1); // -1 um leere Werte beizubehalten
                r.add(Arrays.asList(values));
            }
        } catch (IOException e) {
            System.err.println("Fehler beim Lesen der Datei: " + e.getMessage());
            r = new ArrayList<>(); // Sicherstellen, dass records initialisiert ist
        }
        return r;

    }
}
