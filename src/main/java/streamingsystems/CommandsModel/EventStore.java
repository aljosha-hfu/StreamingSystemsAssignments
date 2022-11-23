package streamingsystems.CommandsModel;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import streamingsystems.CommandsModel.Meta.Event;
import streamingsystems.ConfigManager;

import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class EventStore {
    // Cache settings
    final static public String TOPIC_NAME = ConfigManager.INSTANCE.getKafkaTopicName();
    final static String CLIENT_ID = ConfigManager.INSTANCE.getKafkaClientId();
    final static public String KAFKA_URL = ConfigManager.INSTANCE.getKafkaUrl();
    final KafkaProducer<String, byte[]> kafkaProducer;

    private static final EventStore singletonInstance = new EventStore();
    private final Logger logger;

    private EventStore() {
        logger = LoggerFactory.getLogger(EventStore.class);

        Properties kafkaProducerProps = new Properties();

        kafkaProducerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_URL);
        kafkaProducerProps.put(ProducerConfig.CLIENT_ID_CONFIG, CLIENT_ID);
        kafkaProducerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        kafkaProducerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class.getName());

        kafkaProducer = new KafkaProducer<>(kafkaProducerProps);

        logger.info("Instantiated EventStore singleton...");
    }

    public static EventStore getInstance() {
        return singletonInstance;
    }

    public void addEvent(Event event) {
        byte[] data = SerializationUtils.serialize(event);
        logger.info("Posting serialized message for event " + event + " into Kafka");
        ProducerRecord<String, byte[]> recordToSend = new ProducerRecord<>(TOPIC_NAME, data);

        try {
            RecordMetadata metadata = kafkaProducer.send(recordToSend).get();
            logger.info("Record sent to partition " + metadata.partition() + " with offset " + metadata.offset());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
