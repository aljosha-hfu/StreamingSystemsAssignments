package streamingsystems.communication;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import streamingsystems.CommandsModel.EventStore;
import streamingsystems.CommandsModel.Meta.Event;
import streamingsystems.MovingItemListGenerator;

import java.lang.reflect.Array;
import java.time.Duration;
import java.util.*;

/**
 * This class should provide a method to extract all events from kafka
 */
public class KafkaExtractor {
    private final String topic;
    private final KafkaConsumer<String, byte[]> kafkaConsumer;
    final static String GROUP_ID = "EventStoreClientConsumerGroup";
    private final Logger logger;


    public KafkaExtractor(String topic) {
        this.topic = topic;
        logger = LoggerFactory.getLogger(KafkaExtractor.class);
        kafkaConsumer = new KafkaConsumer<>(generateProperties());
    }


    private Properties generateProperties() {
        Properties kafkaConsumerProps = new Properties();
        kafkaConsumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, EventStore.KAFKA_URL);
        kafkaConsumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        kafkaConsumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class.getName());
        kafkaConsumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        kafkaConsumerProps.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        kafkaConsumerProps.setProperty(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        return kafkaConsumerProps;
    }
    public LinkedList<Event> getEvents() {
        kafkaConsumer.subscribe(List.of(topic));
        LinkedList<Event> eventList = new LinkedList<>();
        do {
            logger.info("Polling for messages...");
            ConsumerRecords<String, byte[]> consumerRecords = kafkaConsumer.poll(Duration.ofMillis(2500));
            for (ConsumerRecord<String, byte[]> record : consumerRecords) {
                logger.info("BYTES EVENT VALUE: " + Arrays.toString(record.value()));
                Event deserializedData = SerializationUtils.deserialize(record.value());
                eventList.add(deserializedData);
            }
        } while (eventList.isEmpty());

        return eventList;
    }
}
