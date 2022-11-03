package streamingsystems;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import streamingsystems.CommandsModel.EventStore;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMQConnectionManager {
    public static final String QUEUE_NAME = "EventStore";
    private static final RabbitMQConnectionManager singletonInstance = new RabbitMQConnectionManager();

    public static RabbitMQConnectionManager getInstance() {
        return singletonInstance;
    }

    private final Connection rabbitMQConnection;
    private final Channel eventStoreChannel;

    private RabbitMQConnectionManager() {
        System.out.println("Connecting to RabbitMQ...");
        ConnectionFactory rabbitMQConnectionFactory = new ConnectionFactory();
        rabbitMQConnectionFactory.setUsername(ConfigManager.INSTANCE.getRabbitMqUser());
        rabbitMQConnectionFactory.setPassword(ConfigManager.INSTANCE.getRabbitMqPassword());
        rabbitMQConnectionFactory.setVirtualHost("/");
        rabbitMQConnectionFactory.setHost(ConfigManager.INSTANCE.getRabbitMqHost());
        rabbitMQConnectionFactory.setPort(ConfigManager.INSTANCE.getRabbitMqPort());

        try {
            rabbitMQConnection = rabbitMQConnectionFactory.newConnection();
            eventStoreChannel = rabbitMQConnection.createChannel();
            eventStoreChannel.queueDeclare(QUEUE_NAME, false, false, false, null);
            eventStoreChannel.queuePurge(QUEUE_NAME);
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Connecting to RabbitMQ successful.");

        System.out.println("Instantiated RabbitMQConnectionManager singleton...");
    }

    public Connection getRabbitMQConnection() {
        return rabbitMQConnection;
    }

    public Channel getEventStoreChannel() {
        return eventStoreChannel;
    }
}
