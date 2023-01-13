package streamingsystems.CommandsModel.commands;

import streamingsystems.CommandsModel.EventStore;
import streamingsystems.CommandsModel.Meta.Command;
import streamingsystems.implemented.MovingItemImpl;
import streamingsystems.implemented.events.MovingItemCreatedEvent;

/**
 * Command for creating a moving item.
 */
public class CreateItemCommand extends Command {

    final MovingItemImpl movingItem;

    /**
     * @param movingItem The moving item to create.
     */
    public CreateItemCommand(MovingItemImpl movingItem) {
        this.movingItem = movingItem;
    }

    /**
     * Handle the command.
     */
    @Override
    public void handle() {
        EventStore.getInstance()
                .addEvent(new MovingItemCreatedEvent(movingItem));
    }
}
