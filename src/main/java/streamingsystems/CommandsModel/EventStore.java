package streamingsystems.CommandsModel;

import streamingsystems.CommandsModel.Meta.Event;

import java.util.concurrent.LinkedBlockingQueue;

public class EventStore {
    private static final EventStore singletonInstance = new EventStore();

    private EventStore() {
        System.out.println("Instantiated EventStore singleton...");
    }

    public static EventStore getInstance() {
        return singletonInstance;
    }

    private final LinkedBlockingQueue<Event> eventQueue = new LinkedBlockingQueue<>();

    public void addEvent(Event event) {
        this.eventQueue.add(event);
    }

    public LinkedBlockingQueue<Event> getEventQueue() {
        return eventQueue;
    }
}
