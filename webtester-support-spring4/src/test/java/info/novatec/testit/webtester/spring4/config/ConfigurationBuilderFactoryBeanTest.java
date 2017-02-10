package info.novatec.testit.webtester.spring4.config;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;

import info.novatec.testit.webtester.config.Configuration;
import info.novatec.testit.webtester.config.ConfigurationAdapter;
import info.novatec.testit.webtester.config.ConfigurationBuilder;
import info.novatec.testit.webtester.config.ConfigurationExporter;


class ConfigurationBuilderFactoryBeanTest {

    ConfigurationBuilderFactoryBean cut = new ConfigurationBuilderFactoryBean();

    @Test
    void configuredAdaptersAreUsed() {

        ConfigurationAdapter adapter1 = mock(ConfigurationAdapter.class);
        ConfigurationAdapter adapter2 = mock(ConfigurationAdapter.class);
        cut.setAdapters(asList(adapter1, adapter2));
        cut.afterPropertiesSet();

        Configuration configuration = cut.getObject().build();

        verify(adapter1).adapt(configuration);
        verify(adapter2).adapt(configuration);

    }

    @Test
    void configuredExportersAreUsed() {

        ConfigurationExporter exporter1 = mock(ConfigurationExporter.class);
        ConfigurationExporter exporter2 = mock(ConfigurationExporter.class);
        cut.setExporters(asList(exporter1, exporter2));
        cut.afterPropertiesSet();

        cut.getObject().build().setProperty("foo", "bar");

        verify(exporter1).export("foo", "bar");
        verify(exporter2).export("foo", "bar");

    }

    @Test
    void producesSingletonBeans() {
        ConfigurationBuilder builder1 = cut.getObject();
        ConfigurationBuilder builder2 = cut.getObject();
        assertThat(builder1).isSameAs(builder2);
    }

}
