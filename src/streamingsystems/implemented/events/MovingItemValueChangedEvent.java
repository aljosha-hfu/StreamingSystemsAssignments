package streamingsystems.implemented.events;

import streamingsystems.CommandsModel.Meta.Event;

public class MovingItemValueChangedEvent extends Event {
    String id;
    int newValue;

    public MovingItemValueChangedEvent(String id, int newValue) {
        this.id = id;
        this.newValue = newValue;
    }

    @Override
    public void executeEvent() {
        // TODO
    }
}
