package info.novatec.testit.webtester.config.builders;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import info.novatec.testit.webtester.config.BaseConfiguration;
import info.novatec.testit.webtester.config.Configuration;
import info.novatec.testit.webtester.config.ConfigurationAdapter;
import info.novatec.testit.webtester.config.ConfigurationBuilder;
import info.novatec.testit.webtester.config.ConfigurationExporter;


/**
 * This is a {@link ConfigurationBuilder builder} used to construct {@link BaseConfiguration base configuration} instances.
 * No default {@link ConfigurationAdapter adapters} or {@link ConfigurationExporter exporters} are set at any time.
 *
 * @see Configuration
 * @see BaseConfiguration
 * @see ConfigurationAdapter
 * @see ConfigurationExporter
 * @since 2.0
 */
@Slf4j
public class BaseConfigurationBuilder implements ConfigurationBuilder {

    private final List<ConfigurationAdapter> adapters = new LinkedList<>();
    private final List<ConfigurationExporter> exporters = new LinkedList<>();

    public BaseConfigurationBuilder() {
        log.trace("started the build of a new configuration");
    }

    @Override
    public ConfigurationBuilder withAdapter(ConfigurationAdapter adapterToAdd) {
        adapters.add(adapterToAdd);
        log.trace("added adapter to builder: {}", adapterToAdd);
        return this;
    }

    @Override
    public ConfigurationBuilder withAdapters(ConfigurationAdapter... adaptersToAdd) {
        return withAdapters(Arrays.asList(adaptersToAdd));
    }

    @Override
    public ConfigurationBuilder withAdapters(Collection<ConfigurationAdapter> adaptersToAdd) {
        adapters.addAll(adaptersToAdd);
        log.trace("added adapters to builder: {}", adaptersToAdd);
        return this;
    }

    @Override
    public ConfigurationBuilder withExporter(ConfigurationExporter exporterToAdd) {
        exporters.add(exporterToAdd);
        log.trace("added exporter to builder: {}", exporterToAdd);
        return this;
    }

    @Override
    public ConfigurationBuilder withExporters(ConfigurationExporter... exportersToAdd) {
        return withExporters(Arrays.asList(exportersToAdd));
    }

    @Override
    public ConfigurationBuilder withExporters(Collection<ConfigurationExporter> exportersToAdd) {
        exporters.addAll(exportersToAdd);
        log.trace("added exporters to builder: {}", exportersToAdd);
        return this;
    }

    @Override
    public Configuration build() {
        log.trace("building new configuration using: {}", Configuration.class);
        BaseConfiguration configuration = new BaseConfiguration();
        adaptConfiguration(configuration);
        exportConfiguration(configuration);
        return configuration;
    }

    private void adaptConfiguration(BaseConfiguration configuration) {
        log.trace("adapting configuration using: {}", adapters);
        adapters.forEach(adapter -> {
            adapter.adapt(configuration);
            log.trace("adapted configuration using: {}", adapter);
        });
    }

    private void exportConfiguration(BaseConfiguration configuration) {

        log.trace("exporting properties of configuration using: {}", exporters);

        /* set exporters on configuration to keep being up to date with
         * configuration changes */
        configuration.addExporters(exporters);

        /* export finished configuration once through all exporters */
        configuration.getKeys()
            .stream()
            .sorted()
            .forEach(key -> exporters.forEach(exporter -> exporter.export(key, configuration.getProperty(key, null))));

    }

}
