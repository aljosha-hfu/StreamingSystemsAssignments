package streamingsystems01.commandsmodel.meta;

import streamingsystems01.queryhandlingmodel.QueryModel;
import streamingsystems01.implemented.MovingItemImpl;

/**
 * Represents an event that can be handled by the event handler.
 */
public abstract class Event {
    private final QueryModel queryModel = QueryModel.getInstance();
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
