package streamingsystems.commandsModel.meta;

import streamingsystems.implemented.MovingItemImpl;

import java.io.Serializable;

/**
 * Representation of an event.
 */
public abstract class Event implements Serializable {
    protected final String id;

    /**
     * @param id The id of the moving item.
     */
    public Event(String id) {
        this.id = id;
    }

    /**
     * @return The id of the moving item.
     */
    public String getId() {
        return id;
    }

    /**
     * Handle the event
     *
     * @return The moving item.
     */
    public abstract MovingItemImpl apply();
}
