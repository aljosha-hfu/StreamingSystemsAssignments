package streamingsystems.implemented.events;

import streamingsystems.commandsModel.meta.Event;
import streamingsystems.MovingItemListTools;
import streamingsystems.implemented.MovingItemImpl;

import java.util.HashMap;

/**
 * Represents a value change of a moving item.
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


    @Override
    public MovingItemImpl apply(
            HashMap<String, MovingItemImpl> movingItemImplHashMap) {
        MovingItemImpl movingItem = MovingItemListTools.getSingletonInstance()
                .getMovingItemImplByName(id, movingItemImplHashMap);
        movingItem.setValue(newValue);
        return movingItem;
    }
}
