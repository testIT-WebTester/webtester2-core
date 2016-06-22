package info.novatec.testit.webtester.config.adapters;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import lombok.extern.slf4j.Slf4j;

import info.novatec.testit.webtester.config.Configuration;
import info.novatec.testit.webtester.config.ConfigurationAdapter;


/**
 * Base implementation of a {@link ConfigurationAdapter configuration adapter}
 * using a {@link Properties properties} file from the classpath as its source.
 * If the file doesn't exist the adaptation will be canceled and a warning will
 * be logged.
 *
 * @see ConfigurationAdapter
 * @see AbstractPropertiesConfigurationAdapter
 * @since 2.0
 */
@Slf4j
public class ClasspathPropertiesFileConfigurationAdapter extends AbstractPropertiesConfigurationAdapter {

    private String propertyFilePath;

    public ClasspathPropertiesFileConfigurationAdapter(String propertyFilePath) {
        this.propertyFilePath = propertyFilePath;
    }

    @Override
    public boolean adapt(Configuration configuration) {

        log.debug("loading properties from classpath resource: {}", propertyFilePath);

        URL resource = getClass().getClassLoader().getResource(propertyFilePath);
        if (resource == null) {
            log.warn("Could not load configuration file! {} file is not on the classpath.", propertyFilePath);
            return false;
        }

        Properties properties = new Properties();

        try {
            loadPropertiesFromResource(resource, properties);
        } catch (IOException e) {
            log.error("exception while loading property file " + propertyFilePath, e);
            return false;
        }

        log.debug("...merging with current configuration...");
        copyInto(properties, configuration);
        log.debug("finished loading properties from classpath resource: {}", propertyFilePath);

        return true;

    }

}
