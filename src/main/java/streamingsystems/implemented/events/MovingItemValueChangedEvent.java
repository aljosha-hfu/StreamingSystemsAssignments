package streamingsystems.implemented.events;

import streamingsystems.CommandsModel.Meta.Event;

public class MovingItemValueChangedEvent extends Event {
    private final int newValue;

    public MovingItemValueChangedEvent(String id, int newValue) {
        super(id);
        this.newValue = newValue;
    }


    public int getNewValue() {
        return newValue;
    }
}
