package streamingsystems.CommandsModel;

import org.apache.commons.lang3.SerializationUtils;
import streamingsystems.CommandsModel.Meta.Event;
import streamingsystems.RabbitMQConnectionManager;

import java.io.IOException;

import static streamingsystems.RabbitMQConnectionManager.QUEUE_NAME;

public class EventStore {
    private static final EventStore singletonInstance = new EventStore();

    private EventStore() {
        System.out.println("Instantiated EventStore singleton...");
    }

    public static EventStore getInstance() {
        return singletonInstance;
    }

    public void addEvent(Event event) {
        try {
            byte[] data = SerializationUtils.serialize(event);
            RabbitMQConnectionManager.getInstance().getEventStoreChannel().basicPublish("", QUEUE_NAME, null, data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
