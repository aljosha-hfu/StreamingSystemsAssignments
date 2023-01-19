package streamingsystems01.commandsmodel;

import java.util.ArrayList;

/**
 * Contains a seperate model with things like existing moving item names, etc.
 */
public class DomainModel {
    private static final DomainModel singletonInstance = new DomainModel();

    private DomainModel() {
        System.out.println("DomainModel Instance created.");
    }

    /**
     * @return The singleton instance of the domain model.
     */
    public static DomainModel getInstance() {
        return singletonInstance;
    }

    private final ArrayList<String> movingItemNameList = new ArrayList<>();

    /**
     * Check if a moving item with a given name exists in the domain model.
     *
     * @param movingItemName The name of the moving item.
     * @return True if the moving item name exists, false otherwise.
     */
    public boolean movingItemNameExists(String movingItemName) {
        return movingItemNameList.contains(movingItemName);
    }

    /**
     * Add a moving item name to the domain model.
     *
     * @param movingItemName The name of the moving item to add.
     */
    public void addMovingItemNameToModel(String movingItemName) {
        movingItemNameList.add(movingItemName);
    }

    /**
     * Remove a moving item name from the domain model.
     *
     * @param movingItemName The name of the moving item to remove.
     */
    public void removeMovingItemNameFromModel(String movingItemName) {
        if (!movingItemNameExists(movingItemName)) {
            throw new IllegalArgumentException(
                    "A moving item with the name " + movingItemName + " does not exist in the domain model.");
        }

        this.movingItemNameList.remove(movingItemName);
    }
}
