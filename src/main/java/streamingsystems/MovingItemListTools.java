package streamingsystems;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import streamingsystems.CommandsModel.Meta.Event;
import streamingsystems.implemented.MovingItemImpl;

import java.util.HashMap;
import java.util.LinkedList;

public class MovingItemListTools {
    private final Logger logger;
    private static final MovingItemListTools singletonInstance = new MovingItemListTools();
    private MovingItemListTools(){
        logger = LoggerFactory.getLogger(MovingItemListTools.class);
    }

    public static MovingItemListTools getSingletonInstance(){
        return singletonInstance;
    }

    public HashMap<String, MovingItemImpl>  createMovingItemList(LinkedList<Event> eventLinkedList) {
        HashMap<String, MovingItemImpl> movingItemImplHashMap = new HashMap<>();
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
        //movingItemImplHashMap.forEach((k, v) -> logger.info(k + " " + v));
        return movingItemImplHashMap;
    }

    public MovingItemImpl getMovingItemImplByName(String name, HashMap<String, MovingItemImpl> movingItemImplHashMap) {
        if (!movingItemImplHashMap.containsKey(name)) {
            throw new IllegalArgumentException("movingItemImplHashMap does not contain key " + name);
        }
        return movingItemImplHashMap.get(name);
    }

}
