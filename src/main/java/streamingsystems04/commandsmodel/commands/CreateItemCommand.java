package streamingsystems04.commandsmodel.commands;

import streamingsystems04.commandsmodel.EventStore;
import streamingsystems04.commandsmodel.meta.Command;
import streamingsystems04.implemented.MovingItemImpl;
import streamingsystems04.implemented.events.MovingItemCreatedEvent;

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
