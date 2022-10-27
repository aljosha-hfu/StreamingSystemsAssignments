package streamingsystems.QueryHandlingModel.Predefined;

import streamingsystems.implemented.MovingItemDTO;

import java.util.Enumeration;

public interface Query {
    MovingItemDTO getMovingItemByName(String name);
    Enumeration<MovingItemDTO> getMovingItems();
    Enumeration<MovingItemDTO> getMovingItemsAtPosition(int[] position);
}