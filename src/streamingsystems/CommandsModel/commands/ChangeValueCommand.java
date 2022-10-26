package streamingsystems.CommandsModel.commands;

import streamingsystems.CommandsModel.Meta.Command;

public class ChangeValueCommand extends Command {
    String id;
    int newValue;

    public ChangeValueCommand(String id, int newValue) {
        this.id = id;
        this.newValue = newValue;
    }

    @Override
    public void handle() {
        // TODO
    }
}
