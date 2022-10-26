package streamingsystems.CommandsModel;

import streamingsystems.CommandsModel.Meta.Command;
import streamingsystems.CommandsModel.Meta.Predefined.Commands;
import streamingsystems.CommandsModel.commands.*;
import streamingsystems.MovingItem;

public class CommandHandler implements Commands {
    private static final CommandHandler singletonInstance = new CommandHandler();

    private CommandHandler() {
        System.out.println("CommandHandler Instance created.");
    }

    public static CommandHandler getInstance() {
        return singletonInstance;
    }

    public void checkMovingItemExistsAndThrowException(String id) {
        if (!DomainModel.getInstance().movingItemNameExists(id)) {
            throw new IllegalArgumentException("An item with this name does not exist");
        }
    }

    @Override
    public void createItem(MovingItem movingItem) {
        if (DomainModel.getInstance().movingItemNameExists(movingItem.getName())) {
            throw new IllegalArgumentException("An item with this name already exists");
        }

        Command command = new CreateItemCommand(movingItem);
        command.handle();
    }

    @Override
    public void deleteItem(String id) {
        if (id.isBlank()) {
            throw new IllegalArgumentException("ID must be a valid, non-blank string");
        }
        checkMovingItemExistsAndThrowException(id);


        Command command = new DeleteItemCommand(id);
        command.handle();
    }

    @Override
    public void moveItem(String id, int[] vector) {
        // TODO: Is this alright?
        if (vector.length != 3) {
            throw new IllegalArgumentException("Vector must be of length 3 (x, y, z)");
        }
        checkMovingItemExistsAndThrowException(id);

        Command command = new MoveItemCommand(id, vector);
        command.handle();
    }

    @Override
    public void changeValue(String id, int newValue) {
        if (id.isBlank()) {
            throw new IllegalArgumentException("ID must be a valid, non-blank string");
        }
        checkMovingItemExistsAndThrowException(id);

        Command command = new ChangeValueCommand(id, newValue);
        command.handle();
    }
}
