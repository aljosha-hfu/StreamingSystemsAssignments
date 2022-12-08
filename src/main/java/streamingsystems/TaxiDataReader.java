package streamingsystems;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import streamingsystems.DataRepresentation.TaxiTrip;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.concurrent.atomic.AtomicLong;

//TODO change to singleton (for consistence)!
public class TaxiDataReader {
    public static void readTaxiTripListFromFile(String filePath) throws FileNotFoundException {
        Logger logger = LoggerFactory.getLogger(TaxiDataReader.class.getName());
        logger.info("Reading file " + filePath);
        BufferedReader bufferedReader = new BufferedReader((new FileReader(filePath)));

        AtomicLong lineIndex = new AtomicLong();

        bufferedReader.lines().forEach(eachLineString -> {
            String[] split = eachLineString.split(",");
            if (split.length == 17) {
                TaxiTrip taxiTrip = TaxiTrip.taxiTripFromStringList(split);
                if (taxiTrip != null) {
                    //                    logger.info("---- Adding TaxiTrip with pickUp time: " + taxiTrip.pickupDatetime());
                    KafkaTaxiTripWriter.getSingletonInstance().writeTaxiTripToKafka(taxiTrip);
                } else {
                    logger.info("TaxiTrip read at line " + lineIndex.get() + " is null. Ignoring it");

                }
            } else {
                logger.info("Read line at index " + lineIndex.get() +
                        " is not valid! Expected a line with 17 entries, got " + split.length);
            }

            lineIndex.getAndIncrement();
        });
    }
}
