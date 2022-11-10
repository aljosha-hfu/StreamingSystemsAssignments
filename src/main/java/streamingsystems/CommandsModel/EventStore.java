package streamingsystems.CommandsModel;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import streamingsystems.CommandsModel.Meta.Event;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

public class EventStore {
    final String TOPIC_NAME = "EventStore";
    Properties kafkaProps;
    Producer<String, String> kafkaProducer;

    private static final EventStore singletonInstance = new EventStore();
    private final Logger logger;

    private EventStore() {
        logger = LoggerFactory.getLogger(EventStore.class);

        kafkaProps = new Properties();

        kafkaProps.put("bootstrap.servers", "localhost:9092");
        kafkaProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        kafkaProducer = new KafkaProducer<>(kafkaProps);

        logger.info("Instantiated EventStore singleton...");
    }

    public static EventStore getInstance() {
        return singletonInstance;
    }

    public void addEvent(Event event) {
        byte[] data = SerializationUtils.serialize(event);
        logger.info("Posting serialized message for event " + event + " into Kafka");
        String stringData = Arrays.toString(data);
        kafkaProducer.send(new ProducerRecord<>(TOPIC_NAME, stringData, stringData));
    }

}
