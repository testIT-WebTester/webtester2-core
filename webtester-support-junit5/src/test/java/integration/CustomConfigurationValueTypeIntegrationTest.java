package integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import lombok.AllArgsConstructor;
import lombok.Data;
import utils.TestBrowserFactory;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.config.Configuration;
import info.novatec.testit.webtester.junit5.extensions.browsers.CreateUsing;
import info.novatec.testit.webtester.junit5.extensions.browsers.Managed;
import info.novatec.testit.webtester.junit5.extensions.configuration.ConfigurationValue;
import info.novatec.testit.webtester.junit5.extensions.configuration.valuegetter.TypedConfigurationValueGetter;


public class CustomConfigurationValueTypeIntegrationTest extends BaseIntegrationTest {

    @Managed
    @CreateUsing(Factory.class)
    Browser browser;

    @ConfigurationValue(value = "my-property", using = CustomValueGetter.class)
    MyValueType value;

    @Test
    @DisplayName("@ConfigurationValue can use custom getter to resolve injectable value")
    void customConfigurationValueCanBeInjected() {
        assertThat(value.getValue()).isEqualTo("my-value");
    }

    public static class Factory extends TestBrowserFactory {
        @Override
        public Browser createBrowser() {
            Browser browser = super.createBrowser();
            browser.configuration().setProperty("my-property", "my-value");
            return browser;
        }
    }

    @Data
    @AllArgsConstructor
    public static class MyValueType {
        private String value;
    }

    public static class CustomValueGetter implements TypedConfigurationValueGetter {

        @Override
        public boolean canHandle(Class type) {
            return MyValueType.class.equals(type);
        }

        @Override
        public Optional<MyValueType> get(Configuration configuration, String key) {
            return configuration.getStringProperty(key).map(MyValueType::new);
        }
    }

}
