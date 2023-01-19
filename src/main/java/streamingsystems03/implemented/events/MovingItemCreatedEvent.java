package streamingsystems03.implemented.events;

import streamingsystems03.commandsModel.meta.Event;
import streamingsystems03.implemented.MovingItemImpl;

/**
 * Represents an event that is created when a moving item is created.
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

    /**
     * @return The moving item.
     */
    public MovingItemImpl getMovingItemImpl() {
        return movingItemImpl;
    }


    @Override
    public MovingItemImpl apply() {
        return movingItemImpl;
    }
}
