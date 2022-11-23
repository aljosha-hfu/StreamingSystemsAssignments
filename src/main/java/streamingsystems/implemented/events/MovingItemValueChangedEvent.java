package streamingsystems.implemented.events;

import streamingsystems.CommandsModel.Meta.Event;
import streamingsystems.QueryHandlingModel.QueryModel;
import streamingsystems.implemented.MovingItemImpl;

public class MovingItemValueChangedEvent extends Event {
    private final int newValue;

    public MovingItemValueChangedEvent(String id, int newValue) {
        super(id);
        this.newValue = newValue;
    }


    public int getNewValue() {
        return newValue;
    }

    @Override
    public MovingItemImpl apply() {
        MovingItemImpl movingItem = QueryModel.getInstance().getMovingItemImplByName(id);
        movingItem.setValue(newValue);
        return movingItem;
    }
}
