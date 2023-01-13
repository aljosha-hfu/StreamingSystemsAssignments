package streamingsystems;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * The config manager that reads the config file.
 */
public enum ConfigManager {
    /**
     * The singleton instance.
     */
    INSTANCE;

    private String rabbitMqHost;

    private int rabbitMqPort;
    private String rabbitMqUser;
    private String rabbitMqPassword;

    private void loadConfig() {
        Properties properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream(
                    "src/main/resources/app.config");
            properties.load(fileInputStream);
            rabbitMqHost = properties.getProperty("rabbitmq.host");
            rabbitMqPort = Integer.parseInt(
                    properties.getProperty("rabbitmq.port"));
            rabbitMqUser = properties.getProperty("rabbitmq.user");
            rabbitMqPassword = properties.getProperty("rabbitmq.password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ConfigManager() {
        loadConfig();
    }

    /**
     * @return The RabbitMQ Host.
     */
    public String getRabbitMqHost() {
        return rabbitMqHost;
    }

    /**
     * @return The RabbitMQ Port.
     */
    public int getRabbitMqPort() {
        return rabbitMqPort;
    }

    /**
     * @return The RabbitMQ User.
     */
    public String getRabbitMqUser() {
        return rabbitMqUser;
    }

    /**
     * @return The RabbitMQ Password.
     */
    public String getRabbitMqPassword() {
        return rabbitMqPassword;
    }
}
