package info.novatec.testit.webtester.spring5.config;

import info.novatec.testit.webtester.spring5.config.adapters.SpringEnvironmentConfigurationAdapter;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

import info.novatec.testit.webtester.config.Configuration;
import info.novatec.testit.webtester.config.builders.DefaultConfigurationBuilder;


/**
 * This {@link FactoryBean factory bean} creates a default {@link Configuration
 * configuration} based on a {@link DefaultConfigurationBuilder default
 * configuration builder} and adding the
 * {@link SpringEnvironmentConfigurationAdapter spring environment adapter}.
 * <p>
 * The resulting configuration will be populated by using the following
 * adapters:
 * <ol>
 * <li>default adapters - see {@link DefaultConfigurationBuilder}</li>
 * <li>{@link SpringEnvironmentConfigurationAdapter}</li>
 * </ol>
 *
 * @since 2.7
 */
public class DefaultSpringConfigurationFactoryBean
    implements FactoryBean<Configuration>, InitializingBean, EnvironmentAware {

    private Environment environment;
    private Configuration configuration;

    @Override
    public void afterPropertiesSet() {
        configuration =
            new DefaultConfigurationBuilder().withAdapter(new SpringEnvironmentConfigurationAdapter(environment)).build();
    }

    @Override
    public Configuration getObject() {
        return configuration;
    }

    @Override
    public Class<Configuration> getObjectType() {
        return Configuration.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

}
