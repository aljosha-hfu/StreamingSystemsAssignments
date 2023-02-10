package streamingsystems07;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import streamingsystems07.events.SensorEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.StringJoiner;

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
        // Next step: generate random speed values with a time skip between m1 and m

        // Traffic jam counter
        int trafficJamCounter = 1;
        int trafficJamFrequency = 50;

        int minTrafficJamSpeed = 0;
        int maxTrafficJamSpeed = 6;

        int randomSensorId;


        while (true) {
            randomSensorId = (int)(randomGenerator.nextDouble() * amountOfSensors);

            StringJoiner speedValueStringBuilder = new StringJoiner(",");

            // Generate random speed values
            for (int i = 0; i < amountOfSpeedValues; i++) {
                float randomSpeedValue = randomGenerator.nextFloat() * (maxSpeed - minSpeed) + minSpeed;
                speedValueStringBuilder.add(String.valueOf(randomSpeedValue));
            }

            // Generate additional speed events that simulate traffic jam every 50 times
            if (trafficJamCounter % trafficJamFrequency == 0) {
                System.out.println("Simulating TRAFFIC JAM for sensor ID: " + randomSensorId);
                // Send the event 100 times
                for (int j = 0; j < 100; j++) {
                    float
                            randomTrafficJamSpeedValue =
                            randomGenerator.nextFloat() * (maxTrafficJamSpeed - minTrafficJamSpeed)
                            + minTrafficJamSpeed;
                    speedValueStringBuilder.add(String.valueOf(randomTrafficJamSpeedValue));
                }
            }
            logger.info("(TrafficCount= "+trafficJamCounter + ")Generated data for sensor with id = "
                        + randomSensorId
                        + ": "
                        + speedValueStringBuilder);
            sendSensorEvents(generateSensorEventList(randomSensorId, speedValueStringBuilder.toString()));
            //logger.info("Sent record: " + recordToSend);

            long timeToSleep = (long)(randomGenerator.nextDouble() * (m2 - m1) + m1);
            Thread.sleep(timeToSleep);
            // Increase traffic jam counter
            trafficJamCounter++;
        }

    }

    private ArrayList<SensorEvent> generateSensorEventList(int sensorId, String speedValues) {
        ArrayList<SensorEvent> sensorEvents = new ArrayList<>();
        Arrays.stream(speedValues.split(",")).forEach(speedValue -> sensorEvents.add(new SensorEvent(sensorId,
                                                                                                     Double.parseDouble(speedValue)
        )));
        return sensorEvents;
    }

    private void sendSensorEvents(ArrayList<SensorEvent> sensorEvents) {
        sensorEvents.forEach(sensorEvent -> {
            EsperClient.getINSTANCE().getSensorEventSender().sendEvent(sensorEvent);
            //logger.info("Sent SensorEvent: sensorId= "                        + sensorEvent.getSensorId()+ " speed: "+sensorEvent.getSpeed());
        });
    }
}
