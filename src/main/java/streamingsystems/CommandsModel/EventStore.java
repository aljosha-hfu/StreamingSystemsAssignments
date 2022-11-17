package streamingsystems.CommandsModel;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import streamingsystems.CommandsModel.Meta.Event;

import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class EventStore {
    final static String TOPIC_NAME = "EventStore";
    final static String CLIENT_ID = "EventStoreClient";
    final static String KAFKA_URL = "localhost:9092";
    KafkaProducer<String, String> kafkaProducer;

    private static final EventStore singletonInstance = new EventStore();
    private final Logger logger;

    private EventStore() {
        logger = LoggerFactory.getLogger(EventStore.class);

        Properties kafkaProducerProps = new Properties();

        kafkaProducerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_URL);
        kafkaProducerProps.put(ProducerConfig.CLIENT_ID_CONFIG, CLIENT_ID);
        kafkaProducerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        kafkaProducerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        kafkaProducer = new KafkaProducer<>(kafkaProducerProps);

        logger.info("Instantiated EventStore singleton...");
    }

    public static EventStore getInstance() {
        return singletonInstance;
    }

    public void addEvent(Event event) {
        byte[] data = SerializationUtils.serialize(event);
        logger.info("Posting serialized message for event " + event + " into Kafka");
        String stringData = Arrays.toString(data);
        ProducerRecord<String, String> recordToSend = new ProducerRecord<>(TOPIC_NAME, stringData);

        try {
            RecordMetadata metadata = kafkaProducer.send(recordToSend).get();
            System.out.println("Record sent to partition " + metadata.partition() + " with offset " + metadata.offset());
//            kafkaProducer.flush();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
