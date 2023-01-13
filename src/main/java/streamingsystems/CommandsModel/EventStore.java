package streamingsystems.CommandsModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import streamingsystems.CommandsModel.Meta.Event;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * The component that stores executed events.
 */
public class EventStore {
    private static final EventStore singletonInstance = new EventStore();
    private final Logger logger;

    private EventStore() {
        logger = LoggerFactory.getLogger(EventStore.class);
        logger.info("Instantiated EventStore singleton...");
    }

    /**
     * @return The singleton instance of the event store.
     */
    public static EventStore getInstance() {
        return singletonInstance;
    }

    private final LinkedBlockingQueue<Event> eventQueue = new LinkedBlockingQueue<>();

    /**
     * Add an event to the event store.
     *
     * @param event The event to add to the event store.
     */
    public void addEvent(Event event) {
        this.eventQueue.add(event);
    }

    /**
     * @return The entire event queue
     */
    public LinkedBlockingQueue<Event> getEventQueue() {
        return eventQueue;
    }
}
