package streamingsystems.CommandsModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import streamingsystems.Helpers;
import streamingsystems.Main;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class DomainModel {
    private static final DomainModel singletonInstance = new DomainModel();
    private final Logger logger;

    private DomainModel() {
        logger = LoggerFactory.getLogger(DomainModel.class);
        logger.info("DomainModel Instance created.");
    }

    public static DomainModel getInstance() {
        return singletonInstance;
    }

    // Data
    private final HashMap<String, Integer> movingItemsMoveCounts = new HashMap<>();
    private final HashMap<String, int[]> movingItemsPositions = new HashMap<>();

    public void checkMovingItemExistsAndThrowException(String movingItemName) {
        if (!movingItemNameExists(movingItemName)) {
            throw new IllegalArgumentException("A moving item with the name " + movingItemName + " does not exist in the domain model.");
        }
    }

    public int getNumberOfMovesForMovingItemName(String movingItemName) {
        checkMovingItemExistsAndThrowException(movingItemName);

        return movingItemsMoveCounts.get(movingItemName);
    }

    public int[] getPositionForMovingItemName(String movingItemName) {
        checkMovingItemExistsAndThrowException(movingItemName);

        return movingItemsPositions.get(movingItemName);
    }

    public void moveMovingItem(String movingItemName, int[] vector) {
        movingItemsPositions.replace(
                movingItemName,
                Helpers.addArrays(movingItemsPositions.get(movingItemName), vector))
        ;
    }

    public String getItemNameForPosition(int[] positionToFind) {
        Optional<String> foundItemName = movingItemsPositions.entrySet().stream().filter(entry -> Arrays.equals(entry.getValue(), positionToFind))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet())
                .stream()
                .findFirst();

        return foundItemName.orElse(null);
    }

    public void incrementNumberOfMovesForMovingItemNameByOne(String movingItemName) {
        checkMovingItemExistsAndThrowException(movingItemName);

        // https://stackoverflow.com/a/42648785
        movingItemsMoveCounts.merge(movingItemName, 1, Integer::sum);
    }

    public boolean movingItemNameExists(String movingItemName) {
        return movingItemsMoveCounts.containsKey(movingItemName);
    }

    public void addMovingItemNameToModel(String movingItemName) {
        movingItemsMoveCounts.put(movingItemName, 0);
        movingItemsPositions.put(movingItemName, new int[]{0, 0, 0});
    }

    public void removeMovingItemNameFromModel(String movingItemName) {
        checkMovingItemExistsAndThrowException(movingItemName);

        movingItemsMoveCounts.remove(movingItemName);
        movingItemsPositions.remove(movingItemName);
    }
}
