package streamingsystems04.queryhandlingmodel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import streamingsystems04.ConfigManager;
import streamingsystems04.MovingItemListTools;
import streamingsystems04.commandsmodel.meta.Event;
import streamingsystems04.communication.KafkaExtractor;
import streamingsystems04.implemented.MovingItemDTO;
import streamingsystems04.implemented.MovingItemImpl;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * The query model that gets generated from the list of events.
 */
public class QueryModel {

    final static String GROUP_ID = "EventStoreClientConsumerGroup";

    private static QueryModel singletonInstance;
    final Logger logger = LoggerFactory.getLogger(QueryModel.class);
    private final HashMap<String, MovingItemImpl> movingItemImplHashMap = new HashMap<>();
    private HashMap<String, MovingItemDTO> movingItemDTOHashMap = new HashMap<>();

    private QueryModel() {
        System.out.println("Instantiated QueryModel singleton...");
    }

    /**
     * @return The singleton instance of this class.
     */
    public static QueryModel getInstance() {
        if (singletonInstance == null) {
            singletonInstance = new QueryModel();
        }
        return singletonInstance;
    }

    /**
     * Updates the query model from the event store.
     */
    public void updateQueryModel() {
        System.out.println("Updating event store...");
        //        Channel channel = RabbitMQConnectionManager.getInstance().getEventStoreChannel();

        LinkedList<Event> kafkaEvents = KafkaExtractor.getSingletonInstance()
                .getEvents(ConfigManager.INSTANCE.getKafkaTopicName());
        HashMap<String, MovingItemImpl> movingItems = MovingItemListTools.getSingletonInstance()
                .createMovingItemList(kafkaEvents);

        movingItemDTOHashMap = convertToMovingItemDTOMap(movingItems);
    }

    private HashMap<String, MovingItemDTO> convertToMovingItemDTOMap(
            HashMap<String, MovingItemImpl> movingItemImplHashMap) {
        HashMap<String, MovingItemDTO> movingItemDTOHashMap = new HashMap<>();
        movingItemImplHashMap.forEach(
                (k, v) -> movingItemDTOHashMap.put(k, new MovingItemDTO(v)));
        return movingItemDTOHashMap;
    }


    /**
     * @param name The name of the moving item.
     * @return The moving item with the given name as found in the moving
     * item dto hash map.
     */
    public MovingItemDTO getMovingItemDTOByName(String name) {
        if (!movingItemDTOHashMap.containsKey(name)) {
            throw new NoSuchElementException(
                    "There is no Item with this specific name!");
        }
        return movingItemDTOHashMap.get(name);
    }

    /**
     * @return All moving items as a collection.
     */
    public Collection<MovingItemDTO> getAllMovingItems() {
        return this.movingItemDTOHashMap.values();
    }
}
