package info.novatec.testit.webtester.junit5.extensions.configuration.unmarshaller;

import java.util.Optional;

import info.novatec.testit.webtester.config.Configuration;


/**
 * {@link ConfigurationUnmarshaller} which returns {@link Integer} values.
 *
 * @see ConfigurationUnmarshaller
 * @since 2.1
 */
public class IntegerUnmarshaller implements ConfigurationUnmarshaller<Integer> {

    @Override
    public boolean canHandle(Class<?> type) {
        return Integer.class.equals(type);
    }

    @Override
    public Optional<Integer> unmarshal(Configuration config, String key) {
        return config.getIntegerProperty(key);
    }

}
