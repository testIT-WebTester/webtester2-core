package integration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.junit5.extensions.browsers.Managed;
import info.novatec.testit.webtester.junit5.extensions.configuration.ConfigurationValue;


public class ConfigurationValueIntegrationTest extends BaseIntegrationTest {

    @Managed
    Browser browser;

    @ConfigurationValue("markings.read.background")
    String value;

    @Test
    @DisplayName("@ConfigurationValue can be injected from single browser")
    void configurationValueCanBeInjectedFromSingleBrowser() {
        String expectedValue = browser.configuration().getStringProperty("markings.read.background").get();
        assertThat(value).isEqualTo(expectedValue);
    }

}
