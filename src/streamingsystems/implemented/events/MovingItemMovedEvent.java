package streamingsystems.implemented.events;

import streamingsystems.CommandsModel.Meta.Event;

public class MovingItemMovedEvent extends Event {
    String id;
    int[] vector;

    public MovingItemMovedEvent(String id, int[] vector) {
        this.id = id;
        this.vector = vector;
    }

    @Override
    public void executeEvent() {
        // TODO
    }
}
