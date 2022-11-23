package streamingsystems;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ConfigManagerTest {
    @Test
    public void testConfigManager() {
        assertNotNull(ConfigManager.INSTANCE.getRabbitMqHost());
        assertNotNull(ConfigManager.INSTANCE.getRabbitMqUser());
        assertNotNull(ConfigManager.INSTANCE.getRabbitMqPassword());
    }

}