package streamingsystems02.implemented.events;

import streamingsystems02.commandsModel.meta.Event;
import streamingsystems02.implemented.MovingItemImpl;

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

    @Override
    public MovingItemImpl apply() {
        return null;
    }
}
