package streamingsystems.implemented.events;

import streamingsystems.CommandsModel.Meta.Event;

public class MovingItemDeletedEvent extends Event {
    String id;

    public MovingItemDeletedEvent(String id) {
        super(id);
        this.id = id;
    }
}
