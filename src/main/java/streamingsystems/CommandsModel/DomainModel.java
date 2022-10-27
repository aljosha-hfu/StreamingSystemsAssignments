package streamingsystems.CommandsModel;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class DomainModel {
    private static final DomainModel singletonInstance = new DomainModel();

    private DomainModel() {
        System.out.println("DomainModel Instance created.");
    }

    public static DomainModel getInstance() {
        return singletonInstance;
    }

    // Data
    private final HashMap<String, Integer> movingItemsMoveCounts = new HashMap<>();
    private final HashMap<int[], String> movingItemsPositions = new HashMap<>();

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

        Optional<int[]> foundPosition = movingItemsPositions.entrySet().stream().filter(entry -> Objects.equals(entry.getValue(), movingItemName))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet())
                .stream()
                .findFirst();

        return foundPosition.orElse(null);
    }

    public String findItemWithPosition(int[] positionToFind) {
        return movingItemsPositions.get(positionToFind);
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
        movingItemsPositions.put(new int[]{0, 0, 0}, movingItemName);
    }

    public void removeMovingItemNameFromModel(String movingItemName) {
        checkMovingItemExistsAndThrowException(movingItemName);

        movingItemsMoveCounts.remove(movingItemName);
        movingItemsPositions.remove(movingItemName);
    }
}
