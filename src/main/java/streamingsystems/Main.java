package streamingsystems;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;

/**
 * Main entrypoint for the application.
 */
public class Main {
    /**
     * @param args The command line arguments.
     */
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