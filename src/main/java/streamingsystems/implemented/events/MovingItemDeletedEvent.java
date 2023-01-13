package streamingsystems.implemented.events;

import streamingsystems.CommandsModel.Meta.Event;
import streamingsystems.implemented.MovingItemImpl;

import java.util.HashMap;

public class MovingItemDeletedEvent extends Event {
    /**
     * @param id The id of the moving item.
     */
    public MovingItemDeletedEvent(String id) {
        super(id);
    }

    @Override
    public MovingItemImpl apply(HashMap<String, MovingItemImpl> movingItemImplHashMap) {
        return null;
    }
}
