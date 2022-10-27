package streamingsystems.CommandsModel.commands;


import streamingsystems.CommandsModel.DomainModel;
import streamingsystems.CommandsModel.EventStore;
import streamingsystems.CommandsModel.Meta.Command;
import streamingsystems.implemented.events.MovingItemDeletedEvent;
import streamingsystems.implemented.events.MovingItemMovedEvent;

public class MoveItemCommand extends Command {
    final String id;
    final int[] vector;

    public MoveItemCommand(String id, int[] vector) {
        this.id = id;
        this.vector = vector;
    }

    @Override
    public void handle() {
        if (DomainModel.getInstance().getNumberOfMovesForMovingItemName(id) >= 19) {
            DomainModel.getInstance().removeMovingItemNameFromModel(id);
            EventStore.getInstance().addEvent(new MovingItemDeletedEvent(id));
        } else {
            EventStore.getInstance().addEvent(new MovingItemMovedEvent(id, vector));
            DomainModel.getInstance().incrementNumberOfMovesForMovingItemNameByOne(id);
        }
    }
}
