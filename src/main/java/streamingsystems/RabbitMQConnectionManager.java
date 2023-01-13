package streamingsystems;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Handles the connection to RabbitMQ.
 */
public class RabbitMQConnectionManager {
    /**
     * The name of the queue in RabbitMQ.
     */
    public static final String QUEUE_NAME = "EventStore";
    private static final RabbitMQConnectionManager singletonInstance = new RabbitMQConnectionManager();

    /**
     * @return The singleton instance of the connection manager.
     */
    public static RabbitMQConnectionManager getInstance() {
        return singletonInstance;
    }

    private final Channel eventStoreChannel;

    private RabbitMQConnectionManager() {
        System.out.println("Connecting to RabbitMQ...");
        ConnectionFactory rabbitMQConnectionFactory = new ConnectionFactory();
        rabbitMQConnectionFactory.setUsername(
                ConfigManager.INSTANCE.getRabbitMqUser());
        rabbitMQConnectionFactory.setPassword(
                ConfigManager.INSTANCE.getRabbitMqPassword());
        rabbitMQConnectionFactory.setVirtualHost("/");
        rabbitMQConnectionFactory.setHost(
                ConfigManager.INSTANCE.getRabbitMqHost());
        rabbitMQConnectionFactory.setPort(
                ConfigManager.INSTANCE.getRabbitMqPort());

        try {
            Connection rabbitMQConnection = rabbitMQConnectionFactory.newConnection();
            eventStoreChannel = rabbitMQConnection.createChannel();
            eventStoreChannel.queueDeclare(QUEUE_NAME, false, false, false,
                    null);
            eventStoreChannel.queuePurge(QUEUE_NAME);
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Connecting to RabbitMQ successful.");

        System.out.println(
                "Instantiated RabbitMQConnectionManager singleton...");
    }

    /**
     * @return The channel to the event store.
     */
    public Channel getEventStoreChannel() {
        return eventStoreChannel;
    }
}
