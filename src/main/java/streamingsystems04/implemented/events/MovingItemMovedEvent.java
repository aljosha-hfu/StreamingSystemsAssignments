package streamingsystems04.implemented.events;

import streamingsystems04.commandsmodel.meta.Event;
import streamingsystems04.MovingItemListTools;
import streamingsystems04.implemented.MovingItemImpl;

import java.util.HashMap;

/**
 * Represents the moving of a moving item.
 */
public class MovingItemMovedEvent extends Event {
    private final int[] vector;

    /**
     * @param id     The id of the moving item.
     * @param vector The vector to move the moving item with.
     */
    public MovingItemMovedEvent(String id, int[] vector) {
        super(id);
        this.vector = vector;
    }

    @Override
    public MovingItemImpl apply(
            HashMap<String, MovingItemImpl> movingItemImplHashMap) {
        MovingItemImpl movingItem = MovingItemListTools.getSingletonInstance()
                .getMovingItemImplByName(id, movingItemImplHashMap);
        movingItem.addMoveToMoveCounter();
        movingItem.move(vector);
        return movingItem;
    }
}
