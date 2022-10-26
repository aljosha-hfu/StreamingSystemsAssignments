package streamingsystems.CommandsModel.commands;

import streamingsystems.CommandsModel.DomainModel;
import streamingsystems.CommandsModel.Meta.Command;

public class DeleteItemCommand extends Command {
    String id;

    public DeleteItemCommand(String id) {
        this.id = id;
    }


    @Override
    public void handle() {
        // TODO

        DomainModel.getInstance().removeMovingItemNameFromModel(this.id);
    }
}
