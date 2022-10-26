package streamingsystems.CommandsModel.commands;

public class DeleteItemCommand extends Command {
    String id;

    public DeleteItemCommand(String id) {
        this.id = id;
    }


    @Override
    public void handle() {

    }
}
