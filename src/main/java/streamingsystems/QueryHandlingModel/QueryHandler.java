package streamingsystems.QueryHandlingModel;

import streamingsystems.QueryHandlingModel.Predefined.Query;
import streamingsystems.implemented.MovingItemDTO;

import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;

public class QueryHandler implements Query {
    private final QueryModel queryModel;

    public QueryHandler(QueryModel queryModel) {
        this.queryModel = queryModel;
    }

    @Override
    public MovingItemDTO getMovingItemByName(String name) {
        return queryModel.getMovingItemDTOByName(name);
    }

    @SuppressWarnings("Convert2MethodRef")
    public Collection<MovingItemDTO> getAllMovingItemsAsCollection() {
        return queryModel.getAllMovingItems();
    }

    @Override
    public Enumeration<MovingItemDTO> getMovingItems() {
        return Collections.enumeration(getAllMovingItemsAsCollection());
    }

    @Override
    public Enumeration<MovingItemDTO> getMovingItemsAtPosition(int[] position) {
        Collection<MovingItemDTO> movingItemDTOsAtPosition = getAllMovingItemsAsCollection()
                .stream().filter((MovingItemDTO eachMovingItem) -> eachMovingItem.getLocation() == position).toList();

        return Collections.enumeration(movingItemDTOsAtPosition);
    }
}
