package streamingsystems.QueryHandlingModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import streamingsystems.CommandsModel.EventStore;
import streamingsystems.CommandsModel.Meta.Event;
import streamingsystems.implemented.MovingItemDTO;
import streamingsystems.implemented.MovingItemImpl;

import java.util.Collection;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.concurrent.LinkedBlockingQueue;

public class QueryModel {
    private static QueryModel INSTANCE;

    public static QueryModel getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new QueryModel();
        }
        return INSTANCE;
    }
    final Logger logger = LoggerFactory.getLogger(QueryModel.class);


    private HashMap<String, MovingItemDTO> movingItemDTOHashMap = new HashMap<>();
    private final HashMap<String, MovingItemImpl> movingItemImplHashMap = new HashMap<>();

    private QueryModel() {
        updateEventStore();
    }


    public void updateEventStore() {
        recalculateEventStoreFromEvents(EventStore.getInstance().getEventQueue());
        movingItemDTOHashMap = convertToMovingItemDTOMap(movingItemImplHashMap);
    }

    private HashMap<String, MovingItemDTO> convertToMovingItemDTOMap(HashMap<String, streamingsystems.implemented.MovingItemImpl> movingItemImplHashMap) {
        HashMap<String, MovingItemDTO> movingItemDTOHashMap = new HashMap<>();
        movingItemImplHashMap.forEach((k, v) -> movingItemDTOHashMap.put(k, new MovingItemDTO(v)));
        return movingItemDTOHashMap;
    }


    private void recalculateEventStoreFromEvents(LinkedBlockingQueue<Event> eventQueue) {
        logger.info("Recalculating EventStore ...");
        eventQueue.forEach(event -> {
            logger.info("Event: " + event.getClass().getName() + ": " + event.getId());
            if (event.apply() != null) {
                movingItemImplHashMap.put(event.getId(), event.apply());
            } else {
                movingItemImplHashMap.remove(event.getId());
            }
        });
        movingItemImplHashMap.forEach((k,v) -> logger.info(k + " " + v));
    }


    public MovingItemDTO getMovingItemDTOByName(String name) {
        if (!movingItemDTOHashMap.containsKey(name)) {
            throw new NoSuchElementException("There is no Item with this specific name!");
        }
        return movingItemDTOHashMap.get(name);
    }

    public MovingItemImpl getMovingItemImplByName(String name) {
        return movingItemImplHashMap.get(name);
    }

    public Collection<MovingItemDTO> getAllMovingItems() {
        return this.movingItemDTOHashMap.values();
    }
}
