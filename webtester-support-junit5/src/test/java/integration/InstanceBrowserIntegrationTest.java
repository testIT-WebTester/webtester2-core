package integration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.junit5.extensions.browsers.Managed;


public class InstanceBrowserIntegrationTest extends BaseIntegrationTest {

    @Managed
    Browser browser;

    @Test
    @DisplayName("@Managed on instance field injects new browser")
    void instanceBrowserInjection() {
        assertThat(browser).isNotNull();
    }

}
