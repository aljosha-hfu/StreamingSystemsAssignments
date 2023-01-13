package streamingsystems.QueryHandlingModel.Predefined;

import streamingsystems.implemented.MovingItemDTO;

import java.util.Enumeration;

/**
 * The queries that were given as part of the assignment.
 */
public interface Query {
    /**
     * Gets a moving item by its name.
     *
     * @param name The name of the moving item.
     * @return The moving item with the given name.
     */
    public MovingItemDTO getMovingItemByName(String name);

    /**
     * Gets all moving items.
     *
     * @return All moving items.
     */
    public Enumeration<MovingItemDTO> getMovingItems();

    /**
     * Gets all moving items at a given position.
     *
     * @param position The position to get the moving items at.
     * @return All moving items at the given position.
     */
    public Enumeration<MovingItemDTO> getMovingItemsAtPosition(int[] position);
}