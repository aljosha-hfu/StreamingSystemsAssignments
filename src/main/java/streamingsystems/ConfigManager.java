package streamingsystems;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * Class for managing the configuration of the application.
 */
public enum ConfigManager {
    /**
     * The singleton instance of the ConfigManager.
     */
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

    /**
     * @return The Kafka topic name.
     */
    public String getKafkaTopicName() {
        return kafkaTopicName;
    }

    /**
     * @return The Kafka client id.
     */
    public String getKafkaClientId() {
        return kafkaClientId;
    }

    /**
     * @return The Kafka url.
     */
    public String getKafkaUrl() {
        return kafkaUrl;
    }
}
