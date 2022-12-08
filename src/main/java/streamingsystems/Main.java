package streamingsystems;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import streamingsystems.DataRepresentation.Route;
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
        ArrayList<Route> topTripList = KafkaTaxiTripConsumer.getSingletonInstance().getTop10MostFrequentRoutes();

        System.out.println("Top 10 trips:");
        System.out.println(topTripList);
        TaxiTrip test = TaxiTrip.taxiTripFromString(
                "F899990AB4A6582D6A5F21265725216E,474E00C0A8C9619AF6BE05D88A15F78A,2013-01-01 00:05:00,2013-01-01 00:08:00,180,0.40,-73.955826,40.779598,-73.949593,40.777012,UNK,4.00,0.50,0.50,1.00,0.00,6.00");
        logger.info("Top 10:");
        logger.info(Top10RoutesStringBuilder.buildTop10RoutesString(topTripList, test, System.nanoTime()));

        logger.info("Terminating...");
    }
}