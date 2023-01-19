package streamingsystems01.commandsmodel.commands;

import streamingsystems01.commandsmodel.DomainModel;
import streamingsystems01.commandsmodel.EventStore;
import streamingsystems01.commandsmodel.meta.Command;
import streamingsystems01.commandsmodel.events.MovingItemDeletedEvent;

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
     * Handle the command and add the needed events to the event store.
     */
    @Override
    public void handle() {
        DomainModel.getInstance().removeMovingItemNameFromModel(this.id);

        EventStore.getInstance().addEvent(new MovingItemDeletedEvent(id));
    }
}
