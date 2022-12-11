package streamingsystems;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(Main.class);
        logger.info("Starting...");
        try {
            TaxiDataReader.readTaxiTripListFromFile(ConfigManager.INSTANCE.getSampleDataPath());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        logger.info("Terminating...");
    }
}