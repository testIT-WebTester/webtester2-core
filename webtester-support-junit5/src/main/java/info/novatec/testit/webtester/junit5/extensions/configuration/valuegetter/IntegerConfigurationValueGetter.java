package info.novatec.testit.webtester.junit5.extensions.configuration.valuegetter;

import java.util.Optional;

import info.novatec.testit.webtester.config.Configuration;


/**
 * {@link TypedConfigurationValueGetter} which returns {@link Integer} values.
 *
 * @see TypedConfigurationValueGetter
 * @since 2.1
 */
public class IntegerConfigurationValueGetter implements TypedConfigurationValueGetter<Integer> {

    @Override
    public boolean canHandle(Class<?> type) {
        return Integer.class.equals(type);
    }

    @Override
    public Optional<Integer> get(Configuration config, String key) {
        return config.getIntegerProperty(key);
    }

}
