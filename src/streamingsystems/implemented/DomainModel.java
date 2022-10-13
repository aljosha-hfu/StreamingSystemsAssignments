package streamingsystems.implemented;

import streamingsystems.MovingItem;

import java.util.HashMap;

public class DomainModel {
    private static final DomainModel singletonInstance = new DomainModel();

    private HashMap<String, MovingItem> movingItemHashMap = new HashMap<>();

    public boolean movingItemExists(MovingItem movingItemName) {
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
