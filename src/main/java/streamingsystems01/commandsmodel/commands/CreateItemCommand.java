package streamingsystems01.commandsmodel.commands;

import streamingsystems01.commandsmodel.DomainModel;
import streamingsystems01.commandsmodel.EventStore;
import streamingsystems01.commandsmodel.meta.Command;
import streamingsystems01.implemented.MovingItemImpl;
import streamingsystems01.commandsmodel.events.MovingItemCreatedEvent;

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
