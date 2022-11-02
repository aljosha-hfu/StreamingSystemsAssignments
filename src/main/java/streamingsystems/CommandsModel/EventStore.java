package streamingsystems.CommandsModel;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import streamingsystems.CommandsModel.Meta.Event;
import streamingsystems.ConfigManager;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeoutException;

public class EventStore {
    private static final EventStore singletonInstance = new EventStore();

    ConnectionFactory rabbitMQConnectionFactory = new ConnectionFactory();

    private EventStore() {
        System.out.println("Connecting to RabbitMQ...");
        rabbitMQConnectionFactory.setUsername(ConfigManager.INSTANCE.getRabbitMqUser());
        rabbitMQConnectionFactory.setPassword(ConfigManager.INSTANCE.getRabbitMqPassword());
        rabbitMQConnectionFactory.setVirtualHost("github-pipeline-test");
        rabbitMQConnectionFactory.setHost(ConfigManager.INSTANCE.getRabbitMqHost());
        rabbitMQConnectionFactory.setPort(ConfigManager.INSTANCE.getRabbitMqPort());

        try {
            Connection conn = rabbitMQConnectionFactory.newConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Connecting to RabbitMQ successful.");

        System.out.println("Instantiated EventStore singleton...");
    }

    public static EventStore getInstance() {
        return singletonInstance;
    }

    private final LinkedBlockingQueue<Event> eventQueue = new LinkedBlockingQueue<>();

    public void addEvent(Event event) {
        // TODO add rabbitmq here
        this.eventQueue.add(event);
    }

    public LinkedBlockingQueue<Event> getEventQueue() {
        return eventQueue;
    }
}
