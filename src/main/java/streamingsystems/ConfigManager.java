package streamingsystems;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public enum ConfigManager {
    INSTANCE;

    private String rabbitMqHost;

    private int rabbitMqPort;
    private String rabbitMqUser;
    private String rabbitMqPassword;

    private String kafkaTopicName;

    private String kafkaClientId;
    private String kafkaUrl;

    ConfigManager() {
        loadConfig();
    }

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
            kafkaTopicName = properties.getProperty(
                    "kafka.topicname") + new SimpleDateFormat(
                    "yyyyMMdd_HHmmss").format(new Date());
            kafkaClientId = properties.getProperty("kafka.clientid");
            kafkaUrl = properties.getProperty("kafka.url");
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public String getKafkaTopicName() {
        return kafkaTopicName;
    }

    public String getKafkaClientId() {
        return kafkaClientId;
    }

    public String getKafkaUrl() {
        return kafkaUrl;
    }
}
