package streamingsystems;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * A class for managing the configuration of the application.
 */
public enum ConfigManager {
    /**
     * The singleton instance of the ConfigManager.
     */
    INSTANCE;

    private String kafkaTopicName;

    private String kafkaClientId;
    private String kafkaUrl;

    private int monitoringWindowInSeconds;


    ConfigManager() {
        loadConfig();
    }

    /**
     * Loads the configuration from the config.properties file.
     */
    private void loadConfig() {
        Logger logger = LoggerFactory.getLogger(ConfigManager.class.getName());
        logger.info("Loading data from config");
        Properties properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream("src/main/resources/app.config");
            properties.load(fileInputStream);
            kafkaTopicName = properties.getProperty("kafka.topicname");
            kafkaClientId = properties.getProperty("kafka.clientid");
            kafkaUrl = properties.getProperty("kafka.url");
            monitoringWindowInSeconds = Integer.parseInt(properties.getProperty("monitoringPeriodInSeconds"));
            logger.info("Kafka topic name: " + kafkaTopicName);
            logger.info("Kafka client id: " + kafkaClientId);
            logger.info("Kafka url: " + kafkaUrl);
            logger.info("MonitoringPeriodInSeconds: " + monitoringWindowInSeconds);
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

    /**
     * @return The time of the monitoring windows in seconds
     */
    public int getMonitoringWindowInSeconds() {
        return monitoringWindowInSeconds;
    }
}
