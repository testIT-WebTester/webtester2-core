package integration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import utils.TestBrowserFactory;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.junit5.extensions.browsers.CreateUsing;
import info.novatec.testit.webtester.junit5.extensions.browsers.Managed;
import info.novatec.testit.webtester.junit5.extensions.configuration.ConfigurationValue;


public class MultiBrowserConfigurationValueIntegrationTest extends BaseIntegrationTest {

    @Managed("primary")
    @CreateUsing(PrimaryFactory.class)
    Browser primaryBrowser;
    @Managed("secondary")
    @CreateUsing(SecondaryFactory.class)
    Browser secondaryBrowser;

    @ConfigurationValue(value = "my-property", source = "primary")
    String primaryValue;
    @ConfigurationValue(value = "my-property", source = "secondary")
    String secondaryValue;

    @Test
    @DisplayName("@ConfigurationValue cen be used to get configuration values from different browsers")
    void valueInjectionFromMultipleBrowsers() {
        assertThat(primaryValue).isEqualTo("primary-value");
        assertThat(secondaryValue).isEqualTo("secondary-value");
    }

    public static class PrimaryFactory extends TestBrowserFactory {
        @Override
        public Browser createBrowser() {
            Browser browser = super.createBrowser();
            browser.configuration().setProperty("my-property", "primary-value");
            return browser;
        }
    }

    public static class SecondaryFactory extends TestBrowserFactory {
        @Override
        public Browser createBrowser() {
            Browser browser = super.createBrowser();
            browser.configuration().setProperty("my-property", "secondary-value");
            return browser;
        }
    }

}
