package streamingsystems.implemented.events;

import streamingsystems.CommandsModel.Meta.Event;
import streamingsystems.MovingItem;

public class MovingItemCreatedEvent extends Event {

    MovingItem movingItem;

    public MovingItemCreatedEvent(MovingItem movingItem) {
        this.movingItem = movingItem;
    }
}
