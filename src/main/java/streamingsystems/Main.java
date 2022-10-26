package streamingsystems;

import streamingsystems.CommandsModel.CommandHandler;
import streamingsystems.CommandsModel.EventStore;
import streamingsystems.QueryHandlingModel.QueryHandler;
import streamingsystems.QueryHandlingModel.QueryModel;
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

//        System.out.println(QueryHandler.getInstance().getMovingItemByName("Moving Item 2"));

        EventStore eventStore = EventStore.getInstance();
        QueryModel queryModel = new QueryModel(eventStore);

        System.out.println(queryModel.getAllMovingItems());

        System.out.println("Terminating...");
    }
}