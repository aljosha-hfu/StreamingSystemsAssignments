package streamingsystems.implemented.events;

import streamingsystems.CommandsModel.Meta.Event;
import streamingsystems.implemented.MovingItemImpl;

public class MovingItemDeletedEvent extends Event {
    public MovingItemDeletedEvent(String id) {
        super(id);
    }

    @Override
    public MovingItemImpl apply() {
        return null;
    }
}
