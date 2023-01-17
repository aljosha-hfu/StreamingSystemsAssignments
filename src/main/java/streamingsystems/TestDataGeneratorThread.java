package streamingsystems;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A class for starting Kafka test data generation.
 */
public class TestDataGeneratorThread extends Thread {
    private final Logger logger = LoggerFactory.getLogger(TestDataGeneratorThread.class.getName());

    public void run() {
        try {
            generateTestData();
        } catch (InterruptedException e) {
            logger.error("Could not start test data generation!");
            logger.error(e.getMessage());
        }
    }

    private void generateTestData() throws InterruptedException {
        logger.info("Starting test data generation...");
        TestDataGenerator.getSingletonInstance().generateTestData(-20, 160, 5, 5, 20, 400);
        logger.info("Terminating...");
    }
}