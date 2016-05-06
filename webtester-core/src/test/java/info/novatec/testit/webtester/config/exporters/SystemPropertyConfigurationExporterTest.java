package info.novatec.testit.webtester.config.exporters;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.Test;


public class SystemPropertyConfigurationExporterTest {

    String key = UUID.randomUUID().toString();
    SystemPropertyConfigurationExporter cut = new SystemPropertyConfigurationExporter();

    @Test
    public void propertiesAreExportedAsSystemProperties() {
        cut.export(key, "fooValue");
        assertThat(getSystemProperty()).isEqualTo("fooValue");
    }

    @Test
    public void propertiesWithNullValueAreNotExportedAsSystemProperties() {
        cut.export(key, null);
        assertThat(getSystemProperty()).isNull();
    }

    @Test
    public void propertiesWithEmptyValueAreNotExportedAsSystemProperties() {
        cut.export(key, "");
        assertThat(getSystemProperty()).isNull();
    }

    @Test
    public void propertiesWithBlankValueAreNotExportedAsSystemProperties() {
        cut.export(key, "  ");
        assertThat(getSystemProperty()).isNull();
    }

    String getSystemProperty() {
        return System.getProperty(key);
    }

}
