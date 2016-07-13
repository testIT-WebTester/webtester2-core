package info.novatec.testit.webtester.junit5.extensions.configuration.valuegetter;

import java.util.Optional;

import info.novatec.testit.webtester.config.Configuration;


/**
 * {@link TypedConfigurationValueGetter} which returns {@link Float} values.
 *
 * @see TypedConfigurationValueGetter
 * @since 2.1
 */
public class FloatConfigurationValueGetter implements TypedConfigurationValueGetter<Float> {

    @Override
    public boolean canHandle(Class<?> type) {
        return Float.class.equals(type);
    }

    @Override
    public Optional<Float> get(Configuration config, String key) {
        return config.getFloatProperty(key);
    }

}
