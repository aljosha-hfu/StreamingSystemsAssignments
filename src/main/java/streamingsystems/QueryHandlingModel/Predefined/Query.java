package streamingsystems.QueryHandlingModel.Predefined;

import streamingsystems.implemented.MovingItemDTO;

import java.util.Enumeration;

/**
 * The queries that were given as part of the assignment.
 */
public interface Query {
    MovingItemDTO getMovingItemByName(String name);

    Enumeration<MovingItemDTO> getMovingItems();

    Enumeration<MovingItemDTO> getMovingItemsAtPosition(int[] position);
}