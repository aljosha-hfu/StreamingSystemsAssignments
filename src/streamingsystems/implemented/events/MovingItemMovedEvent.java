package streamingsystems.implemented.events;

import streamingsystems.CommandsModel.Meta.Event;

public class MovingItemMovedEvent extends Event {
    String id;
    int[] vector;

    @Override
    public void executeEvent() {
        // TODO
    }
}
