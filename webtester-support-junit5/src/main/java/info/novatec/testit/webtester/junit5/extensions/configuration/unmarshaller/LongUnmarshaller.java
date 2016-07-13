package info.novatec.testit.webtester.junit5.extensions.configuration.unmarshaller;

import java.util.Optional;

import info.novatec.testit.webtester.config.Configuration;


/**
 * {@link ConfigurationUnmarshaller} which returns {@link Long} values.
 *
 * @see ConfigurationUnmarshaller
 * @since 2.1
 */
public class LongUnmarshaller implements ConfigurationUnmarshaller<Long> {

    @Override
    public boolean canHandle(Class<?> type) {
        return Long.class.equals(type);
    }

    @Override
    public Optional<Long> unmarshal(Configuration config, String key) {
        return config.getLongProperty(key);
    }

}
