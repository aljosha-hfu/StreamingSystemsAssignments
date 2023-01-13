package streamingsystems.CommandsModel.commands;

import streamingsystems.CommandsModel.DomainModel;
import streamingsystems.CommandsModel.EventStore;
import streamingsystems.CommandsModel.Meta.Command;
import streamingsystems.implemented.events.MovingItemCreatedEvent;
import streamingsystems.implemented.events.MovingItemDeletedEvent;

/**
 * Command for deleting a moving item.
 */
public class DeleteItemCommand extends Command {
    String id;

    /**
     * @param id The id of the moving item to delete.
     */
    public DeleteItemCommand(String id) {
        this.id = id;
    }

    /**
     * Handle the command.
     */
    @Override
    public void handle() {
        DomainModel.getInstance().removeMovingItemNameFromModel(this.id);

        EventStore.getInstance().addEvent(new MovingItemDeletedEvent(id));
    }
}