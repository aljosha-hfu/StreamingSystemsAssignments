package streamingsystems.implemented.events;

import streamingsystems.CommandsModel.Meta.Event;
import streamingsystems.MovingItemListTools;
import streamingsystems.QueryHandlingModel.QueryModel;
import streamingsystems.implemented.MovingItemImpl;

import java.util.HashMap;

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
    public MovingItemImpl apply(HashMap<String, MovingItemImpl> movingItemImplHashMap) {
        MovingItemImpl movingItem = MovingItemListTools.getSingletonInstance().getMovingItemImplByName(id, movingItemImplHashMap);
        movingItem.addMoveToMoveCounter();
        movingItem.move(vector);
        return movingItem;
    }
}
