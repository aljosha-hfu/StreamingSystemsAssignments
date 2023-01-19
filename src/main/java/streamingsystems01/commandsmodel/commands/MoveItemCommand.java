package streamingsystems01.commandsmodel.commands;


import streamingsystems01.commandsmodel.EventStore;
import streamingsystems01.commandsmodel.meta.Command;
import streamingsystems01.commandsmodel.events.MovingItemMovedEvent;

/**
 * Command for moving a moving item.
 */
public class MoveItemCommand extends Command {
    /**
     * The id of the moving item.
     */
    final String id;

    /**
     * The position of the moving item.
     */
    final int[] vector;

    /**
     * @param id     The id of the moving item.
     * @param vector The new vector to apply to the moving item.
     */
    public MoveItemCommand(String id, int[] vector) {
        this.id = id;
        this.vector = vector;
    }

    /**
     * Handle the command and add the needed events to the event store.
     */
    @Override
    public void handle() {
        EventStore.getInstance().addEvent(new MovingItemMovedEvent(id, vector));
    }
}
