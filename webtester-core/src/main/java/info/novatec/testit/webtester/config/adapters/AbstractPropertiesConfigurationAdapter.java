package info.novatec.testit.webtester.config.adapters;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;

import com.google.common.base.Charsets;

import info.novatec.testit.webtester.config.Configuration;
import info.novatec.testit.webtester.config.ConfigurationAdapter;


/**
 * Abstract base implementation of a {@link ConfigurationAdapter} using a {@link Properties properties} object as its source.
 *
 * @see ConfigurationAdapter
 * @since 2.0
 */
abstract class AbstractPropertiesConfigurationAdapter implements ConfigurationAdapter {

    void loadPropertiesFromResource(URL resource, Properties properties) throws IOException {
        try (InputStreamReader isr = new InputStreamReader(resource.openStream(), Charsets.UTF_8)) {
            properties.load(isr);
        }
    }

    void copyInto(Properties source, Configuration target) {
        source.stringPropertyNames().forEach(key -> target.setProperty(key, source.getProperty(key)));
    }

}
