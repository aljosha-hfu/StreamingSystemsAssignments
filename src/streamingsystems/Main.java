package streamingsystems;

import streamingsystems.CommandsModel.CommandHandler;
import streamingsystems.implemented.MovingItemDTO;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting...");

        CommandHandler commandHandlerInstance = CommandHandler.getInstance();

        commandHandlerInstance.createItem(new MovingItemDTO("Moving Item 1"));
        commandHandlerInstance.createItem(new MovingItemDTO("Moving Item 2"));
        commandHandlerInstance.createItem(new MovingItemDTO("Moving Item 3"));
        commandHandlerInstance.changeValue("Moving Item 1", 42);
        commandHandlerInstance.changeValue("Moving Item 2", 69);
        commandHandlerInstance.changeValue("Moving Item 3", 4711);
        commandHandlerInstance.moveItem("Moving Item 1", new int[]{1, 2, 3});
        commandHandlerInstance.deleteItem("Moving Item 1");

        System.out.println("Terminating...");
    }
}