package streamingsystems.CommandsModel.commands;

import streamingsystems.CommandsModel.DomainModel;
import streamingsystems.CommandsModel.EventStore;
import streamingsystems.CommandsModel.Meta.Command;
import streamingsystems.MovingItem;
import streamingsystems.implemented.events.MovingItemCreatedEvent;

public class CreateItemCommand extends Command {

    MovingItem movingItem;

    public CreateItemCommand(MovingItem movingItem) {
        this.movingItem = movingItem;
    }

    @Override
    public void handle() {
        DomainModel.getInstance().addMovingItemNameToModel(movingItem.getName());
        EventStore.getInstance().addEvent(new MovingItemCreatedEvent(movingItem));
    }
}
