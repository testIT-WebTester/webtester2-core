package info.novatec.testit.webtester.junit5.extensions.configuration.unmarshaller;

import java.util.Optional;

import info.novatec.testit.webtester.config.Configuration;


/**
 * {@link ConfigurationUnmarshaller} which returns {@link Boolean} values.
 *
 * @see ConfigurationUnmarshaller
 * @since 2.1
 */
public class BooleanUnmarshaller implements ConfigurationUnmarshaller<Boolean> {

    @Override
    public boolean canHandle(Class<?> type) {
        return Boolean.class.equals(type);
    }

    @Override
    public Optional<Boolean> unmarshal(Configuration config, String key) {
        return config.getBooleanProperty(key);
    }

}
