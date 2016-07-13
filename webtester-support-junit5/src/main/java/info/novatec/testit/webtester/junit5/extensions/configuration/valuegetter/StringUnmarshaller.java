package info.novatec.testit.webtester.junit5.extensions.configuration.valuegetter;

import java.util.Optional;

import info.novatec.testit.webtester.config.Configuration;


/**
 * {@link ConfigurationUnmarshaller} which returns {@link String} values.
 *
 * @see ConfigurationUnmarshaller
 * @since 2.1
 */
public class StringUnmarshaller implements ConfigurationUnmarshaller<String> {

    @Override
    public boolean canHandle(Class<?> type) {
        return String.class.equals(type);
    }

    @Override
    public Optional<String> unmarshal(Configuration config, String key) {
        return config.getStringProperty(key);
    }

}
