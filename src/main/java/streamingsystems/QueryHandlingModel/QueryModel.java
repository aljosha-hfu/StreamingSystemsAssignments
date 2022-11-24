package streamingsystems.QueryHandlingModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import streamingsystems.CommandsModel.Meta.Event;
import streamingsystems.ConfigManager;
import streamingsystems.MovingItemListGenerator;
import streamingsystems.communication.KafkaExtractor;
import streamingsystems.implemented.MovingItemDTO;
import streamingsystems.implemented.MovingItemImpl;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class QueryModel {

    final static String GROUP_ID = "EventStoreClientConsumerGroup";

    private static QueryModel singletonInstance;

    private QueryModel() {
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

        LinkedList<Event> kafkaEvents = KafkaExtractor.getSingletonInstance().getEvents(ConfigManager.INSTANCE.getKafkaTopicName());
        HashMap<String, MovingItemImpl> movingItems = MovingItemListGenerator.getSingletonInstance().createMovingItemList(kafkaEvents);

        movingItemDTOHashMap = convertToMovingItemDTOMap(movingItems);
    }

    private HashMap<String, MovingItemDTO> convertToMovingItemDTOMap(HashMap<String, MovingItemImpl> movingItemImplHashMap) {
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
