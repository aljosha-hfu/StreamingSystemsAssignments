package streamingsystems04;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import streamingsystems04.commandsmodel.meta.Event;
import streamingsystems04.implemented.MovingItemImpl;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * This class contains the tools to work with the moving item list.
 */
public class MovingItemListTools {
    private static final MovingItemListTools singletonInstance = new MovingItemListTools();
    private final Logger logger;

    private MovingItemListTools() {
        logger = LoggerFactory.getLogger(MovingItemListTools.class);
    }

    /**
     * @return The singleton instance of this class.
     */
    public static MovingItemListTools getSingletonInstance() {
        return singletonInstance;
    }

    /**
     * @param eventLinkedList The list of events to use.
     * @return A HashMap with all moving items that are in the event list.
     */
    public HashMap<String, MovingItemImpl> createMovingItemList(
            LinkedList<Event> eventLinkedList) {
        HashMap<String, MovingItemImpl> movingItemImplHashMap = new HashMap<>();
        logger.info("Recalculating EventStore ...");
        eventLinkedList.forEach(event -> {
            logger.info("Event: " + event.getClass()
                    .getName() + ": " + event.getId());
            MovingItemImpl applyReturnValue = event.apply(
                    movingItemImplHashMap);
            if (applyReturnValue != null) {
                movingItemImplHashMap.put(event.getId(), applyReturnValue);
            } else {
                movingItemImplHashMap.remove(event.getId());
            }
        });
        //movingItemImplHashMap.forEach((k, v) -> logger.info(k + " " + v));
        return movingItemImplHashMap;
    }

    /**
     * @param name                  The name of the moving item.
     * @param movingItemImplHashMap The moving item list to search in.
     * @return The moving item with the given name as found in the moving item list.
     */
    public MovingItemImpl getMovingItemImplByName(String name,
                                                  HashMap<String, MovingItemImpl> movingItemImplHashMap) {
        if (!movingItemImplHashMap.containsKey(name)) {
            throw new IllegalArgumentException(
                    "movingItemImplHashMap does not contain key " + name);
        }
        return movingItemImplHashMap.get(name);
    }

}
