package streamingsystems.CommandsModel.Meta.Predefined;

import streamingsystems.implemented.MovingItemImpl;

/**
 * This class contains all predefined commands that were given in the assignment.
 */
public interface Commands {
    /**
     * Create a moving item.
     *
     * @param movingItem The moving item to create.
     */
    void createItem(MovingItemImpl movingItem);

    void deleteItem(String id);

    void moveItem(String id, int[] vector);

    void changeValue(String id, int newValue);
}