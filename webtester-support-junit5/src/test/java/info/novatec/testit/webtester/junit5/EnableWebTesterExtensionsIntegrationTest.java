package info.novatec.testit.webtester.junit5;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;

import utils.TestBrowserFactory;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.junit5.extensions.browsers.CreateBrowsersUsing;
import info.novatec.testit.webtester.junit5.extensions.browsers.EntryPoint;
import info.novatec.testit.webtester.junit5.extensions.browsers.Managed;
import info.novatec.testit.webtester.junit5.extensions.configuration.ConfigurationValue;
import info.novatec.testit.webtester.junit5.extensions.pages.Initialized;
import info.novatec.testit.webtester.pages.Page;


/**
 * This integration test test all of the features provided by our JUnit 5 extensions in one big test.
 * It's very much a happy path test since all border cases are already tested in the integration test for each extension.
 * The focus of this test is on checking if there are any problems if all extensions are used in combination.
 */
@EnableWebTesterExtensions
@CreateBrowsersUsing(TestBrowserFactory.class)
public class EnableWebTesterExtensionsIntegrationTest {

    @Managed("browser-1")
    @EntryPoint("http://${host}:${port}/index1.html")
    Browser browserOne;
    @Managed("browser-2")
    @EntryPoint("http://www.example.com")
    Browser browserTwo;

    @ConfigurationValue(value = "stringValue", source = "browser-1")
    String stringValue;
    @ConfigurationValue(value = "booleanValue", source = "browser-2")
    Boolean booleanValue;

    @Initialized(source = "browser-1")
    TestPage pageOne;
    @Initialized(source = "browser-2")
    TestPage pageTwo;

    @Test
    void browserUrlsAreInvoked() {
        verify(browserOne.webDriver()).get("http://localhost:8080/index1.html");
        verify(browserTwo.webDriver()).get("http://www.example.com");
    }

    @Test
    void configurationValuesAreInjected() {
        assertThat(stringValue).isEqualTo("Hello World!");
        assertThat(booleanValue).isTrue();
    }

    @Test
    void pagesAreInitialized() {
        assertThat(pageOne.browser()).isSameAs(browserOne);
        assertThat(pageTwo.browser()).isSameAs(browserTwo);
    }

    public interface TestPage extends Page {

    }

}
