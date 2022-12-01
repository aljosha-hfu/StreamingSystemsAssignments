package streamingsystems;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class KafkaConnector {
    private static final KafkaConnector singletonInstance = new KafkaConnector();

    final static String GROUP_ID = "EventStoreClientConsumerGroup";
    private final Logger logger;
    Properties kafkaConsumerProperties;


    private KafkaConnector() {
        logger = LoggerFactory.getLogger(KafkaConnector.class);
        kafkaConsumerProperties = generateProperties();
    }

    public static KafkaConnector getSingletonInstance() {
        return singletonInstance;
    }

    private Properties generateProperties() {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, ConfigManager.INSTANCE.getKafkaUrl());
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class.getName());
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        properties.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        properties.setProperty(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        return properties;
    }
}
