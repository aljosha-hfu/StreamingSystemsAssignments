package streamingsystems;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A class for starting Kafka test data generation.
 */
public class GenerateTestDataMain {
    public static void main(String[] args) throws InterruptedException {
        Logger logger = LoggerFactory.getLogger(GenerateTestDataMain.class);
        logger.info("Starting test data generation...");

        TestDataGenerator.getSingletonInstance().generateTestData(20, 160, 5, 3, 30, 600);

        logger.info("Terminating...");
    }
}