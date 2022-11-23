package streamingsystems.QueryHandlingModel;

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
import streamingsystems.implemented.MovingItemDTO;
import streamingsystems.implemented.MovingItemImpl;

import java.time.Duration;
import java.util.*;

public class QueryModel {

    final static String GROUP_ID = "EventStoreClientConsumerGroup";
    final KafkaConsumer<String, byte[]> kafkaConsumer;

    private static QueryModel singletonInstance;

    private QueryModel() {
        Properties kafkaConsumerProps = new Properties();

        kafkaConsumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, EventStore.KAFKA_URL);
        kafkaConsumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        kafkaConsumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class.getName());
        kafkaConsumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        kafkaConsumerProps.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        kafkaConsumerProps.setProperty(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        kafkaConsumer = new KafkaConsumer<>(kafkaConsumerProps);
        kafkaConsumer.subscribe(List.of(EventStore.TOPIC_NAME));

        System.out.println("Instantiated QueryModel singleton...");
    }

    public static QueryModel getInstance() {
        if (singletonInstance == null) {
            singletonInstance = new QueryModel();
            singletonInstance.updateEventStore();
        }
        return singletonInstance;
    }


    final Logger logger = LoggerFactory.getLogger(QueryModel.class);

    private HashMap<String, MovingItemDTO> movingItemDTOHashMap = new HashMap<>();
    private HashMap<String, MovingItemImpl> movingItemImplHashMap = new HashMap<>();


    public void updateEventStore() {
        System.out.println("Updating event store...");
//        Channel channel = RabbitMQConnectionManager.getInstance().getEventStoreChannel();

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

        movingItemImplHashMap = MovingItemListGenerator.getSingletonInstance().createMovingItemList(eventList);
        movingItemDTOHashMap = convertToMovingItemDTOMap(movingItemImplHashMap);
    }

    private HashMap<String, MovingItemDTO> convertToMovingItemDTOMap(HashMap<String, streamingsystems.implemented.MovingItemImpl> movingItemImplHashMap) {
        HashMap<String, MovingItemDTO> movingItemDTOHashMap = new HashMap<>();
        movingItemImplHashMap.forEach((k, v) -> movingItemDTOHashMap.put(k, new MovingItemDTO(v)));
        return movingItemDTOHashMap;
    }


    public MovingItemDTO getMovingItemDTOByName(String name) {
        if (!movingItemDTOHashMap.containsKey(name)) {
            throw new NoSuchElementException("There is no Item with this specific name!");
        }
        return movingItemDTOHashMap.get(name);
    }

    public MovingItemImpl getMovingItemImplByName(String name) {
        if (!movingItemImplHashMap.containsKey(name)) {
            throw new IllegalArgumentException("movingItemImplHashMap does not contain key " + name);
        }
        return movingItemImplHashMap.get(name);
    }

    public Collection<MovingItemDTO> getAllMovingItems() {
        return this.movingItemDTOHashMap.values();
    }
}
