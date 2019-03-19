package properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class PropertiesController {

    private static Logger log = LoggerFactory.getLogger(PropertiesController.class);
    private static PropertiesController instance = null;
    private static final String DEFAULT_CONFIG = "environment/debug.properties";
    private static final String ENVIRONMENT_CONFIG_SYSTEM_PROPERTY = "env.config";

    private final Properties properties = new Properties();

    private PropertiesController() {
        try {
            if (System.getProperty(ENVIRONMENT_CONFIG_SYSTEM_PROPERTY) == null) {
                loadProperties(DEFAULT_CONFIG);
            } else {
                loadProperties(System.getProperty(ENVIRONMENT_CONFIG_SYSTEM_PROPERTY));
            }
        } catch (final IOException e) {
            throw new IllegalStateException("Failed to load configuration file", e);
        }
    }

    public static String getProperty(final String propertyName) {
        if (instance == null) {
            instance = new PropertiesController();
        }
        return System.getProperty(propertyName, instance.properties.getProperty(propertyName));
    }

    public static Properties getProperties() {
        return instance.properties;
    }

    private void loadProperties(final String resource) throws IOException {
        log.info("Reading config properties: {}", resource);
        final InputStream inputStream = PropertiesController.class.getClassLoader().getResourceAsStream(resource);
        if (inputStream == null) {
            throw new IOException("Unable to open stream for resource " + resource);
        }
        final Properties props = new Properties();
        props.load(inputStream);
        inputStream.close();
        for (final String propertyName : props.stringPropertyNames()) {
            if (propertyName.startsWith("+")) {
                loadProperties(propertyName.substring(1));
            }
        }
        properties.putAll(props);
    }
}
