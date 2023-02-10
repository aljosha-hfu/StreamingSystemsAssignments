package streamingsystems02.commandsModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import streamingsystems02.commandsModel.meta.Command;
import streamingsystems02.commandsModel.meta.predefined.Commands;
import streamingsystems02.commandsModel.commands.ChangeValueCommand;
import streamingsystems02.commandsModel.commands.CreateItemCommand;
import streamingsystems02.commandsModel.commands.DeleteItemCommand;
import streamingsystems02.commandsModel.commands.MoveItemCommand;
import streamingsystems02.implemented.MovingItemImpl;

/**
 * The command handler that handles all commands that were given in the
 * assignment.
 */
public class CommandHandler implements Commands {
    private static final CommandHandler singletonInstance = new CommandHandler();

    private CommandHandler() {
        Logger logger = LoggerFactory.getLogger(CommandHandler.class);
        logger.info("CommandHandler Instance created.");
    }

    /**
     * @return The singleton instance of the command handler.
     */
    public static CommandHandler getInstance() {
        return singletonInstance;
    }

    /**
     * Check if the moving item exists and throw an exception if it does not.
     *
     * @param id The id of the moving item.
     */
    public void checkMovingItemExistsAndThrowException(String id) {
        if (!DomainModel.getInstance().movingItemNameExists(id)) {
            throw new IllegalArgumentException(
                    "An item with this name does not exist");
        }
    }

    /**
     * Create a moving item.
     *
     * @param movingItem The moving item to create.
     */
    @Override
    public void createItem(MovingItemImpl movingItem) {
        if (DomainModel.getInstance()
                .movingItemNameExists(movingItem.getName())) {
            throw new IllegalArgumentException(
                    "An item with this name already exists");
        }

        Command command = new CreateItemCommand(movingItem);
        command.handle();
    }

    /**
     * Delete a moving item.
     *
     * @param id The id of the moving item to delete.
     */
    @Override
    public void deleteItem(String id) {
        if (id.isBlank()) {
            throw new IllegalArgumentException(
                    "ID must be a valid, non-blank string");
        }
        checkMovingItemExistsAndThrowException(id);


        Command command = new DeleteItemCommand(id);
        command.handle();
    }

    /**
     * Move a moving item.
     *
     * @param id     The id of the moving item.
     * @param vector The vector to apply to the moving item.
     */
    @Override
    public void moveItem(String id, int[] vector) {
        if (vector.length != 3) {
            throw new IllegalArgumentException(
                    "Vector must be of length 3 (x, y, z)");
        }
        checkMovingItemExistsAndThrowException(id);

        Command command = new MoveItemCommand(id, vector);
        command.handle();
    }

    /**
     * Change the value of a moving item.
     *
     * @param id       The id of the moving item.
     * @param newValue The new value to set.
     */
    @Override
    public void changeValue(String id, int newValue) {
        if (id.isBlank()) {
            throw new IllegalArgumentException(
                    "ID must be a valid, non-blank string");
        }
        checkMovingItemExistsAndThrowException(id);

        Command command = new ChangeValueCommand(id, newValue);
        command.handle();
    }
}
