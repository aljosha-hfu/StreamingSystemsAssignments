package streamingsystems.CommandsModel.commands;


import streamingsystems.CommandsModel.Meta.Command;

public class MoveItemCommand extends Command {
    String id;
    int[] vector;

    public MoveItemCommand(String id, int[] vector) {
        this.id = id;
        this.vector = vector;
    }

    @Override
    public void handle() {

    }
}
