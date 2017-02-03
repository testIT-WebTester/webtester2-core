package info.novatec.testit.webtester.internal.configuration;

import java.lang.reflect.Field;

import lombok.extern.slf4j.Slf4j;

import info.novatec.testit.webtester.config.Configuration;
import info.novatec.testit.webtester.config.ConfigurationAdapter;


@Slf4j
public class NamedPropertiesConfigurationAdapter implements ConfigurationAdapter {

    @Override
    public boolean adapt(Configuration configuration) {
        for (NamedProperties property : NamedProperties.values()) {
            tryToSetDefaultValue(configuration, property);
        }
        return true;
    }

    private void tryToSetDefaultValue(Configuration configuration, NamedProperties property) {
        try {
            doSetDefaultValue(configuration, property);
        } catch (NoSuchFieldException e) {
            log.warn("couldn't read default value for {}", property);
            log.debug("exception for previous warning", e);
        }
    }

    private void doSetDefaultValue(Configuration configuration, NamedProperties property) throws NoSuchFieldException {
        Field field = NamedProperties.class.getField(property.name());
        DefaultValue defaultValue = field.getAnnotation(DefaultValue.class);
        if (defaultValue != null) {
            String key = property.getKey();
            String value = defaultValue.value();
            configuration.setProperty(key, value);
        }
    }

}
