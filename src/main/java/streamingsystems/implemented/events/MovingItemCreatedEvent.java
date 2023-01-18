package streamingsystems.implemented.events;

import streamingsystems.commandsModel.meta.Event;
import streamingsystems.implemented.MovingItemImpl;

import java.util.HashMap;

/**
 * Represents the creation of a moving item.
 */
public class MovingItemCreatedEvent extends Event {

    private final MovingItemImpl movingItemImpl;

    /**
     * @param movingItem The moving item that is created.
     */
    public MovingItemCreatedEvent(MovingItemImpl movingItem) {
        super(movingItem.getName());
        this.movingItemImpl = movingItem;
    }


    @Override
    public MovingItemImpl apply(
            HashMap<String, MovingItemImpl> movingItemImplHashMap) {
        return movingItemImpl;
    }

}
