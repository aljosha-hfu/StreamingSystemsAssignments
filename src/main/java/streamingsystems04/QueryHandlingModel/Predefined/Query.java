package streamingsystems04.QueryHandlingModel.Predefined;

import streamingsystems04.implemented.MovingItemDTO;

import java.util.Enumeration;

/**
 * The queries that were given as part of the assignment.
 */
public interface Query {
    /**
     * Finds a moving item by its name.
     * @param name The name of the moving item.
     * @return The moving item with the given name.
     */
    MovingItemDTO getMovingItemByName(String name);

    /**
     * @return All moving items.
     */
    Enumeration<MovingItemDTO> getMovingItems();

    /**
     * Get the moving items that are at the given position.
     * @param position The position to check.
     * @return All moving items that are at the given position.
     */
    Enumeration<MovingItemDTO> getMovingItemsAtPosition(int[] position);
}