package streamingsystems.implemented.events;

import streamingsystems.CommandsModel.Meta.Event;
import streamingsystems.implemented.MovingItemImpl;

import java.util.HashMap;

public class MovingItemCreatedEvent extends Event {

    private final MovingItemImpl movingItemImpl;

    public MovingItemCreatedEvent(MovingItemImpl movingItem) {
        super(movingItem.getName());
        this.movingItemImpl = movingItem;
    }


    @Override
    public MovingItemImpl apply(HashMap<String, MovingItemImpl> movingItemImplHashMap) {
        return movingItemImpl;
    }

}
