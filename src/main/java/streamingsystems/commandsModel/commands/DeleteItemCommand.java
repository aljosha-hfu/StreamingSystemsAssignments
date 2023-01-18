package streamingsystems.commandsModel.commands;

import streamingsystems.commandsModel.DomainModel;
import streamingsystems.commandsModel.EventStore;
import streamingsystems.commandsModel.meta.Command;
import streamingsystems.implemented.events.MovingItemDeletedEvent;

/**
 * Command for deleting a moving item.
 */
public class DeleteItemCommand extends Command {
    final String id;

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
