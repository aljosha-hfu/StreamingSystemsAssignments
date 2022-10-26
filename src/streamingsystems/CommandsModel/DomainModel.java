package streamingsystems.CommandsModel;

import java.util.ArrayList;

public class DomainModel {
    private static final DomainModel singletonInstance = new DomainModel();

    private final ArrayList<String> movingItemNameList = new ArrayList<>();

    private DomainModel() {
        System.out.println("DomainModel Instance created.");
    }

    public static DomainModel getInstance() {
        return singletonInstance;
    }


    public boolean movingItemNameExists(String movingItemName) {
        return movingItemNameList.contains(movingItemName);
    }

    public void addMovingItemNameToModel(String movingItemName) {
        movingItemNameList.add(movingItemName);
    }

    public void removeMovingItemNameFromModel(String movingItemName) {
        if (!movingItemNameExists(movingItemName)) {
            throw new IllegalArgumentException("A moving item with the name " + movingItemName + " does not exist in the domain model.");
        }

        this.movingItemNameList.remove(movingItemName);
    }
}
