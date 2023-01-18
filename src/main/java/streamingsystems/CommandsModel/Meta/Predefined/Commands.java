package streamingsystems.commandsModel.meta.predefined;

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

    /**
     * @param id The id of the moving item to delete.
     */
    void deleteItem(String id);

    /**
     * @param id     The id of the moving item.
     * @param vector The vector to add to the moving item's vector.
     **/

    void moveItem(String id, int[] vector);

    /**
     * @param id       The id of the moving item.
     * @param newValue The new value to set.
     */
    void changeValue(String id, int newValue);
}