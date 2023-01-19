package streamingsystems06;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.Random;
import java.util.StringJoiner;

/**
 * This class generates test data of virtual sensors for the Kafka topic.
 */
public class TestDataGenerator {


    private static final TestDataGenerator singletonInstance = new TestDataGenerator();
    private final KafkaProducer<Integer, String> kafkaProducer;
    private final String KAFKA_TOPIC_NAME = ConfigManager.INSTANCE.getKafkaTopicName();

    private final Random randomGenerator = new Random(31337101);
    private final Logger logger;

    private TestDataGenerator() {
        kafkaProducer = new KafkaProducer<>(generateProperties());
        logger = LoggerFactory.getLogger(TestDataGenerator.class.getName());
    }

    /**
     * @return the singleton instance of TestDataGenerator
     */
    public static TestDataGenerator getSingletonInstance() {
        return singletonInstance;
    }

    /**
     * @return the properties for the Kafka producer
     */
    private Properties generateProperties() {
        Properties kafkaProducerProps = new Properties();

        kafkaProducerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, ConfigManager.INSTANCE.getKafkaUrl());
        kafkaProducerProps.put(ProducerConfig.CLIENT_ID_CONFIG, ConfigManager.INSTANCE.getKafkaClientId());
        kafkaProducerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        kafkaProducerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        return kafkaProducerProps;
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

        while (true) {
            Integer randomSensorId = (int)(randomGenerator.nextDouble() * amountOfSensors);
            int randomAmountOfGeneratedSpeedValues = (int)(randomGenerator.nextDouble() * amountOfSpeedValues);

            StringJoiner speedValueStringBuilder = new StringJoiner(",");

            // Generate random speed values
            for (int i = 0; i < randomAmountOfGeneratedSpeedValues; i++) {
                float randomSpeedValue = randomGenerator.nextFloat() * (maxSpeed - minSpeed) + minSpeed;
                speedValueStringBuilder.add(String.valueOf(randomSpeedValue));
            }

            ProducerRecord<Integer, String> recordToSend = new ProducerRecord<>(KAFKA_TOPIC_NAME,
                                                                                randomSensorId,
                                                                                speedValueStringBuilder.toString()
            );
            kafkaProducer.send(recordToSend);
            logger.info("Sent record: " + recordToSend);

            long timeToSleep = (long)(randomGenerator.nextDouble() * (m2 - m1) + m1);
            Thread.sleep(timeToSleep);
        }
    }
}
