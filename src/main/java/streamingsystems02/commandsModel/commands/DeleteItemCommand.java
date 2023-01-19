package streamingsystems02.commandsModel.commands;

import streamingsystems02.commandsModel.DomainModel;
import streamingsystems02.commandsModel.EventStore;
import streamingsystems02.commandsModel.meta.Command;
import streamingsystems02.implemented.events.MovingItemDeletedEvent;

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
