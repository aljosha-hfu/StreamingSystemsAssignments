package streamingsystems;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public enum ConfigManager {
    INSTANCE;

    private String rabbitMqHost;

    private int rabbitMqPort;
    private String rabbitMqUser;
    private String rabbitMqPassword;

    private void loadConfig() {
        Properties properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream("src/main/resources/app.config");
            properties.load(fileInputStream);
            rabbitMqHost = properties.getProperty("rabbitmq.host");
            rabbitMqPort = Integer.parseInt(properties.getProperty("rabbitmq.port"));
            rabbitMqUser = properties.getProperty("rabbitmq.user");
            rabbitMqPassword = properties.getProperty("rabbitmq.password");
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    ConfigManager() {
        loadConfig();
    }

    public String getRabbitMqHost() {
        return rabbitMqHost;
    }

    public int getRabbitMqPort() {
        return rabbitMqPort;
    }

    public String getRabbitMqUser() {
        return rabbitMqUser;
    }

    public String getRabbitMqPassword() {
        return rabbitMqPassword;
    }
}
