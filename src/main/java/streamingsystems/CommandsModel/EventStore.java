package streamingsystems.CommandsModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import streamingsystems.CommandsModel.Meta.Event;

import java.util.concurrent.LinkedBlockingQueue;

public class EventStore {
    private static final EventStore singletonInstance = new EventStore();
    private final Logger logger;

    private EventStore() {
        logger = LoggerFactory.getLogger(EventStore.class);
        logger.info("Instantiated EventStore singleton...");
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
