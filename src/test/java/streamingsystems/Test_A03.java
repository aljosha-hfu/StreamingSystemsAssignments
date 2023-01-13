package streamingsystems;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import streamingsystems.CommandsModel.CommandHandler;
import streamingsystems.CommandsModel.EventStore;
import streamingsystems.QueryHandlingModel.QueryHandler;
import streamingsystems.QueryHandlingModel.QueryModel;
import streamingsystems.implemented.MovingItemImpl;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertThrows;

class Test_A03 {
    CommandHandler commandHandlerInstance;
    EventStore eventStore;
    QueryModel queryModel;
    QueryHandler queryHandler;

    String movingItemTestName1 = "Moving Item 1";
    String movingItemTestName2 = "Moving Item 2";
    String movingItemTestName3 = "Moving Item 3";

    @BeforeEach
    void setUp() {
        commandHandlerInstance = CommandHandler.getInstance();
        eventStore = EventStore.getInstance();
        queryModel = QueryModel.getInstance();
        queryHandler = new QueryHandler(queryModel);
    }

    @Test
    void createTwoItemsAndMoveOnTopOfOneAnotherToAssertDeletion() {
        commandHandlerInstance.createItem(new MovingItemImpl(movingItemTestName1));
        commandHandlerInstance.createItem(new MovingItemImpl(movingItemTestName2));

        commandHandlerInstance.moveItem(movingItemTestName1, new int[]{1, 1, 1});
        commandHandlerInstance.moveItem(movingItemTestName2, new int[]{1, 1, 1});

        assertThrows(IllegalArgumentException.class, () -> commandHandlerInstance.deleteItem(movingItemTestName1));
        commandHandlerInstance.deleteItem(movingItemTestName2);
    }

    @Test
    void assertDeleteMovingItemIfMoved19Times() {
        commandHandlerInstance.createItem(new MovingItemImpl(movingItemTestName1));

        IntStream
                .rangeClosed(1, 19)
                .forEach((i) -> commandHandlerInstance.moveItem(movingItemTestName1, new int[]{i, i, i}));

        commandHandlerInstance.deleteItem(movingItemTestName1);
    }

    @Test
    void assertDeleteMovingItemIfMoved20Times() {
        commandHandlerInstance.createItem(new MovingItemImpl(movingItemTestName1));

        IntStream
                .rangeClosed(1, 20)
                .forEach((i) -> commandHandlerInstance.moveItem(movingItemTestName1, new int[]{i, i, i}));

        assertThrows(IllegalArgumentException.class, () -> commandHandlerInstance.deleteItem(movingItemTestName1));
    }
}