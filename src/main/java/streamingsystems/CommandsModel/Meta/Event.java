package streamingsystems.CommandsModel.Meta;

import streamingsystems.implemented.MovingItemImpl;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Represents a change of the state of the system.
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
     * @param movingItemImplHashMap The moving item list to apply the event to.
     */
    public abstract MovingItemImpl apply(
            HashMap<String, MovingItemImpl> movingItemImplHashMap);
}
