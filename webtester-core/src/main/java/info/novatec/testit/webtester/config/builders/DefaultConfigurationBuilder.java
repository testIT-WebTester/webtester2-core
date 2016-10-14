package info.novatec.testit.webtester.config.builders;

import info.novatec.testit.webtester.config.BaseConfiguration;
import info.novatec.testit.webtester.config.Configuration;
import info.novatec.testit.webtester.config.ConfigurationAdapter;
import info.novatec.testit.webtester.config.ConfigurationBuilder;
import info.novatec.testit.webtester.config.ConfigurationExporter;
import info.novatec.testit.webtester.config.adapters.DefaultFileConfigurationAdapter;
import info.novatec.testit.webtester.config.adapters.GlobalFileConfigurationAdapter;
import info.novatec.testit.webtester.config.adapters.LocalFileConfigurationAdapter;


/**
 * This is a {@link ConfigurationBuilder builder} used to construct {@link BaseConfiguration base configuration} instances
 * using the frameworks default set of {@link ConfigurationAdapter adapters}. No default {@link ConfigurationExporter
 * exporters} are set.
 * <p>
 * <b>The default adapters are as follows:</b>
 * <ol>
 * <li>{@link DefaultFileConfigurationAdapter}</li>
 * <li>{@link GlobalFileConfigurationAdapter}</li>
 * <li>{@link LocalFileConfigurationAdapter}</li>
 * </ol>
 *
 * @see Configuration
 * @see BaseConfiguration
 * @see ConfigurationAdapter
 * @see ConfigurationExporter
 * @since 2.0
 */
public class DefaultConfigurationBuilder extends BaseConfigurationBuilder {

    public DefaultConfigurationBuilder() {
        withAdapter(new DefaultFileConfigurationAdapter());
        withAdapter(new GlobalFileConfigurationAdapter());
        withAdapter(new LocalFileConfigurationAdapter());
    }

}
