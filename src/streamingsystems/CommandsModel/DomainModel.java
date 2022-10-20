package streamingsystems.CommandsModel;

import streamingsystems.MovingItem;

import java.util.ArrayList;
import java.util.List;

public class DomainModel {
    private static final DomainModel singletonInstance = new DomainModel();

    private final ArrayList<String> movingItemList = new ArrayList<>();

    public boolean movingItemNameExists(String movingItemName) {
        return movingItemList.contains(movingItemName);
    }

    public void addMovingItemNameToModel(String movingItemName) {
        movingItemList.add(movingItemName);
    }

    private DomainModel() {
        System.out.println("DomainModel Instance created.");
    }

    public static DomainModel getInstance() {
        return singletonInstance;
    }
}
