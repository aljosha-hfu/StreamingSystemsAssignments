package streamingsystems02.implemented.events;

import streamingsystems02.commandsmodel.meta.Event;
import streamingsystems02.queryhandlingmodel.QueryModel;
import streamingsystems02.implemented.MovingItemImpl;

/**
 * Represents an event that is created when a moving item is moved.
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

    /**
     * @return The vector to move the moving item with.
     */
    public int[] getVector() {
        return vector;
    }

    @Override
    public MovingItemImpl apply() {
        MovingItemImpl movingItem = QueryModel.getInstance()
                .getMovingItemImplByName(id);
        movingItem.addMoveToMoveCounter();
        movingItem.move(vector);
        return movingItem;
    }
}
