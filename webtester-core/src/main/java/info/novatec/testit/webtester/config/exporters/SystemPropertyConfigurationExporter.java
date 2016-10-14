package info.novatec.testit.webtester.config.exporters;

import org.apache.commons.lang.StringUtils;

import lombok.extern.slf4j.Slf4j;

import info.novatec.testit.webtester.config.ConfigurationExporter;


/**
 * This {@link ConfigurationExporter} exports changed properties as system properties using
 * {@link System#setProperty(String, String)}.
 *
 * @see ConfigurationExporter
 * @since 2.0
 */
@Slf4j
public class SystemPropertyConfigurationExporter implements ConfigurationExporter {

    @Override
    public void export(String key, Object value) {
        String stringValue = value != null ? String.valueOf(value) : null;
        if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(stringValue)) {
            System.setProperty(key, String.valueOf(value));
            log.debug("exposed configuration key '{}' with value '{}' as system property", key, value);
        }
    }

}
