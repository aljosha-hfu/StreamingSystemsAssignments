package streamingsystems.implemented.events;

import streamingsystems.CommandsModel.Meta.Event;
import streamingsystems.MovingItemListTools;
import streamingsystems.QueryHandlingModel.QueryModel;
import streamingsystems.implemented.MovingItemImpl;

import java.util.HashMap;

public class MovingItemValueChangedEvent extends Event {
    private final int newValue;

    public MovingItemValueChangedEvent(String id, int newValue) {
        super(id);
        this.newValue = newValue;
    }


    @Override
    public MovingItemImpl apply(HashMap<String, MovingItemImpl> movingItemImplHashMap) {
        MovingItemImpl movingItem = MovingItemListTools.getSingletonInstance().getMovingItemImplByName(id, movingItemImplHashMap);
        movingItem.setValue(newValue);
        return movingItem;
    }
}
