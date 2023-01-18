package streamingsystems.implemented.events;

import streamingsystems.commandsModel.meta.Event;
import streamingsystems.implemented.MovingItemImpl;

/**
 * Represents an event that is created when a moving item is deleted.
 */
public class MovingItemDeletedEvent extends Event {
    /**
     * @param id The id of the moving item.
     */
    public MovingItemDeletedEvent(String id) {
        super(id);
    }

    @SuppressWarnings("SameReturnValue")
    @Override
    public MovingItemImpl apply() {
        return null;
    }
}
