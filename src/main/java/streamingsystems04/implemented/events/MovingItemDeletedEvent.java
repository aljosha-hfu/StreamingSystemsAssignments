package streamingsystems04.implemented.events;

import streamingsystems04.commandsmodel.meta.Event;
import streamingsystems04.implemented.MovingItemImpl;

import java.util.HashMap;

/**
 * Represents the deletion of a moving item.
 */
public class MovingItemDeletedEvent extends Event {
    /**
     * @param id The id of the moving item.
     */
    public MovingItemDeletedEvent(String id) {
        super(id);
    }

    @Override
    public MovingItemImpl apply(
            HashMap<String, MovingItemImpl> movingItemImplHashMap) {
        return null;
    }
}
