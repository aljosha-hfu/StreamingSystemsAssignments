package streamingsystems;

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
        Properties properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream("src/main/resources/app.config");
            properties.load(fileInputStream);
            kafkaTopicName = properties.getProperty("kafka.topicname") + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            kafkaClientId = properties.getProperty("kafka.clientid");
            kafkaUrl = properties.getProperty("kafka.url");
            sampleDataPath = properties.getProperty("sampledatapath");
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
