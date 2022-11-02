
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public enum ConfigManager {
    INSTANCE;

    private String rabbitMqHost;
    private String rabbitMqUser;
    private String rabbitMqPassword;

    public void loadConfig() {
        Properties properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream("src/main/resources/app.config");
            properties.load(fileInputStream);
            rabbitMqHost = properties.getProperty("rabbitmq.host");
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

    public String getRabbitMqUser() {
        return rabbitMqUser;
    }

    public String getRabbitMqPassword() {
        return rabbitMqPassword;
    }
}
