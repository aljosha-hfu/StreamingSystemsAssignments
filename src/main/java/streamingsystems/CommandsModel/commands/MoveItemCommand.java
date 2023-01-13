package streamingsystems.CommandsModel.commands;


import streamingsystems.CommandsModel.EventStore;
import streamingsystems.CommandsModel.Meta.Command;
import streamingsystems.implemented.events.MovingItemMovedEvent;

/**
 * Command for moving a moving item.
 */
public class MoveItemCommand extends Command {
    /**
     * The id of the moving item.
     */
    String id;

    /**
     * The position of the moving item.
     */
    int[] vector;

    /**
     * @param id     The id of the moving item.
     * @param vector The new vector to apply to the moving item.
     */
    public MoveItemCommand(String id, int[] vector) {
        this.id = id;
        this.vector = vector;
    }

    /**
     * Handle the command.
     */
    @Override
    public void handle() {
        EventStore.getInstance().addEvent(new MovingItemMovedEvent(id, vector));
    }
}
