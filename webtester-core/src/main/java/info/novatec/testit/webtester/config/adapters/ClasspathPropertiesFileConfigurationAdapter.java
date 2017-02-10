package info.novatec.testit.webtester.config.adapters;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.function.Consumer;

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
public class ClasspathPropertiesFileConfigurationAdapter extends AbstractPropertiesConfigurationAdapter {

    private static final String FILE_NOT_ON_CLASSPATH =
        "{} properties file {} wasn't found on the classpath - it's properties will not be merged into configuration.";

    /**
     * Classification how important the existence of a configuration file is.
     *
     * @see ClasspathPropertiesFileConfigurationAdapter
     * @since 2.1
     */
    protected enum Importance {

        /** Property file is optional - if not found an INFO will be logged. */
        OPTIONAL(path -> {
            log.info(FILE_NOT_ON_CLASSPATH, "Optional", path);
        }),

        /** Property file is recommended - if not found a WARNING will be logged. */
        RECOMMENDED(path -> {
            log.warn(FILE_NOT_ON_CLASSPATH, "Recommended", path);
        }),

        /** Property file is required - if not found an exception will be thrown. */
        REQUIRED(path -> {
            String message = "Required properties file " + path + " wasn't found on the classpath!";
            throw new IllegalStateException(message);
        });

        private final Consumer<String> handler;

        Importance(Consumer<String> handler) {
            this.handler = handler;
        }

        private void handleFileNotFound(String pathToFile) {
            handler.accept(pathToFile);
        }

    }

    private final String propertyFilePath;
    private final Importance importance;

    /**
     * Creates a new instance for the given property file path and a default {@link Importance} of
     * {@link Importance#OPTIONAL}.
     * <p>
     * The property file path must be provided in a format relative to the classpath root.
     * <p>
     * <b>Examples:</b>
     * <ul>
     * <li>my.properties</li>
     * <li>some/folders/within/classpath/my.properties</li>
     * </ul>
     *
     * @param propertyFilePath the path to the property file on the classpath
     * @since 2.0
     */
    public ClasspathPropertiesFileConfigurationAdapter(String propertyFilePath) {
        this(propertyFilePath, Importance.OPTIONAL);
    }

    /**
     * Creates a new instance for the given property file path and a {@link Importance}.
     * <p>
     * The property file path must be provided in a format relative to the classpath root.
     * <p>
     * <b>Examples:</b>
     * <ul>
     * <li>my.properties</li>
     * <li>some/folders/within/classpath/my.properties</li>
     * </ul>
     *
     * @param propertyFilePath the path to the property file on the classpath
     * @param importance the importance of the property file
     * @since 2.0
     */
    public ClasspathPropertiesFileConfigurationAdapter(String propertyFilePath, Importance importance) {
        this.propertyFilePath = propertyFilePath;
        this.importance = importance;
    }

    @Override
    public boolean adapt(Configuration configuration) {

        log.debug("loading properties from classpath resource: {}", propertyFilePath);

        URL resource = getClass().getClassLoader().getResource(propertyFilePath);
        if (resource == null) {
            importance.handleFileNotFound(propertyFilePath);
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
