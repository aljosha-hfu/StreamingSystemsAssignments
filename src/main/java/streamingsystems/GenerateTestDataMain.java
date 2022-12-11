package streamingsystems;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenerateTestDataMain {
    public static void main(String[] args) throws InterruptedException {
        Logger logger = LoggerFactory.getLogger(GenerateTestDataMain.class);
        logger.info("Starting test data generation...");

        TestDataGenerator.getSingletonInstance().generateTestData(0, 100, 10, 5, 100, 800);

        logger.info("Terminating...");
    }
}