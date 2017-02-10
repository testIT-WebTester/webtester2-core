package info.novatec.testit.webtester.spring4.config;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.SpringBootDependencyInjectionTestExecutionListener;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import info.novatec.testit.webtester.config.Configuration;
import info.novatec.testit.webtester.config.ConfigurationBuilder;
import info.novatec.testit.webtester.config.adapters.LocalFileConfigurationAdapter;
import info.novatec.testit.webtester.config.builders.BaseConfigurationBuilder;
import info.novatec.testit.webtester.spring4.config.adapters.SpringEnvironmentConfigurationAdapter;


@RunWith(Enclosed.class)
public class AdapterIntTest {

    // TODO remove workaround ASAP

    // The annotation:
    // @TestExecutionListeners(SpringBootDependencyInjectionTestExecutionListener.class)
    // is needed because spring does not officially support Mockito 2 and throws an exception
    // in case the default test listeners are used.

    @RunWith(SpringJUnit4ClassRunner.class)
    @TestExecutionListeners(SpringBootDependencyInjectionTestExecutionListener.class)
    @SpringBootTest(classes = ManualAdapterDefinitionTest.TestConfiguration.class)
    public static class ManualAdapterDefinitionTest {

        @Autowired
        Configuration configuration;

        /**
         * This test verifies that properties from the configuration are correctly
         * resolved against the spring environment and the configuration is
         * correctly adapted if a property could be resolved against the
         * environment.
         */
        @Test
        public void propertiesFromSpringContextOverrideExistingValues() {
            assertThat(configuration.getStringProperty("test.property.key1")).hasValue("value 1 from spring environment");
            assertThat(configuration.getStringProperty("test.property.key2")).hasValue("value 2 from webtester");
        }

        @org.springframework.context.annotation.Configuration
        @PropertySource("classpath:spring-environment.properties")
        public static class TestConfiguration {

            @Bean
            public Configuration configuration() {
                BaseConfigurationBuilder builder = new BaseConfigurationBuilder();
                builder.withAdapter(new LocalFileConfigurationAdapter());
                builder.withAdapter(springEnvironmentConfigurationAdapter());
                return builder.build();
            }

            @Bean
            public SpringEnvironmentConfigurationAdapter springEnvironmentConfigurationAdapter() {
                return new SpringEnvironmentConfigurationAdapter();
            }

        }

    }

    @RunWith(SpringJUnit4ClassRunner.class)
    @TestExecutionListeners(SpringBootDependencyInjectionTestExecutionListener.class)
    @SpringBootTest(classes = ConfigurationBuilderFactoryBeanTest.TestConfiguration.class)
    public static class ConfigurationBuilderFactoryBeanTest {

        public static final Logger log = LoggerFactory.getLogger(ConfigurationBuilderFactoryBeanTest.class);

        @Autowired
        ConfigurationBuilder builder;
        @Autowired
        ConfigurationBuilder additionalBuild;

        @Test
        public void propertiesFromSpringContextOverrideExistingValues() {
            Configuration configuration = builder.build();
            assertThat(configuration.getStringProperty("test.property.key1")).hasValue("value 1 from spring environment");
            assertThat(configuration.getStringProperty("test.property.key2")).hasValue("value 2 from webtester");
        }

        @Test
        public void builderIsASingleton() {
            assertThat(additionalBuild).isSameAs(builder);
        }

        @org.springframework.context.annotation.Configuration
        @PropertySource("classpath:spring-environment.properties")
        public static class TestConfiguration {

            @Bean
            public ConfigurationBuilderFactoryBean configurationBuilder() {
                ConfigurationBuilderFactoryBean bean = new ConfigurationBuilderFactoryBean();
                bean.setAdapters(asList(new LocalFileConfigurationAdapter(), springEnvironmentConfigurationAdapter()));
                bean.setExporters(Collections.singletonList((key, value) -> log.debug("exported: {}={}", key, value)));
                return bean;
            }

            @Bean
            public SpringEnvironmentConfigurationAdapter springEnvironmentConfigurationAdapter() {
                return new SpringEnvironmentConfigurationAdapter();
            }

        }

    }

    @RunWith(SpringJUnit4ClassRunner.class)
    @TestExecutionListeners(SpringBootDependencyInjectionTestExecutionListener.class)
    @SpringBootTest(classes = PrototypeConfigurationBuilderFactoryBeanTest.TestConfiguration.class)
    public static class PrototypeConfigurationBuilderFactoryBeanTest {

        public static final Logger log = LoggerFactory.getLogger(PrototypeConfigurationBuilderFactoryBeanTest.class);

        @Autowired
        ConfigurationBuilder builder;
        @Autowired
        ConfigurationBuilder additionalBuild;

        @Test
        public void propertiesFromSpringContextOverrideExistingValues() {
            Configuration configuration = builder.build();
            assertThat(configuration.getStringProperty("test.property.key1")).hasValue("value 1 from spring environment");
            assertThat(configuration.getStringProperty("test.property.key2")).hasValue("value 2 from webtester");
        }

        @Test
        public void builderIsNotASingleton() {
            assertThat(additionalBuild).isNotSameAs(builder);
        }

        @org.springframework.context.annotation.Configuration
        @PropertySource("classpath:spring-environment.properties")
        public static class TestConfiguration {

            @Bean
            public PrototypeConfigurationBuilderFactoryBean configurationBuilder() {
                PrototypeConfigurationBuilderFactoryBean bean = new PrototypeConfigurationBuilderFactoryBean();
                bean.setAdapters(asList(new LocalFileConfigurationAdapter(), springEnvironmentConfigurationAdapter()));
                return bean;
            }

            @Bean
            public SpringEnvironmentConfigurationAdapter springEnvironmentConfigurationAdapter() {
                return new SpringEnvironmentConfigurationAdapter();
            }

        }

    }

    @RunWith(SpringJUnit4ClassRunner.class)
    @TestExecutionListeners(SpringBootDependencyInjectionTestExecutionListener.class)
    @SpringBootTest(classes = DefaultSpringConfigurationFactoryBeanTest.TestConfiguration.class)
    public static class DefaultSpringConfigurationFactoryBeanTest {

        @Autowired
        Configuration configuration;
        @Autowired
        Configuration configurationBuild;

        @Test
        public void propertiesFromSpringContextOverrideExistingValues() {
            assertThat(configuration.getStringProperty("test.property.key1")).hasValue("value 1 from spring environment");
            assertThat(configuration.getStringProperty("test.property.key2")).hasValue("value 2 from webtester");
        }

        @Test
        public void configurationIsASingleton() {
            assertThat(configurationBuild).isSameAs(configuration);
        }

        @org.springframework.context.annotation.Configuration
        @PropertySource("classpath:spring-environment.properties")
        public static class TestConfiguration {

            @Bean
            public DefaultSpringConfigurationFactoryBean configurationBuilder() {
                return new DefaultSpringConfigurationFactoryBean();
            }

        }

    }

}
