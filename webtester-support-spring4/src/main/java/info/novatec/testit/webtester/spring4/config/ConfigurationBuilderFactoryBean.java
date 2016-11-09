package info.novatec.testit.webtester.spring4.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import info.novatec.testit.webtester.config.ConfigurationAdapter;
import info.novatec.testit.webtester.config.ConfigurationBuilder;
import info.novatec.testit.webtester.config.ConfigurationExporter;
import info.novatec.testit.webtester.config.builders.BaseConfigurationBuilder;


/**
 * This {@link FactoryBean factory bean} can be used to initialize a
 * <b>singleton</b> {@link ConfigurationBuilder configuration builder}. It is
 * mainly intended to be used in XML based spring projects. If your project uses
 * Java based Spring configuration, initializing a
 * {@link BaseConfigurationBuilder base configuration builder} directly might be
 * easier.
 * <p>
 * You can set configuration {@link ConfigurationAdapter adapters} and
 * {@link ConfigurationExporter exporters} to be used when creating the builder
 * instance.
 *
 * @see #setAdapters(List)
 * @see #setExporters(List)
 * @see ConfigurationBuilder
 * @see ConfigurationAdapter
 * @see ConfigurationExporter
 * @see BaseConfigurationBuilder
 * @since 2.0
 */
public class ConfigurationBuilderFactoryBean implements FactoryBean<ConfigurationBuilder>, InitializingBean {

    private List<ConfigurationAdapter> adapters = new ArrayList<>();
    private List<ConfigurationExporter> exporters = new ArrayList<>();
    private ConfigurationBuilder configurationBuilder;

    @Override
    public void afterPropertiesSet() {
        configurationBuilder = new BaseConfigurationBuilder().withAdapters(adapters).withExporters(exporters);
    }

    @Override
    public ConfigurationBuilder getObject() {
        return configurationBuilder;
    }

    @Override
    public Class<ConfigurationBuilder> getObjectType() {
        return ConfigurationBuilder.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    /**
     * Sets the {@link ConfigurationAdapter adapters} to use when initializing
     * the {@link ConfigurationBuilder builder}.
     *
     * @param adapters the adapters
     */
    public void setAdapters(List<ConfigurationAdapter> adapters) {
        this.adapters = adapters;
    }

    /**
     * Sets the {@link ConfigurationExporter exporters} to use when initializing
     * the {@link ConfigurationBuilder builder}.
     *
     * @param exporters the exporters
     */
    public void setExporters(List<ConfigurationExporter> exporters) {
        this.exporters = exporters;
    }

}
