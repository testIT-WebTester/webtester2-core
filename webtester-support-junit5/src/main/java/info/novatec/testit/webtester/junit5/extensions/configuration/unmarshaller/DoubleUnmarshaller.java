package info.novatec.testit.webtester.junit5.extensions.configuration.unmarshaller;

import java.util.Optional;

import info.novatec.testit.webtester.config.Configuration;


/**
 * {@link ConfigurationUnmarshaller} which returns {@link Double} values.
 *
 * @see ConfigurationUnmarshaller
 * @since 2.1
 */
public class DoubleUnmarshaller implements ConfigurationUnmarshaller<Double> {

    @Override
    public boolean canHandle(Class<?> type) {
        return Double.class.equals(type);
    }

    @Override
    public Optional<Double> unmarshal(Configuration config, String key) {
        return config.getDoubleProperty(key);
    }
}
