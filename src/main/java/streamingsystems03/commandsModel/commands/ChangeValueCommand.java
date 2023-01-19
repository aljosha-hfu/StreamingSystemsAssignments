package streamingsystems03.commandsModel.commands;

import streamingsystems03.commandsModel.EventStore;
import streamingsystems03.commandsModel.meta.Command;
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
