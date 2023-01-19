package streamingsystems02.commandsModel.commands;

import streamingsystems02.commandsModel.DomainModel;
import streamingsystems02.commandsModel.EventStore;
import streamingsystems02.commandsModel.meta.Command;
import streamingsystems02.implemented.MovingItemImpl;
import streamingsystems02.implemented.events.MovingItemCreatedEvent;

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
