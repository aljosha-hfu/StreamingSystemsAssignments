package streamingsystems;

import streamingsystems.DataRepresentation.TaxiTrip;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class TaxiDataReader {
    public static void readTaxiTripListFromFile(String filePath) throws FileNotFoundException {
        BufferedReader bufferedReader = new BufferedReader((new FileReader(filePath)));
        bufferedReader.lines().forEach(line -> {
            String[] split = line.split(",");
            if (split.length != 17) {
                TaxiTrip taxiTrip = TaxiTrip.taxiTripFromStringList(split);
                if (taxiTrip != null) {
                    KafkaTaxiTripWriter.getSingletonInstance().writeTaxiTripToKafka(taxiTrip);
                }
            }
        });
    }
}
