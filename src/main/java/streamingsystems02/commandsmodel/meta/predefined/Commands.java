package streamingsystems02.commandsmodel.meta.predefined;

import streamingsystems02.implemented.MovingItemImpl;

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

    /**
     * Move a moving item.
     *
     * @param id The id of the moving item.
     */
    void deleteItem(String id);

    /**
     * Move a moving item.
     *
     * @param id     The id of the moving item.
     * @param vector The vector to apply to the moving item.
     */
    void moveItem(String id, int[] vector);

    /**
     * Change the value of a moving item.
     *
     * @param id       The id of the moving item.
     * @param newValue The new value to set.
     */
    void changeValue(String id, int newValue);
}