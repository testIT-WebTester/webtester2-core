package info.novatec.testit.webtester.junit5.extensions.configuration.valuegetter;

import java.util.Optional;

import info.novatec.testit.webtester.config.Configuration;


/**
 * {@link TypedConfigurationValueGetter} which returns {@link Double} values.
 *
 * @see TypedConfigurationValueGetter
 * @since 2.1
 */
public class DoubleConfigurationValueGetter implements TypedConfigurationValueGetter<Double> {

    @Override
    public boolean canHandle(Class<?> type) {
        return Double.class.equals(type);
    }

    @Override
    public Optional<Double> get(Configuration config, String key) {
        return config.getDoubleProperty(key);
    }

}
