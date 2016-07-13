package integration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.junit5.extensions.browsers.Managed;


public class MixedBrowsersIntegrationTest extends BaseIntegrationTest {

    @Managed("static-browser")
    static Browser staticBrowser;
    @Managed("instance-browser")
    Browser instanceBrowser;

    @Test
    @DisplayName("Multiple @Managed browsers can coexist")
    void openGoogle() {
        assertThat(staticBrowser).isNotNull();
        assertThat(instanceBrowser).isNotNull();
        assertThat(staticBrowser).isNotSameAs(instanceBrowser);
    }

}
