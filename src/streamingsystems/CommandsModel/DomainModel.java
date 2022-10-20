package streamingsystems.CommandsModel;

import streamingsystems.MovingItem;

import java.util.List;

public class DomainModel {
    private static final DomainModel singletonInstance = new DomainModel();

    private List<String> movingItemList = new List<>;

    public boolean movingItemExists(String movingItemName) {
        return movingItemHashMap.containsKey(movingItemName);
    }

    public void addMovingItemToModel(MovingItem movingItemToAdd) {
        movingItemHashMap.put(movingItemToAdd.getName(), movingItemToAdd);
    }

    private DomainModel() {
        System.out.println("DomainModel Instance created.");
    }

    public static DomainModel getInstance() {
        return singletonInstance;
    }
}
