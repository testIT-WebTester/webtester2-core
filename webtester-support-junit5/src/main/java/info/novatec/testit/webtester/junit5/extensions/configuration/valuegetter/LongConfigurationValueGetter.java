package info.novatec.testit.webtester.junit5.extensions.configuration.valuegetter;

import java.util.Optional;

import info.novatec.testit.webtester.config.Configuration;


/**
 * {@link TypedConfigurationValueGetter} which returns {@link Long} values.
 *
 * @see TypedConfigurationValueGetter
 * @since 2.1
 */
public class LongConfigurationValueGetter implements TypedConfigurationValueGetter<Long> {

    @Override
    public boolean canHandle(Class<?> type) {
        return Long.class.equals(type);
    }

    @Override
    public Optional<Long> get(Configuration config, String key) {
        return config.getLongProperty(key);
    }

}
