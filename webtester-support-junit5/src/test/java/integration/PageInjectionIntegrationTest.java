package integration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.junit5.extensions.browsers.Managed;
import info.novatec.testit.webtester.junit5.extensions.pages.Initialized;
import info.novatec.testit.webtester.pages.Page;


public class PageInjectionIntegrationTest extends BaseIntegrationTest {

    @Managed
    Browser browser;

    @Initialized
    Page page;

    @Test
    @DisplayName("@Initialized injects new pages using browser")
    void pageInjection() {
        assertThat(page).isNotNull();
        assertThat(page.browser()).isSameAs(browser);
    }

}
