package streamingsystems.commands;

public class CommandHandler {

    private static final CommandHandler singletonInstance = new CommandHandler();

    private CommandHandler() {
        System.out.println("Instantiated CommandHandler singleton...");
    }

    public static CommandHandler getInstance() {
        return singletonInstance;
    }

    public void handleCommand(Commands commandsToExecute) {
        // TODO
    }
}
