package streamingsystems;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import streamingsystems.DataRepresentation.TaxiTrip;
import streamingsystems.Queries.KafkaTaxiTripConsumer;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(Main.class);
        logger.info("Starting...");
        try {
            TaxiDataReader.readTaxiTripListFromFile(ConfigManager.INSTANCE.getSampleDataPath());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        ArrayList<TaxiTrip> topTripList =
                KafkaTaxiTripConsumer.getSingletonInstance().getTop10MostFrequentRoutes();

        System.out.println("Top 10 trips:");
        System.out.println(topTripList);

        logger.info("Terminating...");
    }
}