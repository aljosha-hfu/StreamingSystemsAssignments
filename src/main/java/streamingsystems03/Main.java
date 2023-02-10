package streamingsystems03;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import streamingsystems03.commandsModel.CommandHandler;
import streamingsystems03.queryHandlingModel.QueryHandler;
import streamingsystems03.queryHandlingModel.QueryModel;
import streamingsystems03.implemented.MovingItemImpl;

/**
 * The main class of the application.
 */
public class Main {
    /**
     * The main entrypoint of the application.
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(Main.class);
        logger.info("Starting...");
        CommandHandler commandHandlerInstance = CommandHandler.getInstance();
        commandHandlerInstance.createItem(new MovingItemImpl("Moving Item 1"));
        commandHandlerInstance.createItem(new MovingItemImpl("Moving Item 2"));
        commandHandlerInstance.createItem(new MovingItemImpl("Moving Item 3"));
        commandHandlerInstance.changeValue("Moving Item 1", 42);
        commandHandlerInstance.changeValue("Moving Item 2", 69);
        commandHandlerInstance.changeValue("Moving Item 3", 4711);
        commandHandlerInstance.moveItem("Moving Item 1", new int[]{1, 2, 3});
        commandHandlerInstance.deleteItem("Moving Item 1");
        QueryModel queryModel = QueryModel.getInstance();
        QueryHandler queryHandler = new QueryHandler(queryModel);
        queryModel.updateQueryModel();

        queryModel.getAllMovingItems().forEach(x -> logger.info(x.toString()));
        logger.info(queryHandler.getMovingItemByName("Moving Item 2").toString());

        logger.info("Terminating...");
    }
}