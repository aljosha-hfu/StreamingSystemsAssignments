package streamingsystems.commands;

import streamingsystems.MovingItem;

import java.util.concurrent.LinkedBlockingQueue;

public class CommandHandler {

    private static final CommandHandler singletonInstance = new CommandHandler();

    private CommandHandler() {
        System.out.println("Instantiated CommandHandler singleton...");
    }

    public static CommandHandler getInstance() {
        return singletonInstance;
    }

    public void handleCommand(Command commandToExecute) {
        // TODO
    }
}
