package streamingsystems.CommandsModel;

import streamingsystems.CommandsModel.Meta.Event;

import java.util.concurrent.LinkedBlockingQueue;

public class EventStore {
    private static final EventStore singletonInstance = new EventStore();

    private LinkedBlockingQueue<Event> eventQueue = new LinkedBlockingQueue<>();


    private EventStore() {
        System.out.println("Instantiated EventStore singleton...");
    }

    public static EventStore getInstance() {
        return singletonInstance;
    }

    public void addEvent(Event event) {
        this.eventQueue.add(event);
    }
}
