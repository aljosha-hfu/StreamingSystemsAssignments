package streamingsystems;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import streamingsystems.events.SensorEvent;

import java.util.Random;

/**
 * This class generates test data of virtual sensors and sends them to esper.
 */
public class TestDataGenerator {


    private static final TestDataGenerator singletonInstance = new TestDataGenerator();

    private final Random randomGenerator = new Random(31337101);
    private final Logger logger;

    private TestDataGenerator() {
        logger = LoggerFactory.getLogger(TestDataGenerator.class.getName());
    }

    /**
     * @return the singleton instance of TestDataGenerator
     */
    public static TestDataGenerator getSingletonInstance() {
        return singletonInstance;
    }


    /**
     * @param minSpeed            Minimum speed in km/h
     * @param maxSpeed            Maximum speed in km/h
     * @param amountOfSensors     Amount of sensors
     * @param amountOfSpeedValues Max amount of speed values per sensor (0 - n)
     * @param m1                  Minimum time between two speed values in ms
     * @param m2                  Maximum time between two speed values in ms
     * @throws InterruptedException Thrown if the thread is interrupted
     */
    @SuppressWarnings("InfiniteLoopStatement") public void generateTestData(
            float minSpeed, float maxSpeed, int amountOfSensors, int amountOfSpeedValues, int m1, int m2
    ) throws InterruptedException {
        // Negative speed values should be possible
        // Next step: generate random speed values with a time skip between m1 and m2

        // Traffic jam counter
        int trafficJamCounter = 1;
        int trafficJamFrequency = 50;

        int randomSensorId;
        int randomAmountOfGeneratedSpeedValues;

        while (true) {
            // Random values
            randomSensorId = (int)(randomGenerator.nextDouble() * amountOfSensors);

            if (trafficJamCounter % trafficJamFrequency == 0) {
                System.out.println("Simulating TRAFFIC JAM for sensor ID: " + randomSensorId);
                // Send the event 100 times
                for (int j = 0; j < 100; j++) {
                    sendRandomDataMessage(0, 6, randomSensorId);
                }
            }

            // Generate random speed values
            for (int i = 0; i < amountOfSpeedValues; i++) {
                sendRandomDataMessage(minSpeed, maxSpeed, randomSensorId);
            }

            logger.info("Sent data for sensor id "
                        + randomSensorId
                        + " - traffic jam counter is at "
                        + trafficJamCounter % trafficJamFrequency);

            long timeToSleep = (long)(randomGenerator.nextDouble() * (m2 - m1) + m1);
            Thread.sleep(timeToSleep);

            // Increase traffic jam counter
            trafficJamCounter++;
        }
    }

    private void sendRandomDataMessage(float thisSensorMinSpeed, float thisSensorMaxSpeed, int randomSensorId) {
        float
                randomSpeedValue =
                randomGenerator.nextFloat() * (thisSensorMaxSpeed - thisSensorMinSpeed) + thisSensorMinSpeed;

        SensorEvent sensorEvent = new SensorEvent(randomSensorId, randomSpeedValue);
        EsperClient.getINSTANCE().getSensorEventSender().sendEvent(sensorEvent);
    }
}
