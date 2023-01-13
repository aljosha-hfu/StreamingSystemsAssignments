package streamingsystems;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ConfigManagerTest {
    @Test
    public void testConfigManager() {
        assertNotNull(ConfigManager.INSTANCE.getKafkaTopicName());
        assertNotNull(ConfigManager.INSTANCE.getKafkaClientId());
        assertNotNull(ConfigManager.INSTANCE.getKafkaUrl());
    }

}