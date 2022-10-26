package streamingsystems.QueryHandlingModel;

import streamingsystems.MovingItem;
import streamingsystems.implemented.MovingItemDTO;
import streamingsystems.QueryHandlingModel.Predefined.Query;

import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.stream.Collectors;

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
        return new MovingItemDTO(QueryModel.getInstance().getMovingItemFromName(name));
    }

    @SuppressWarnings("Convert2MethodRef")
    public Collection<MovingItemDTO> getAllMovingItemsAsCollection() {
        Collection<MovingItem> allMovingItemsCollection = QueryModel.getInstance().getAllMovingItems();
        return allMovingItemsCollection.stream().map((MovingItem eachMovingItem) -> new MovingItemDTO(eachMovingItem)).toList();
    }

    @Override
    public Enumeration<MovingItemDTO> getMovingItems() {
        return Collections.enumeration(getAllMovingItemsAsCollection());
    }

    @Override
    public Enumeration<MovingItemDTO> getMovingItemsAtPosition(int[] position) {
        Collection<MovingItemDTO> movingItemDTOsAtPosition = getAllMovingItemsAsCollection()
                .stream().filter((MovingItem eachMovingItem) -> eachMovingItem.getLocation() == position).toList();

        return Collections.enumeration(movingItemDTOsAtPosition);
    }
}
