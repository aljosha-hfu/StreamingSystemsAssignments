package streamingsystems05;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * Manages the configuration of the application.
 */
public enum ConfigManager {
    /**
     * The singleton instance of the ConfigManager.
     */
    INSTANCE;

    private String kafkaTopicName;

    private String kafkaClientId;
    private String kafkaUrl;

    private String sampleDataPath;

    ConfigManager() {
        loadConfig();
    }

    private void loadConfig() {
        Logger logger = LoggerFactory.getLogger(ConfigManager.class.getName());
        logger.info("Loading data from config");
        Properties properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream(
                    "src/main/resources/app.config");
            properties.load(fileInputStream);
            kafkaTopicName = properties.getProperty(
                    "kafka.topicname") + new SimpleDateFormat(
                    "yyyyMMdd_HHmmss").format(new Date());
            kafkaClientId = properties.getProperty("kafka.clientid");
            kafkaUrl = properties.getProperty("kafka.url");
            sampleDataPath = properties.getProperty("sampledatapath");
            logger.info("Kafka topic name: " + kafkaTopicName);
            logger.info("Kafka client id: " + kafkaClientId);
            logger.info("Kafka url: " + kafkaUrl);
            logger.info("Sample data path: " + sampleDataPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return The name of the Kafka topic to use.
     */
    public String getKafkaTopicName() {
        return kafkaTopicName;
    }

    /**
     * @return The client id to use for the Kafka consumer.
     */
    public String getKafkaClientId() {
        return kafkaClientId;
    }

    /**
     * @return The URL of the Kafka broker.
     */
    public String getKafkaUrl() {
        return kafkaUrl;
    }

    /**
     * @return The path to the sample data.
     */
    public String getSampleDataPath() {
        return sampleDataPath;
    }
}
