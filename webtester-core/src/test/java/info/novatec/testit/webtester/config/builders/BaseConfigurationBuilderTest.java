package info.novatec.testit.webtester.config.builders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Test;
import org.mockito.InOrder;

import info.novatec.testit.webtester.config.BaseConfiguration;
import info.novatec.testit.webtester.config.Configuration;
import info.novatec.testit.webtester.config.ConfigurationAdapter;
import info.novatec.testit.webtester.config.ConfigurationExporter;
import info.novatec.testit.webtester.config.builders.BaseConfigurationBuilder;


public class BaseConfigurationBuilderTest {

    @Test
    public void baseConfigurationBuilderBuildsInstancesOfBaseConfiguration() {
        Configuration configuration = new BaseConfigurationBuilder().build();
        assertThat(configuration).isInstanceOf(BaseConfiguration.class);
    }

    @Test
    public void singleAdapterCanBeUsed() {

        ConfigurationAdapter adapter = mock(ConfigurationAdapter.class);

        Configuration configuration = new BaseConfigurationBuilder()
            .withAdapter(adapter)
            .build();

        verify(adapter).adapt(configuration);
        verifyNoMoreInteractions(adapter);

    }

    @Test
    public void multipleAdaptersAreUsedInOrder() {

        ConfigurationAdapter adapter1 = mock(ConfigurationAdapter.class);
        ConfigurationAdapter adapter2 = mock(ConfigurationAdapter.class);

        Configuration configuration = new BaseConfigurationBuilder()
            .withAdapter(adapter1)
            .withAdapter(adapter2)
            .build();

        InOrder inOrder = inOrder(adapter1, adapter2);
        inOrder.verify(adapter1).adapt(configuration);
        inOrder.verify(adapter2).adapt(configuration);
        inOrder.verifyNoMoreInteractions();

    }

    @Test
    public void singleExporterCanBeUsed() {

        ConfigurationAdapter adapter = config -> {
            config.setProperty("foo", "value");
            return true;
        };
        ConfigurationExporter exporter = mock(ConfigurationExporter.class);

        new BaseConfigurationBuilder()
            .withAdapter(adapter)
            .withExporter(exporter)
            .build();

        verify(exporter).export("foo", "value");
        verifyNoMoreInteractions(exporter);

    }

    @Test
    public void multipleExportersAreUsedInOrder() {

        ConfigurationAdapter adapter = config -> {
            config.setProperty("foo", "value");
            return true;
        };
        ConfigurationExporter exporter1 = mock(ConfigurationExporter.class);
        ConfigurationExporter exporter2 = mock(ConfigurationExporter.class);

        new BaseConfigurationBuilder()
            .withAdapter(adapter)
            .withExporter(exporter1)
            .withExporter(exporter2)
            .build();

        InOrder inOrder = inOrder(exporter1, exporter2);
        inOrder.verify(exporter1).export("foo", "value");
        inOrder.verify(exporter2).export("foo", "value");
        inOrder.verifyNoMoreInteractions();

    }

    @Test
    public void adaptersAreExecutedBeforeExporters() {

        ConfigurationAdapter adapter = config -> {
            config.setProperty("foo", "value");
            return true;
        };

        ConfigurationAdapter adapter1 = mock(ConfigurationAdapter.class);
        ConfigurationAdapter adapter2 = mock(ConfigurationAdapter.class);
        ConfigurationExporter exporter1 = mock(ConfigurationExporter.class);
        ConfigurationExporter exporter2 = mock(ConfigurationExporter.class);

        Configuration configuration = new BaseConfigurationBuilder()
            .withAdapter(adapter)
            .withAdapters(adapter1, adapter2)
            .withExporters(exporter1, exporter2)
            .build();

        InOrder inOrder = inOrder(adapter1, adapter2, exporter1, exporter2);
        inOrder.verify(adapter1).adapt(configuration);
        inOrder.verify(adapter2).adapt(configuration);
        inOrder.verify(exporter1).export("foo", "value");
        inOrder.verify(exporter2).export("foo", "value");
        inOrder.verifyNoMoreInteractions();

    }

}
