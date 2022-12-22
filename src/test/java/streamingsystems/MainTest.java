package streamingsystems;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import streamingsystems.CommandsModel.CommandHandler;
import streamingsystems.CommandsModel.EventStore;
import streamingsystems.QueryHandlingModel.QueryHandler;
import streamingsystems.QueryHandlingModel.QueryModel;
import streamingsystems.implemented.MovingItemImpl;

import static org.junit.jupiter.api.Assertions.assertThrows;

class MainTest {
    CommandHandler commandHandlerInstance;
    EventStore eventStore;
    QueryModel queryModel;
    QueryHandler queryHandler;

    @BeforeEach
    void setUp() {
        commandHandlerInstance = CommandHandler.getInstance();
        eventStore = EventStore.getInstance();
        queryModel =  QueryModel.getInstance();
        queryHandler = new QueryHandler(queryModel);
    }

    @Test
    void createAndDeleteMovingItem() {
        commandHandlerInstance.createItem(new MovingItemImpl("Moving Item 1"));
        commandHandlerInstance.deleteItem("Moving Item 1");
    }

    @Test
    void deleteItemThatDoesNotExist() {
        assertThrows(IllegalArgumentException.class, () -> commandHandlerInstance.deleteItem("Moving Item 1"));
    }

    @Test
    void moveItemThatDoesNotExist() {
        assertThrows(IllegalArgumentException.class, () -> commandHandlerInstance.moveItem("Moving Item 1", new int[]{1, 2, 3}));
    }

    @Test
    void changeValueOfItemThatDoesNotExist() {
        assertThrows(IllegalArgumentException.class, () -> commandHandlerInstance.changeValue("Moving Item 1", 420));
    }
}