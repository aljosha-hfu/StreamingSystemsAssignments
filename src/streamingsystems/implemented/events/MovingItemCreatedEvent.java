package streamingsystems.implemented.events;

import streamingsystems.CommandsModel.Meta.Event;
import streamingsystems.MovingItem;

public class MovingItemCreatedEvent extends Event {

    MovingItem movingItem;

    @Override
    public void executeEvent() {
        // TODO
    }
}
