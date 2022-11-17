package streamingsystems.QueryHandlingModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import streamingsystems.CommandsModel.Meta.Event;
import streamingsystems.implemented.MovingItemDTO;
import streamingsystems.implemented.MovingItemImpl;

import java.util.*;

public class QueryModel {


    private static  QueryModel singletonInstance;

    private QueryModel() {
        System.out.println("Instantiated QueryModel singleton...");
    }

    public static QueryModel getInstance() {
        if(singletonInstance == null){
            singletonInstance = new QueryModel();
            singletonInstance.updateEventStore();

        }
        return singletonInstance;
    }


    final Logger logger = LoggerFactory.getLogger(QueryModel.class);

    private HashMap<String, MovingItemDTO> movingItemDTOHashMap = new HashMap<>();
    private final HashMap<String, MovingItemImpl> movingItemImplHashMap = new HashMap<>();


    public void updateEventStore() {
        System.out.println("Updating event store...");
//        Channel channel = RabbitMQConnectionManager.getInstance().getEventStoreChannel();

        LinkedList<Event> eventList = new LinkedList<>();


//        GetResponse response = null;
//        do {
//            try {
//                response = channel.basicGet(RabbitMQConnectionManager.QUEUE_NAME, true);
//                if (response != null) {
//                    AMQP.BasicProperties props = response.getProps();
//                    byte[] body = response.getBody();
//
//                    System.out.println("New event from RabbitMQ:");
//                    System.out.println(Arrays.toString(body));
//
//                    Event deserializedData = SerializationUtils.deserialize(body);
//                    eventList.add(deserializedData);
//                }
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        } while (response != null);


        recalculateEventStoreFromEvents(eventList);
        movingItemDTOHashMap = convertToMovingItemDTOMap(movingItemImplHashMap);
    }

    private HashMap<String, MovingItemDTO> convertToMovingItemDTOMap(HashMap<String, streamingsystems.implemented.MovingItemImpl> movingItemImplHashMap) {
        HashMap<String, MovingItemDTO> movingItemDTOHashMap = new HashMap<>();
        movingItemImplHashMap.forEach((k, v) -> movingItemDTOHashMap.put(k, new MovingItemDTO(v)));
        return movingItemDTOHashMap;
    }


    private void recalculateEventStoreFromEvents(LinkedList<Event> eventLinkedList) {
        logger.info("Recalculating EventStore ...");
        eventLinkedList.forEach(event -> {
            logger.info("Event: " + event.getClass().getName() + ": " + event.getId());
            MovingItemImpl applyReturnValue = event.apply();
            if (applyReturnValue != null) {
                movingItemImplHashMap.put(event.getId(), applyReturnValue);
            } else {
                movingItemImplHashMap.remove(event.getId());
            }
        });
        movingItemImplHashMap.forEach((k, v) -> logger.info(k + " " + v));
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
