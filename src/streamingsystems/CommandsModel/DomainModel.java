package streamingsystems.CommandsModel;

import streamingsystems.MovingItem;

import java.util.ArrayList;
import java.util.List;

public class DomainModel {
    private static final DomainModel singletonInstance = new DomainModel();

    private final ArrayList<String> movingItemList = new ArrayList<>();

    private DomainModel() {
        System.out.println("DomainModel Instance created.");
    }

    public static DomainModel getInstance() {
        return singletonInstance;
    }


    public boolean movingItemNameExists(String movingItemName) {
        return movingItemList.contains(movingItemName);
    }

    public void addMovingItemNameToModel(String movingItemName) {
        movingItemList.add(movingItemName);
    }

    public void removeMovingItemNameFromModel(String movingItemName) {
        if (!movingItemNameExists(movingItemName)) {
            throw new IllegalArgumentException("A moving item with the name " + movingItemName + " does not exist in the domain model.");
        }

        this.movingItemList.remove(movingItemName);
    }
}
