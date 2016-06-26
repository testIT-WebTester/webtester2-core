package info.novatec.testit.webtester.browser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.WebDriver;

import utils.TestUtils;
import utils.events.EventCaptor;

import info.novatec.testit.webtester.browser.operations.UrlOpener;
import info.novatec.testit.webtester.config.Configuration;
import info.novatec.testit.webtester.events.browser.ClosedBrowserEvent;
import info.novatec.testit.webtester.internal.PageFactory;
import info.novatec.testit.webtester.pages.Page;


@RunWith(Enclosed.class)
public class WebDriverBrowserTest {

    @RunWith(MockitoJUnitRunner.class)
    public static class BrowserOperations {

        @InjectMocks
        WebDriverBrowser browser;

        @Test
        public void urlOpenerIsReturned() {
            assertThat(browser.open()).isNotNull();
        }

        @Test
        public void currentWindowIsReturned() {
            assertThat(browser.currentWindow()).isNotNull();
        }

        @Test
        public void navigatorIsReturned() {
            assertThat(browser.navigate()).isNotNull();
        }

        @Test
        public void alertHandlerIsReturned() {
            assertThat(browser.alert()).isNotNull();
        }

        @Test
        public void screenshotTakerIsReturned() {
            assertThat(browser.screenshot()).isNotNull();
        }

        @Test
        public void pageSourceSaverIsReturned() {
            assertThat(browser.pageSource()).isNotNull();
        }

        @Test
        public void javaScriptExecutorIsReturned() {
            assertThat(browser.javaScript()).isNotNull();
        }

        @Test
        public void focusSetterIsReturned() {
            assertThat(browser.focus()).isNotNull();
        }

        @Test
        public void eventSystemIsReturned() {
            assertThat(browser.events()).isNotNull();
        }

        @Test
        public void adHocFinderIsReturned() {
            assertThat(browser.finder()).isNotNull();
        }

    }

    @RunWith(MockitoJUnitRunner.class)
    public static class CloseBrowser {

        @Mock
        Configuration configuration;
        @Mock
        WebDriver webDriver;
        @InjectMocks
        WebDriverBrowser browser;

        @Test
        public void callingCloseMultipleTimesOnlyClosesBrowserTheFirstTime() {
            browser.close();
            browser.close();
            verify(webDriver).quit();
            verifyNoMoreInteractions(webDriver);
        }

        @Test
        public void closingBrowserFiresEvent() {
            doReturn(true).when(configuration).isEventSystemEnabled();
            EventCaptor.capture(browser.events(), ClosedBrowserEvent.class)
                .execute(() -> browser.close())
                .assertEventWasFired();
        }

    }

    @RunWith(MockitoJUnitRunner.class)
    public static class CurrentPageTitle {

        @Mock
        WebDriver webDriver;
        @InjectMocks
        WebDriverBrowser browser;

        @Test
        public void pageTitleIsRetrievedFromWebDriver() {
            doReturn("Title").when(webDriver).getTitle();
            assertThat(browser.currentPageTitle()).isEqualTo("Title");
        }

        @Test
        public void nullTitlesAreDefaultedToEmptyString() {
            doReturn(null).when(webDriver).getTitle();
            assertThat(browser.currentPageTitle()).isEqualTo("");
        }

    }

    @RunWith(MockitoJUnitRunner.class)
    public static class CurrentUrl {

        @Mock
        WebDriver webDriver;
        @InjectMocks
        WebDriverBrowser browser;

        @Test
        public void urlIsRetrievedFromWebDriver() {
            doReturn("http://www.foo.com").when(webDriver).getCurrentUrl();
            assertThat(browser.currentUrl()).isEqualTo("http://www.foo.com");
        }

        @Test
        public void nullUrlsAreDefaultedToEmptyString() {
            doReturn(null).when(webDriver).getCurrentUrl();
            assertThat(browser.currentUrl()).isEqualTo("");
        }

    }

    @RunWith(MockitoJUnitRunner.class)
    public static class CreatePage {

        @Mock
        PageFactory pageFactory;
        @InjectMocks
        WebDriverBrowser browser;

        @Test
        public void pagesAreCreatedByTheBrowserPageFactory() {

            TestUtils.setFieldValue(browser, "pageFactory", pageFactory);

            TestPage page = mock(TestPage.class);
            doReturn(page).when(this.pageFactory).page(TestPage.class);

            TestPage actualPage = browser.create(TestPage.class);
            assertThat(actualPage).isSameAs(page);

        }

        public interface TestPage extends Page {

        }

    }

    @RunWith(MockitoJUnitRunner.class)
    public static class DefaultMethods {

        @Mock
        UrlOpener open;
        @InjectMocks
        WebDriverBrowser browser;

        @Test
        public void openingAnUrlInvokesTheOpenUrlOperationClass() {
            TestUtils.setFieldValue(browser, "open", open);
            browser.open("http://www.foo.com");
            verify(open).url("http://www.foo.com");
        }

        @Test
        public void openingAnUrlReturnsSameBrowser() {
            TestUtils.setFieldValue(browser, "open", open);
            Browser returnedBrowser = this.browser.open("http://www.foo.com");
            assertThat(returnedBrowser).isSameAs(browser);
        }

    }

    public static class FactoryAndBuilder {

        @Test
        public void browserCanBeDirectlyBuildFromWebDriver() {

            WebDriver webDriver = mock(WebDriver.class);

            Browser browser = WebDriverBrowser.buildForWebDriver(webDriver);

            assertThat(browser.webDriver()).isSameAs(webDriver);
            assertThat(browser.configuration()).isNotNull();

        }

        @Test
        public void browserBuildCanBeCustomized() {

            WebDriver webDriver = mock(WebDriver.class);
            Configuration configuration = mock(Configuration.class);

            Browser browser = WebDriverBrowser.forWebDriver(webDriver).withConfiguration(configuration).build();

            assertThat(browser.webDriver()).isSameAs(webDriver);
            assertThat(browser.configuration()).isSameAs(configuration);

        }

    }

}
