package streamingsystems.commands;

import streamingsystems.MovingItem;

import java.util.concurrent.LinkedBlockingQueue;

public class EventStore {
    private static final EventStore singletonInstance = new EventStore();

    private LinkedBlockingQueue<MovingItem> movingItemQueue = new LinkedBlockingQueue<>();


    private EventStore() {
        System.out.println("Instantiated EventStore singleton...");
    }

    public static EventStore getInstance() {
        return singletonInstance;
    }
}
