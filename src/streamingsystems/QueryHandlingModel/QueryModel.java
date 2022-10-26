package streamingsystems.QueryHandlingModel;

import streamingsystems.MovingItem;

import java.util.HashMap;

public class QueryModel {
    private static final QueryModel singletonInstance = new QueryModel();

    private QueryModel() {
        System.out.println("QueryModel Instance created.");
    }

    public static QueryModel getInstance() {
        return singletonInstance;
    }

    private HashMap<String, MovingItem> movingItemHashMap = new HashMap<>();

    public MovingItem getMovingItemFromName(String name) {
        return movingItemHashMap.get(name);
    }
}
