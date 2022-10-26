package streamingsystems.CommandsModel.commands;


import streamingsystems.CommandsModel.EventStore;
import streamingsystems.CommandsModel.Meta.Command;
import streamingsystems.implemented.events.MovingItemMovedEvent;

public class MoveItemCommand extends Command {
    String id;
    int[] vector;

    public MoveItemCommand(String id, int[] vector) {
        this.id = id;
        this.vector = vector;
    }

    @Override
    public void handle() {
        EventStore.getInstance().addEvent(new MovingItemMovedEvent(id, vector));
    }
}
