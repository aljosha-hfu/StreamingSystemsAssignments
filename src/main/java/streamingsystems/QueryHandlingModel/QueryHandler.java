package streamingsystems.QueryHandlingModel;

import streamingsystems.QueryHandlingModel.Predefined.Query;
import streamingsystems.implemented.MovingItemDTO;

import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;

/**
 * Handles the user queries.
 */
public class QueryHandler implements Query {
    private final QueryModel queryModel;

    /**
     * @param queryModel The query model to use.
     */
    public QueryHandler(QueryModel queryModel) {
        this.queryModel = queryModel;
    }

    @Override
    public MovingItemDTO getMovingItemByName(String name) {
        return queryModel.getMovingItemDTOByName(name);
    }

    /**
     * @return The names of all moving items as a collection.
     */
    public Collection<MovingItemDTO> getAllMovingItemsAsCollection() {
        return queryModel.getAllMovingItems();
    }

    /**
     * Returns all moving items as an enumeration.
     *
     * @return All moving items.
     */
    @Override
    public Enumeration<MovingItemDTO> getMovingItems() {
        return Collections.enumeration(getAllMovingItemsAsCollection());
    }

    /**
     * Returns all moving items at a given position as an enumeration.
     *
     * @param position The position to get the moving items at.
     * @return All moving items at the given position.
     */
    @Override
    public Enumeration<MovingItemDTO> getMovingItemsAtPosition(int[] position) {
        Collection<MovingItemDTO> movingItemDTOsAtPosition = getAllMovingItemsAsCollection()
                .stream().filter((MovingItemDTO eachMovingItem) -> eachMovingItem.getLocation() == position).toList();

        return Collections.enumeration(movingItemDTOsAtPosition);
    }
}
