package streamingsystems.commandsModel.commands;

import streamingsystems.commandsModel.DomainModel;
import streamingsystems.commandsModel.EventStore;
import streamingsystems.commandsModel.meta.Command;
import streamingsystems.implemented.MovingItemImpl;
import streamingsystems.commandsModel.events.MovingItemCreatedEvent;

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
     * Handle the command and add the needed events to the event store.
     */
    @Override
    public void handle() {
        DomainModel.getInstance().addMovingItemNameToModel(movingItem.getName());
        EventStore.getInstance().addEvent(new MovingItemCreatedEvent(movingItem));
    }
}
