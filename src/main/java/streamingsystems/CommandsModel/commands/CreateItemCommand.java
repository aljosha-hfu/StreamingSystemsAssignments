package streamingsystems.CommandsModel.commands;

import streamingsystems.CommandsModel.DomainModel;
import streamingsystems.CommandsModel.EventStore;
import streamingsystems.CommandsModel.Meta.Command;
import streamingsystems.implemented.MovingItemImpl;
import streamingsystems.implemented.events.MovingItemCreatedEvent;

public class CreateItemCommand extends Command {

    final MovingItemImpl movingItem;

    public CreateItemCommand(MovingItemImpl movingItem) {
        this.movingItem = movingItem;
    }

    @Override
    public void handle() {
        EventStore.getInstance().addEvent(new MovingItemCreatedEvent(movingItem));
    }
}
