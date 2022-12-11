package streamingsystems;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.IntegerSerializer;

import java.util.Properties;

public class TestDataGenerator {


    private static final TestDataGenerator singletonInstance = new TestDataGenerator();
    private final KafkaProducer<Integer, String> kafkaProducer;
    private final String KAFKA_TOPIC_NAME = ConfigManager.INSTANCE.getKafkaTopicName();

    private TestDataGenerator() {
        kafkaProducer = new KafkaProducer<>(generateProperties());
    }

    public static TestDataGenerator getSingletonInstance() {
        return singletonInstance;
    }

    private Properties generateProperties() {
        Properties kafkaProducerProps = new Properties();

        kafkaProducerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, ConfigManager.INSTANCE.getKafkaUrl());
        kafkaProducerProps.put(ProducerConfig.CLIENT_ID_CONFIG, ConfigManager.INSTANCE.getKafkaClientId());
        kafkaProducerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        kafkaProducerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class.getName());

        return kafkaProducerProps;
    }


    public void generateTestData(
            float minSpeed, float maxSpeed, int amountOfSensors, int amountOfSpeedValues, int m1, int m2
    ) throws InterruptedException {
        // Negative speed values should be possible
        // Next step: generate random speed values with a time skip between m1 and m2

        while (true) {
            int randomSensorId = (int)(Math.random() * amountOfSensors);
            int randomAmountOfGeneratedSpeedValues = (int)(Math.random() * amountOfSpeedValues);

            StringBuilder speedValueString = new StringBuilder();

            for (int j = 0; j < randomAmountOfGeneratedSpeedValues; j++) {
                float randomSpeedValue = (float)(Math.random() * (maxSpeed - minSpeed) + minSpeed);
                speedValueString.append(randomSpeedValue).append(",");
            }

            ProducerRecord<Integer, String> recordToSend =
                    new ProducerRecord<>(KAFKA_TOPIC_NAME, randomSensorId, speedValueString.toString());
            kafkaProducer.send(recordToSend);

            Thread.sleep((long)(Math.random() * (m2 - m1) + m1));
        }
    }
}
