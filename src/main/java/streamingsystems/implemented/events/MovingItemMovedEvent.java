package streamingsystems.implemented.events;

import streamingsystems.CommandsModel.Meta.Event;

public class MovingItemMovedEvent extends Event {
    private final int[] vector;

    public MovingItemMovedEvent(String id, int[] vector) {
        super(id);
        this.vector = vector;
    }

    public int[] getVector() {
        return vector;
    }
}
