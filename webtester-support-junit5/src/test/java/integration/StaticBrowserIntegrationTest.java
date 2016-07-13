package integration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.junit5.extensions.browsers.Managed;


public class StaticBrowserIntegrationTest extends BaseIntegrationTest {

    @Managed
    static Browser browser;

    @Test
    @DisplayName("@Managed on static field injects new browser")
    void staticBrowserInjection() {
        assertThat(browser).isNotNull();
    }

}
