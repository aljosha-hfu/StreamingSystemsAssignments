package streamingsystems;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import streamingsystems.CommandsModel.CommandHandler;
import streamingsystems.CommandsModel.EventStore;
import streamingsystems.QueryHandlingModel.QueryHandler;
import streamingsystems.QueryHandlingModel.QueryModel;
import streamingsystems.implemented.MovingItemDTO;

import static org.junit.jupiter.api.Assertions.assertThrows;

class MainTest {
    CommandHandler commandHandlerInstance;
    EventStore eventStore;
    QueryModel queryModel;
    QueryHandler queryHandler;

    public static String baseMovingItemName = "Moving Item 1";

    @BeforeEach
    void setUp() {
        commandHandlerInstance = CommandHandler.getInstance();
        eventStore = EventStore.getInstance();
        queryModel = new QueryModel(eventStore);
        queryHandler = new QueryHandler(queryModel);
    }

    @Test
    void createAndDeleteMovingItem() {
        commandHandlerInstance.createItem(new MovingItemDTO(baseMovingItemName));
        commandHandlerInstance.deleteItem(baseMovingItemName);
    }

    @Test
    void createAndMoveMovingItem() {
        commandHandlerInstance.createItem(new MovingItemDTO(baseMovingItemName));
        commandHandlerInstance.moveItem(baseMovingItemName, new int[]{1, 1, 1});
        commandHandlerInstance.deleteItem(baseMovingItemName);
    }

    @Test
    void createMovingItemAndChangeValue() {
        commandHandlerInstance.createItem(new MovingItemDTO(baseMovingItemName));
        commandHandlerInstance.changeValue(baseMovingItemName, 42);
        commandHandlerInstance.deleteItem(baseMovingItemName);
    }

    @Test
    void deleteItemThatDoesNotExist() {
        assertThrows(IllegalArgumentException.class, () -> commandHandlerInstance.deleteItem(baseMovingItemName));
    }

    @Test
    void moveItemThatDoesNotExist() {
        assertThrows(IllegalArgumentException.class, () -> commandHandlerInstance.moveItem(baseMovingItemName, new int[]{1, 2, 3}));
    }

    @Test
    void changeValueOfItemThatDoesNotExist() {
        assertThrows(IllegalArgumentException.class, () -> commandHandlerInstance.changeValue(baseMovingItemName, 420));
    }
}