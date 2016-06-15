package info.novatec.testit.webtester.browser.operations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.net.URL;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.WebDriver;

import utils.TestUtils;
import utils.events.EventCaptor;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.config.Configuration;
import info.novatec.testit.webtester.events.EventSystem;
import info.novatec.testit.webtester.events.EventSystemImpl;
import info.novatec.testit.webtester.events.browser.OpenedUrlEvent;
import info.novatec.testit.webtester.pages.Page;


@RunWith(MockitoJUnitRunner.class)
public class UrlOpenerTest {

    static final String URL = "http://www.examples.com";
    static final URL URL_OBJECT = TestUtils.toUrl(URL);

    @Captor
    ArgumentCaptor<OpenedUrlEvent> captor;

    @Mock
    Configuration configuration;
    @Mock
    WebDriver webDriver;

    EventSystem eventSystem;

    UrlOpener cut;

    @Before
    public void init() {

        Browser browser = mock(Browser.class);

        eventSystem = spy(new EventSystemImpl(browser));

        doReturn(configuration).when(browser).configuration();
        doReturn(webDriver).when(browser).webDriver();
        doReturn(eventSystem).when(browser).events();
        doAnswer(invocation -> mock(( Class ) invocation.getArguments()[0])).when(browser).create(any());

        doReturn(true).when(configuration).isEventSystemEnabled();

        cut = new UrlOpener(browser);

        setDefaultEntryPoint(URL);

    }

    /* default entry point */

    @Test
    public void defaultEntryPointCanBeOpened() {
        executeDefaultEntryPoint();
        verifyWebDriverNavigationToUrl();
    }

    @Test(expected = IllegalStateException.class)
    public void emptyDefaultEntryPointThrowsException() {
        setDefaultEntryPoint("");
        executeDefaultEntryPoint();
    }

    @Test(expected = IllegalStateException.class)
    public void nullDefaultEntryPointThrowsException() {
        setDefaultEntryPoint(null);
        executeDefaultEntryPoint();
    }

    @Test
    public void openingDefaultEntryPointFiresEvent() {
        assertOpenUrlEventIsFired(this::executeDefaultEntryPoint);
    }

    @Test
    public void defaultEntryPointWithPageCanBeOpened() {
        Page page = executeDefaultEntryPointWithPage();
        assertThat(page).isNotNull();
        verifyWebDriverNavigationToUrl();
    }

    @Test(expected = IllegalStateException.class)
    public void emptyDefaultEntryPointWithPageThrowsException() {
        setDefaultEntryPoint("");
        executeDefaultEntryPointWithPage();
    }

    @Test(expected = IllegalStateException.class)
    public void nullDefaultEntryPointWithPageThrowsException() {
        setDefaultEntryPoint(null);
        executeDefaultEntryPointWithPage();
    }

    @Test
    public void openingDefaultEntryPointWithPageFiresEvent() {
        assertOpenUrlEventIsFired(this::executeDefaultEntryPointWithPage);
    }

    /* URL objects */

    @Test
    public void urlObjectCanBeOpened() {
        executeUrlObject();
        verifyWebDriverNavigationToUrl();
    }

    @Test
    public void urlObjectWithPageCanBeOpened() {
        Page page = executeUrlObjectWithPage();
        assertThat(page).isNotNull();
        verifyWebDriverNavigationToUrl();
    }

    @Test
    public void openingUrlObjectFiresEvent() {
        assertOpenUrlEventIsFired(this::executeUrlObject);
    }

    @Test
    public void openingUrlObjectWithPageFiresEvent() {
        assertOpenUrlEventIsFired(this::executeUrlObjectWithPage);
    }

    /* URL strings */

    @Test
    public void urlStringCanBeOpened() {
        executeUrlString();
        verifyWebDriverNavigationToUrl();
    }

    @Test
    public void urlStringWithPageCanBeOpened() {
        Page page = executeUrlStringWithPage();
        assertThat(page).isNotNull();
        verifyWebDriverNavigationToUrl();
    }

    @Test
    public void openingUrlStringFiresEvent() {
        assertOpenUrlEventIsFired(this::executeUrlString);
    }

    @Test
    public void openingUrlStringWithPageFiresEvent() {
        assertOpenUrlEventIsFired(this::executeUrlStringWithPage);
    }

    /* method execution */

    void executeDefaultEntryPoint() {
        cut.defaultEntryPoint();
    }

    Page executeDefaultEntryPointWithPage() {
        return cut.defaultEntryPoint(Page.class);
    }

    void executeUrlObject() {
        cut.url(URL_OBJECT);
    }

    Page executeUrlObjectWithPage() {
        return cut.url(URL_OBJECT, Page.class);
    }

    void executeUrlString() {
        cut.url(URL);
    }

    Page executeUrlStringWithPage() {
        return cut.url(URL, Page.class);
    }

    /* utilities */

    void setDefaultEntryPoint(String entryPoint) {
        doReturn(Optional.ofNullable(entryPoint)).when(configuration).getDefaultEntryPoint();
    }

    void verifyWebDriverNavigationToUrl() {
        verify(webDriver).get(URL);
        verifyNoMoreInteractions(webDriver);
    }

    void assertOpenUrlEventIsFired(Runnable runnable){
        EventCaptor.capture(eventSystem, OpenedUrlEvent.class)
            .execute(runnable)
            .assertEventWasFired()
            .assertEvent(this::assertOpenUrlEventContainsUrl);
    }

    void assertOpenUrlEventContainsUrl(OpenedUrlEvent event) {
        String eventUrl = event.getUrl();
        assertThat(eventUrl).isEqualTo(URL);
    }

}
