package info.novatec.testit.webtester.config.adapters;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import info.novatec.testit.webtester.config.Configuration;
import info.novatec.testit.webtester.config.ConfigurationAdapter;


/**
 * Base implementation of a {@link ConfigurationAdapter configuration adapter} using a {@link Properties properties} file
 * from the classpath as its source. If the file doesn't exist the adaptation will be canceled and a warning will be logged.
 *
 * @see ConfigurationAdapter
 * @see AbstractPropertiesConfigurationAdapter
 * @since 2.0
 */
@Slf4j
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ClasspathPropertiesFileConfigurationAdapter extends AbstractPropertiesConfigurationAdapter {

    private static final String FILE_NOT_ON_CLASSPATH =
        "Could not load configuration file: {}! It was not found  on the classpath and will be ignored.";

    private final String propertyFilePath;
    private final boolean required;

    @Override
    public boolean adapt(Configuration configuration) {

        log.debug("loading properties from classpath resource: {}", propertyFilePath);

        URL resource = getClass().getClassLoader().getResource(propertyFilePath);
        if (resource == null) {
            logFileNotOnClasspath();
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

    public void logFileNotOnClasspath() {
        if (required) {
            log.warn(FILE_NOT_ON_CLASSPATH, propertyFilePath);
        } else {
            log.info(FILE_NOT_ON_CLASSPATH, propertyFilePath);
        }
    }

}
