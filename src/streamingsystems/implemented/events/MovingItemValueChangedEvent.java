package streamingsystems.implemented.events;

import streamingsystems.CommandsModel.Meta.Event;

public class MovingItemValueChangedEvent extends Event {
    String id;
    int newValue;

    @Override
    public void executeEvent() {
        // TODO
    }
}
