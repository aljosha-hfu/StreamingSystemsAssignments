package streamingsystems;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import streamingsystems.CommandsModel.CommandHandler;
import streamingsystems.implemented.MovingItemDTO;

import static org.junit.jupiter.api.Assertions.assertThrows;

class MainTest {
    CommandHandler commandHandlerInstance;

    @BeforeEach
    void setUp() {
        commandHandlerInstance = CommandHandler.getInstance();
    }

    @Test
    void createAndDeleteMovingItem() {
        commandHandlerInstance.createItem(new MovingItemDTO("Moving Item 1"));
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