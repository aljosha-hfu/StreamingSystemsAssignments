package streamingsystems.CommandsModel.commands;

import streamingsystems.CommandsModel.EventStore;
import streamingsystems.CommandsModel.Meta.Command;
import streamingsystems.implemented.events.MovingItemValueChangedEvent;

public class ChangeValueCommand extends Command {
    String id;
    int newValue;

    public ChangeValueCommand(String id, int newValue) {
        this.id = id;
        this.newValue = newValue;
    }

    @Override
    public void handle() {
        EventStore.getInstance().addEvent(new MovingItemValueChangedEvent(id, newValue));
    }
}
