package streamingsystems03.commandsmodel.commands;

import streamingsystems03.commandsmodel.EventStore;
import streamingsystems03.commandsmodel.meta.Command;
import streamingsystems03.implemented.events.MovingItemValueChangedEvent;

/**
 * Command for changing the value of a moving item.
 */
public class ChangeValueCommand extends Command {
    final String id;
    final int newValue;

    /**
     * @param id       The id of the moving item.
     * @param newValue The new value to set.
     */
    public ChangeValueCommand(String id, int newValue) {
        this.id = id;
        this.newValue = newValue;
    }

    /**
     * Handle the command.
     */
    @Override
    public void handle() {
        EventStore.getInstance()
                .addEvent(new MovingItemValueChangedEvent(id, newValue));
    }
}
