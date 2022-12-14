package streamingsystems;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
            kafkaTopicName = properties.getProperty("kafka.topicname") +
                    new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            kafkaClientId = properties.getProperty("kafka.clientid");
            kafkaUrl = properties.getProperty("kafka.url");
            logger.info("Kafka topic name: " + kafkaTopicName);
            logger.info("Kafka client id: " + kafkaClientId);
            logger.info("Kafka url: " + kafkaUrl);
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
