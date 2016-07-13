package info.novatec.testit.webtester.junit5.extensions.configuration.valuegetter;

import java.util.Optional;

import info.novatec.testit.webtester.config.Configuration;


/**
 * {@link TypedConfigurationValueGetter} which returns {@link Boolean} values.
 *
 * @see TypedConfigurationValueGetter
 * @since 2.1
 */
public class BooleanConfigurationValueGetter implements TypedConfigurationValueGetter<Boolean> {

    @Override
    public boolean canHandle(Class<?> type) {
        return Boolean.class.equals(type);
    }

    @Override
    public Optional<Boolean> get(Configuration config, String key) {
        return config.getBooleanProperty(key);
    }

}
