package streamingsystems;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import streamingsystems.CommandsModel.CommandHandler;
import streamingsystems.CommandsModel.EventStore;
import streamingsystems.QueryHandlingModel.QueryHandler;
import streamingsystems.QueryHandlingModel.QueryModel;
import streamingsystems.implemented.MovingItemImpl;

import static org.junit.jupiter.api.Assertions.assertThrows;

class Test_A01 {
    final String movingItemTestName1 = "Moving Item 1";
    CommandHandler commandHandlerInstance;
    EventStore eventStore;
    QueryModel queryModel;
    QueryHandler queryHandler;
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
    void createAndDeleteMovingItem() {
        commandHandlerInstance.createItem(
                new MovingItemImpl(movingItemTestName1));
        commandHandlerInstance.deleteItem(movingItemTestName1);
    }

    @Test
    void createAndMoveMovingItem() {
        commandHandlerInstance.createItem(
                new MovingItemImpl(movingItemTestName1));
        commandHandlerInstance.moveItem(movingItemTestName1,
                new int[]{1, 2, 3});
        commandHandlerInstance.deleteItem(movingItemTestName1);
    }

    @Test
    void createMovingItemAndChangeValue() {
        commandHandlerInstance.createItem(
                new MovingItemImpl(movingItemTestName1));
        commandHandlerInstance.changeValue(movingItemTestName1, 42);
        commandHandlerInstance.deleteItem(movingItemTestName1);
    }

    @Test
    void deleteItemThatDoesNotExist() {
        assertThrows(IllegalArgumentException.class,
                () -> commandHandlerInstance.deleteItem(movingItemTestName1));
    }

    @Test
    void moveItemThatDoesNotExist() {
        assertThrows(IllegalArgumentException.class,
                () -> commandHandlerInstance.moveItem(movingItemTestName1,
                        new int[]{1, 2, 3}));
    }

    @Test
    void changeValueOfItemThatDoesNotExist() {
        assertThrows(IllegalArgumentException.class,
                () -> commandHandlerInstance.changeValue(movingItemTestName1,
                        420));
    }
}