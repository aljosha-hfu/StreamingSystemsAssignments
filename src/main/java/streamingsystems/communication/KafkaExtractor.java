package streamingsystems.communication;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import streamingsystems.CommandsModel.EventStore;
import streamingsystems.CommandsModel.Meta.Event;

import java.time.Duration;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * This class should provide a method to extract all events from kafka
 */
public class KafkaExtractor {

    private final int POLL_FREQUENCY_MILLIS = 100;
    private static final KafkaExtractor singletonInstance = new KafkaExtractor();

    final static String GROUP_ID = "EventStoreClientConsumerGroup";
    private final Logger logger;
    Properties kafkaConsumerProperties;


    private KafkaExtractor() {
        logger = LoggerFactory.getLogger(KafkaExtractor.class);
        kafkaConsumerProperties = generateProperties();
    }

    public static KafkaExtractor getSingletonInstance() {
        return singletonInstance;
    }

    private Properties generateProperties() {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, EventStore.KAFKA_URL);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class.getName());
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        properties.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        properties.setProperty(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        return properties;
    }

    public LinkedList<Event> getEvents(String topic) {
        KafkaConsumer<String, byte[]> kafkaConsumer = new KafkaConsumer<>(kafkaConsumerProperties);
        TopicPartition topicPartition = new TopicPartition(topic, 0);
        kafkaConsumer.assign(List.of(topicPartition));
        kafkaConsumer.seekToBeginning(kafkaConsumer.assignment());
        LinkedList<Event> eventList = new LinkedList<>();

        logger.info("Polling for messages...");
        ConsumerRecords<String, byte[]> consumerRecords = kafkaConsumer.poll(Duration.ofMillis(POLL_FREQUENCY_MILLIS));
        for (ConsumerRecord<String, byte[]> record : consumerRecords) {
            logger.info("BYTES EVENT VALUE: " + Arrays.toString(record.value()));
            Event deserializedData = SerializationUtils.deserialize(record.value());
            eventList.add(deserializedData);
        }

        return eventList;
    }
}
