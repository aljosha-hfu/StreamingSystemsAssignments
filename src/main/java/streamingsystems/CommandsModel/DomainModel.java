package streamingsystems.CommandsModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import streamingsystems.Helpers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The domain model that handles correction checks.
 */
public class DomainModel {

    private static final DomainModel singletonInstance = new DomainModel();
    private final Logger logger;

    private DomainModel() {
        logger = LoggerFactory.getLogger(DomainModel.class);
        logger.info("DomainModel Instance created.");
    }

    /**
     * @return The singleton instance of the domain model.
     */
    public static DomainModel getInstance() {
        return singletonInstance;
    }

    // Data
    private final HashMap<String, Integer> movingItemsMoveCounts = new HashMap<>();
    private final HashMap<String, int[]> movingItemsPositions = new HashMap<>();

    /**
     * Checks if the moving item exists and throws an exception if it does not.
     *
     * @param movingItemName The name of the moving item.
     */
    public void checkMovingItemExistsAndThrowException(String movingItemName) {
        if (!movingItemNameExists(movingItemName)) {
            throw new IllegalArgumentException(
                    "A moving item with the name " + movingItemName + " does not exist in the domain model.");
        }
    }

    /**
     * @param movingItemName The name of the moving item.
     * @return The number of moves the moving item has made.
     */
    public int getNumberOfMovesForMovingItemName(String movingItemName) {
        checkMovingItemExistsAndThrowException(movingItemName);

        return movingItemsMoveCounts.get(movingItemName);
    }

    /**
     * @param movingItemName The name of the moving item.
     * @return The position of the moving item.
     */
    public int[] getPositionForMovingItemName(String movingItemName) {
        checkMovingItemExistsAndThrowException(movingItemName);

        return movingItemsPositions.get(movingItemName);
    }

    /**
     * @param movingItemName The name of the moving item.
     * @param vector         The vector to move the moving item by.
     */
    public void moveMovingItem(String movingItemName, int[] vector) {
        movingItemsPositions.replace(movingItemName,
                Helpers.addArrays(movingItemsPositions.get(movingItemName),
                        vector));
    }

    /**
     * @param position The position to check.
     * @return True if a moving item exists at the position, false otherwise.
     */
    public boolean itemExistsOnPosition(int[] position) {
        // Using stream and filter instead of HashMap.containsValue() because the values are int[] and they cannot be compared this way
        long numberOfItemsAtPosition = movingItemsPositions.values().stream()
                .filter(pos -> Arrays.equals(pos, position)).count();
        return numberOfItemsAtPosition > 0;
    }

    /**
     * @param positionToFind The position to check.
     * @return The name of the moving item at the position.
     */
    public String getItemNameForPosition(int[] positionToFind) {
        Optional<String> foundItemName = movingItemsPositions.entrySet()
                .stream().filter(entry -> Arrays.equals(entry.getValue(),
                        positionToFind)).map(Map.Entry::getKey)
                .collect(Collectors.toSet()).stream().findFirst();
        return foundItemName.orElse(null);
    }

    /**
     * Increment the number of moves for the moving item by one.
     *
     * @param movingItemName The name of the moving item.
     */
    public void incrementNumberOfMovesForMovingItemNameByOne(
            String movingItemName) {
        checkMovingItemExistsAndThrowException(movingItemName);

        // https://stackoverflow.com/a/42648785
        movingItemsMoveCounts.merge(movingItemName, 1, Integer::sum);
    }

    /**
     * @param movingItemName The name of the moving item.
     * @return True if the moving item name exists, false otherwise.
     */
    public boolean movingItemNameExists(String movingItemName) {
        return movingItemsMoveCounts.containsKey(movingItemName);
    }

    /**
     * Add a moving item name to the domain model.
     *
     * @param movingItemName The name of the moving item to add.
     */
    public void addMovingItemNameToModel(String movingItemName) {
        movingItemsMoveCounts.put(movingItemName, 0);
        movingItemsPositions.put(movingItemName, new int[]{0, 0, 0});
    }

    /**
     * Remove a moving item name from the domain model.
     *
     * @param movingItemName The name of the moving item to remove.
     */
    public void removeMovingItemNameFromModel(String movingItemName) {
        checkMovingItemExistsAndThrowException(movingItemName);

        movingItemsMoveCounts.remove(movingItemName);
        movingItemsPositions.remove(movingItemName);
    }

    /**
     * @param id The id of the moving item.
     * @return If the moving item has reached the maximum number of moves.
     */
    public boolean itemHasReachedMaximumMoves(String id) {
        int maximumMoves = 20;
        return getNumberOfMovesForMovingItemName(id) >= maximumMoves - 1;
    }
}
