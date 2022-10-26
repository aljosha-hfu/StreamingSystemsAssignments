package streamingsystems.QueryHandlingModel;

import streamingsystems.implemented.MovingItemDTO;
import streamingsystems.QueryHandlingModel.Predefined.Query;

import java.util.Enumeration;

public class QueryHandler implements Query {

    private static final QueryHandler singletonInstance = new QueryHandler();

    private QueryHandler() {
        System.out.println("Instantiated QueryHandler singleton...");
    }

    public static QueryHandler getInstance() {
        return singletonInstance;
    }

    @Override
    public MovingItemDTO getMovingItemByName(String name) {
        return null;
    }

    @Override
    public Enumeration<MovingItemDTO> getMovingItems() {
        return null;
    }

    @Override
    public Enumeration<MovingItemDTO> getMovingItemsAtPosition(int[] position) {
        return null;
    }
}
