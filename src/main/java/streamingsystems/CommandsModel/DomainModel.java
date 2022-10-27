package streamingsystems.CommandsModel;

import java.util.ArrayList;
import java.util.HashMap;

public class DomainModel {
    private static final DomainModel singletonInstance = new DomainModel();

    private DomainModel() {
        System.out.println("DomainModel Instance created.");
    }

    public static DomainModel getInstance() {
        return singletonInstance;
    }

    // Data
    private final ArrayList<String> movingItemNameList = new ArrayList<>();
    private final HashMap<String, Integer> movingItemsMoveCounts = new HashMap<>();

    public int getNumberOfMovesForMovingItemName(String movingItemName) {
        if (!movingItemNameExists(movingItemName)) {
            throw new IllegalArgumentException("A moving item with the name " + movingItemName + " does not exist in the domain model.");
        }

        return movingItemsMoveCounts.get(movingItemName);
    }

    public int incrementNumberOfMovesForMovingItemNameByOne(String movingItemName) {
        if (!movingItemNameExists(movingItemName)) {
            throw new IllegalArgumentException("A moving item with the name " + movingItemName + " does not exist in the domain model.");
        }

        // https://stackoverflow.com/a/42648785
        return movingItemsMoveCounts.merge(movingItemName, 1, Integer::sum);
    }

    public boolean movingItemNameExists(String movingItemName) {
        return movingItemNameList.contains(movingItemName);
    }

    public void addMovingItemNameToModel(String movingItemName) {
        movingItemNameList.add(movingItemName);
        movingItemsMoveCounts.put(movingItemName, 0);
    }

    public void removeMovingItemNameFromModel(String movingItemName) {
        if (!movingItemNameExists(movingItemName)) {
            throw new IllegalArgumentException("A moving item with the name " + movingItemName + " does not exist in the domain model.");
        }

        movingItemNameList.remove(movingItemName);
        movingItemsMoveCounts.remove(movingItemName);
    }
}
