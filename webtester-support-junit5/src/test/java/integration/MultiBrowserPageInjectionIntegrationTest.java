package integration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.junit5.extensions.browsers.Managed;
import info.novatec.testit.webtester.junit5.extensions.pages.Initialized;
import info.novatec.testit.webtester.pages.Page;


public class MultiBrowserPageInjectionIntegrationTest extends BaseIntegrationTest {

    @Managed("primary")
    Browser primaryBrowser;
    @Managed("secondary")
    Browser secondaryBrowser;

    @Initialized(source = "primary")
    Page primaryPage;
    @Initialized(source = "secondary")
    Page secondaryPage;

    @Test
    @DisplayName("@Initialized can be used to inject pages from different browsers")
    void pageInjectionFromMultipleBrowsers() {
        assertThat(primaryPage).isNotNull();
        assertThat(secondaryPage).isNotNull();
        assertThat(primaryPage.browser()).isSameAs(primaryBrowser);
        assertThat(secondaryPage.browser()).isSameAs(secondaryBrowser);
    }

}
