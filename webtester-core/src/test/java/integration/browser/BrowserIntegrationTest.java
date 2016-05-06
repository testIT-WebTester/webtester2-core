package integration.browser;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import integration.BaseIntegrationTest;


public class BrowserIntegrationTest extends BaseIntegrationTest {

    @Test
    public void titleOfPageCanBeReturned() {
        browser().open(getUrlFor("html/browser/pageWithTitle.html"));
        String title = browser().currentPageTitle();
        assertThat(title).isEqualTo("Page Title");
    }

    @Test
    public void idThePageHasNoTitleAnEmptyOptionalIsReturnedAsTitle() {
        browser().open(getUrlFor("html/browser/pageWithoutTitle.html"));
        String title = browser().currentPageTitle();
        assertThat(title).isEmpty();
    }

    @Test
    public void currentUrlCanBeReturned() {
        browser().open(getUrlFor("html/browser/empty.html"));
        String actualUrl = browser().currentUrl();
        assertThat(actualUrl).isEqualTo(getUrlFor("html/browser/empty.html"));
    }

}
