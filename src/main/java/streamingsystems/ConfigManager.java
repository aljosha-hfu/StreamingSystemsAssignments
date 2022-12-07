package streamingsystems;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public enum ConfigManager {
    INSTANCE;

    private String kafkaTopicName;

    private String kafkaClientId;
    private String kafkaUrl;

    private String sampleDataPath;

    private void loadConfig() {
        Logger logger = LoggerFactory.getLogger(ConfigManager.class.getName());
        logger.info("Loading data from config");
        Properties properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream("src/main/resources/app.config");
            properties.load(fileInputStream);
            kafkaTopicName = properties.getProperty("kafka.topicname") + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            kafkaClientId = properties.getProperty("kafka.clientid");
            kafkaUrl = properties.getProperty("kafka.url");
            sampleDataPath = properties.getProperty("smapledatapath");
            logger.info("Kafka topic name: " + kafkaTopicName);
            logger.info("Kafka client id: " + kafkaClientId);
            logger.info("Kafka url: " + kafkaUrl);
            logger.info("Sample data path: " + sampleDataPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ConfigManager() {
        loadConfig();
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

    public String getSampleDataPath() {
        return sampleDataPath;
    }
}
