package info.novatec.testit.webtester.junit5.extensions.configuration.unmarshaller;

import java.util.Optional;

import info.novatec.testit.webtester.config.Configuration;


/**
 * {@link ConfigurationUnmarshaller} which returns {@link Float} values.
 *
 * @see ConfigurationUnmarshaller
 * @since 2.1
 */
public class FloatUnmarshaller implements ConfigurationUnmarshaller<Float> {

    @Override
    public boolean canHandle(Class<?> type) {
        return Float.class.equals(type);
    }

    @Override
    public Optional<Float> unmarshal(Configuration config, String key) {
        return config.getFloatProperty(key);
    }

}
