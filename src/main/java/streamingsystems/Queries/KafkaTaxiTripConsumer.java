package streamingsystems.Queries;

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
import streamingsystems.ConfigManager;
import streamingsystems.DataRepresentation.Route;
import streamingsystems.DataRepresentation.TaxiTrip;
import streamingsystems.Top10RoutesStringBuilder;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

public class KafkaTaxiTripConsumer {
    final static String GROUP_ID = "EventStoreClientConsumerGroup";
    private static final KafkaTaxiTripConsumer singletonInstance = new KafkaTaxiTripConsumer();
    private final Logger logger;
    private final Properties kafkaConsumerProperties;


    private KafkaTaxiTripConsumer() {
        logger = LoggerFactory.getLogger(KafkaTaxiTripConsumer.class);
        kafkaConsumerProperties = generateConsumerProperties();
    }

    public static KafkaTaxiTripConsumer getSingletonInstance() {
        return singletonInstance;
    }

    private Properties generateConsumerProperties() {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, ConfigManager.INSTANCE.getKafkaUrl());
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class.getName());
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        properties.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        properties.setProperty(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        return properties;
    }

    public ArrayList<Route> getTop10MostFrequentRoutes() {
        try (KafkaConsumer<String, byte[]> kafkaConsumer = new KafkaConsumer<>(kafkaConsumerProperties)) {
            TopicPartition topicPartition = new TopicPartition(ConfigManager.INSTANCE.getKafkaTopicName(), 0);
            kafkaConsumer.assign(List.of(topicPartition));
            kafkaConsumer.seekToBeginning(kafkaConsumer.assignment());
            ArrayList<TaxiTrip> taxiTripList = new ArrayList<>();

            logger.info("Polling for messages...");
            final int POLL_FREQUENCY_MILLIS = 250;
            ConsumerRecords<String, byte[]> consumerRecords =
                    kafkaConsumer.poll(Duration.ofMillis(POLL_FREQUENCY_MILLIS));
            for (ConsumerRecord<String, byte[]> record : consumerRecords) {
                TaxiTrip deserializedData = SerializationUtils.deserialize(record.value());
                taxiTripList.add(deserializedData);
            }

            HashMap<Route, Long> routeCountMap = new HashMap<>();

            taxiTripList.forEach((TaxiTrip eachTrip) -> routeCountMap.merge(eachTrip.getRoute(), 1L, Long::sum));

            List<Map.Entry<Route, Long>> entryMapList = new ArrayList<>(routeCountMap.entrySet());
            entryMapList.sort(Map.Entry.comparingByValue());

            return entryMapList
                    .stream()
                    .map(Map.Entry::getKey)
                    .limit(10)
                    .collect(Collectors.toCollection(ArrayList::new));
        }
    }

    public void printTop10MostFrequentRoutesForTriggeringTrip(TaxiTrip triggeringTrip) {
        // Get top 10 trips from Kafka
        ArrayList<Route> topTripList = KafkaTaxiTripConsumer.getSingletonInstance().getTop10MostFrequentRoutes();

        // Print top 10 trips in java format
        logger.info("Top 10 trips Java List:");
        logger.info(String.valueOf(topTripList));

        // Print top 10 trips in DEBS string format
        logger.info("Top 10 DEBS output format:");
        logger.info(Top10RoutesStringBuilder.buildTop10RoutesString(topTripList, triggeringTrip, System.nanoTime()));
    }
}
