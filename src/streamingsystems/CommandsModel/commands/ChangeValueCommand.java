package streamingsystems.CommandsModel.commands;

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
