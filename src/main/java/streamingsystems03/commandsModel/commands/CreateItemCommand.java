package streamingsystems03.commandsModel.commands;

import streamingsystems03.commandsModel.DomainModel;
import streamingsystems03.commandsModel.EventStore;
import streamingsystems03.commandsModel.meta.Command;
import streamingsystems03.implemented.MovingItemImpl;
import streamingsystems03.implemented.events.MovingItemCreatedEvent;

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
        DomainModel.getInstance().addMovingItemNameToModel(movingItem.getName());
        EventStore.getInstance().addEvent(new MovingItemCreatedEvent(movingItem));
    }
}
