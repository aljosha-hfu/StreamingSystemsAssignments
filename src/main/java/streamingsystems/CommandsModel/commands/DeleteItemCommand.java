package streamingsystems.CommandsModel.commands;

import streamingsystems.CommandsModel.DomainModel;
import streamingsystems.CommandsModel.EventStore;
import streamingsystems.CommandsModel.Meta.Command;
import streamingsystems.implemented.events.MovingItemCreatedEvent;
import streamingsystems.implemented.events.MovingItemDeletedEvent;

public class DeleteItemCommand extends Command {
    final String id;

    public DeleteItemCommand(String id) {
        this.id = id;
    }

    @Override
    public void handle() {
        DomainModel.getInstance().removeMovingItemNameFromModel(this.id);

        EventStore.getInstance().addEvent(new MovingItemDeletedEvent(id));
    }
}
