package streamingsystems02.queryhandlingmodel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import streamingsystems02.commandsmodel.EventStore;
import streamingsystems02.commandsmodel.meta.Event;
import streamingsystems02.implemented.MovingItemDTO;
import streamingsystems02.implemented.MovingItemImpl;

import java.util.Collection;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * The query model that gets generated from the list of events.
 */
public class QueryModel {
    private static QueryModel INSTANCE;

    /**
     * @return The singleton instance of the query model.
     */
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
        updateQueryModel();
    }


    /**
     * Update the query model from the event store.
     */
    public void updateQueryModel() {
        recalculateQueryModelFromEvents(
                EventStore.getInstance().getEventQueue());
        movingItemDTOHashMap = convertToMovingItemDTOMap(movingItemImplHashMap);
    }

    private HashMap<String, MovingItemDTO> convertToMovingItemDTOMap(
            HashMap<String, streamingsystems02.implemented.MovingItemImpl> movingItemImplHashMap) {
        HashMap<String, MovingItemDTO> movingItemDTOHashMap = new HashMap<>();
        movingItemImplHashMap.forEach(
                (k, v) -> movingItemDTOHashMap.put(k, new MovingItemDTO(v)));
        return movingItemDTOHashMap;
    }


    private void recalculateQueryModelFromEvents(
            LinkedBlockingQueue<Event> eventQueue) {
        logger.info("Recalculating EventStore ...");
        eventQueue.forEach(event -> {
            logger.info("Event: " + event.getClass()
                    .getName() + ": " + event.getId());
            if (event.apply() != null) {
                movingItemImplHashMap.put(event.getId(), event.apply());
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
     * @param name The name of the moving item.
     * @return The moving item implementation with the given name.
     */
    public MovingItemImpl getMovingItemImplByName(String name) {
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
