package streamingsystems.CommandsModel.commands;

import streamingsystems.MovingItem;

public class CreateItemCommand extends Command {

    MovingItem movingItem;

    public CreateItemCommand(MovingItem movingItem) {
        this.movingItem = movingItem;
    }

    @Override
    public void handle() {
        // TODO
    }
}
