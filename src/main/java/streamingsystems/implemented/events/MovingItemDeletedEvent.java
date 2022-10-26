package streamingsystems.implemented.events;

import streamingsystems.CommandsModel.Meta.Event;

public class MovingItemDeletedEvent extends Event {
    public MovingItemDeletedEvent(String id) {
        super(id);
    }
}
