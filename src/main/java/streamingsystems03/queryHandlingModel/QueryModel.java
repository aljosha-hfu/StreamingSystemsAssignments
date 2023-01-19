package streamingsystems03.queryHandlingModel;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.GetResponse;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import streamingsystems03.commandsModel.meta.Event;
import streamingsystems03.RabbitMQConnectionManager;
import streamingsystems03.implemented.MovingItemDTO;
import streamingsystems03.implemented.MovingItemImpl;

import java.io.IOException;
import java.util.*;

/**
 * The query model that gets generated from the list of events.
 */
public class QueryModel {


    private static QueryModel singletonInstance;

    private QueryModel() {
        System.out.println("Instantiated QueryModel singleton...");
    }

    /**
     * @return The singleton instance of the query model.
     */
    public static QueryModel getInstance() {
        if (singletonInstance == null) {
            singletonInstance = new QueryModel();
            singletonInstance.updateQueryModel();

        }
        return singletonInstance;
    }


    final Logger logger = LoggerFactory.getLogger(QueryModel.class);

    private HashMap<String, MovingItemDTO> movingItemDTOHashMap = new HashMap<>();
    private final HashMap<String, MovingItemImpl> movingItemImplHashMap = new HashMap<>();


    /**
     * Updates the query model with the latest events from the event store.
     */
    public void updateQueryModel() {
        System.out.println("Updating event store...");
        Channel channel = RabbitMQConnectionManager.getInstance()
                .getEventStoreChannel();

        LinkedList<Event> eventList = new LinkedList<>();


        GetResponse response;
        do {
            try {
                response = channel.basicGet(
                        RabbitMQConnectionManager.QUEUE_NAME, true);
                if (response != null) {
                    AMQP.BasicProperties props = response.getProps();
                    byte[] body = response.getBody();

                    System.out.println("New event from RabbitMQ:");
                    System.out.println(Arrays.toString(body));

                    Event deserializedData = SerializationUtils.deserialize(
                            body);
                    eventList.add(deserializedData);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } while (response != null);


        recalculateEventStoreFromEvents(eventList);
        movingItemDTOHashMap = convertToMovingItemDTOMap(movingItemImplHashMap);
    }

    private HashMap<String, MovingItemDTO> convertToMovingItemDTOMap(
            HashMap<String, streamingsystems03.implemented.MovingItemImpl> movingItemImplHashMap) {
        HashMap<String, MovingItemDTO> movingItemDTOHashMap = new HashMap<>();
        movingItemImplHashMap.forEach(
                (k, v) -> movingItemDTOHashMap.put(k, new MovingItemDTO(v)));
        return movingItemDTOHashMap;
    }


    private void recalculateEventStoreFromEvents(
            LinkedList<Event> eventLinkedList) {
        logger.info("Recalculating EventStore ...");
        eventLinkedList.forEach(event -> {
            logger.info("Event: " + event.getClass()
                    .getName() + ": " + event.getId());
            MovingItemImpl applyReturnValue = event.apply();
            if (applyReturnValue != null) {
                movingItemImplHashMap.put(event.getId(), applyReturnValue);
            } else {
                movingItemImplHashMap.remove(event.getId());
            }
        });
        movingItemImplHashMap.forEach((k, v) -> logger.info(k + " " + v));
    }


    /**
     * Get a moving item DTO by its name.
     *
     * @param name The name of the moving item to get.
     * @return The moving item DTO with the given name.
     */
    public MovingItemDTO getMovingItemDTOByName(String name) {
        if (!movingItemDTOHashMap.containsKey(name)) {
            throw new NoSuchElementException(
                    "There is no Item with this specific name!");
        }
        return movingItemDTOHashMap.get(name);
    }

    /**
     * @param name The name of the moving item to check.
     * @return The moving item impl with the given name.
     */
    public MovingItemImpl getMovingItemImplByName(String name) {
        if (!movingItemImplHashMap.containsKey(name)) {
            throw new IllegalArgumentException(
                    "movingItemImplHashMap does not contain key " + name);
        }
        return movingItemImplHashMap.get(name);
    }

    /**
     * Get all moving item DTOs.
     *
     * @return The entire moving item DTO hash map.
     */
    public Collection<MovingItemDTO> getAllMovingItems() {
        return this.movingItemDTOHashMap.values();
    }
}
