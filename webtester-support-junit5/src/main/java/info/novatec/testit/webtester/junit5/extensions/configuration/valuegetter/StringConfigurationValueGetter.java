package info.novatec.testit.webtester.junit5.extensions.configuration.valuegetter;

import java.util.Optional;

import info.novatec.testit.webtester.config.Configuration;


/**
 * {@link TypedConfigurationValueGetter} which returns {@link String} values.
 *
 * @see TypedConfigurationValueGetter
 * @since 2.1
 */
public class StringConfigurationValueGetter implements TypedConfigurationValueGetter<String> {

    @Override
    public boolean canHandle(Class<?> type) {
        return String.class.equals(type);
    }

    @Override
    public Optional<String> get(Configuration config, String key) {
        return config.getStringProperty(key);
    }

}
