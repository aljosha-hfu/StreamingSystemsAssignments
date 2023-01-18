package streamingsystems.commandsModel.events;

import streamingsystems.commandsModel.meta.Event;
import streamingsystems.queryHandlingModel.QueryModel;
import streamingsystems.implemented.MovingItemImpl;

/**
 * Represents an event that is created when a moving item is moved.
 */
public class MovingItemValueChangedEvent extends Event {
    private final int newValue;

    /**
     * @param id       The id of the moving item.
     * @param newValue The new value of the moving item.
     */
    public MovingItemValueChangedEvent(String id, int newValue) {
        super(id);
        this.newValue = newValue;
    }


    /**
     * @return The new value of the moving item.
     */
    public int getNewValue() {
        return newValue;
    }

    @Override
    public MovingItemImpl apply() {
        MovingItemImpl movingItem = QueryModel.getInstance()
                .getMovingItemImplByName(id);
        movingItem.setValue(newValue);
        return movingItem;
    }
}
