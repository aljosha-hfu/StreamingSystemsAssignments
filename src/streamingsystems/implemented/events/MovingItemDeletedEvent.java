package streamingsystems.implemented.events;

import streamingsystems.CommandsModel.Meta.Event;

public class MovingItemDeletedEvent extends Event {
    String id;

    public MovingItemDeletedEvent(String id) {
        this.id = id;
    }

    @Override
    public void executeEvent() {
        // TODO
    }
}
