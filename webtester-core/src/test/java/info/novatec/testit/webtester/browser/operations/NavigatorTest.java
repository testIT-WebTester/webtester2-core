package info.novatec.testit.webtester.browser.operations;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.WebDriver;

import utils.events.EventCaptor;
import utils.events.MultiEventCaptor;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.browser.WebDriverBrowser;
import info.novatec.testit.webtester.events.EventSystem;
import info.novatec.testit.webtester.events.browser.NavigatedBackwardsEvent;
import info.novatec.testit.webtester.events.browser.NavigatedForwardsEvent;


@RunWith(MockitoJUnitRunner.class)
public class NavigatorTest {

    @Mock
    WebDriver webDriver;
    @Mock
    WebDriver.Navigation navigation;

    Browser browser;
    EventSystem eventSystem;
    Navigator cut;

    @Before
    public void init() throws IOException {
        doReturn(navigation).when(webDriver).navigate();
        browser = WebDriverBrowser.forWebDriver(webDriver).build();
        eventSystem = browser.events();
        cut = new Navigator(browser);
    }

    /* forwards */

    @Test
    public void navigatingForwardsDelegatesToCorrectWebDriverCall() {
        cut.forwards();
        verify(navigation).forward();
    }

    @Test
    public void navigatingForwardsFiresEvent() {
        EventCaptor.capture(eventSystem, NavigatedForwardsEvent.class)
            .execute(() -> cut.forwards())
            .assertEventWasFired();
    }

    /* multiple forwards */

    @Test
    public void navigatingForwardsMultipleTimesDelegatesToCorrectWebDriverCall() {
        cut.forwards(10);
        verify(navigation, times(10)).forward();
    }

    @Test
    public void navigatingForwardsMultipleTimesFiresMultipleEvents() {
        MultiEventCaptor.capture(eventSystem, NavigatedForwardsEvent.class)
            .execute(() -> cut.forwards(10))
            .assertEventsWereFired(10);
    }

    /* backwards */

    @Test
    public void navigatingBackwardsDelegatesToCorrectWebDriverCall() {
        cut.backwards();
        verify(navigation).back();
    }

    @Test
    public void navigatingBackwardsFiresEvent() {
        EventCaptor.capture(eventSystem, NavigatedBackwardsEvent.class)
            .execute(() -> cut.backwards())
            .assertEventWasFired();
    }

    /* multiple backwards */

    @Test
    public void navigatingBackwardsMultipleTimesDelegatesToCorrectWebDriverCall() {
        cut.backwards(10);
        verify(navigation, times(10)).back();
    }

    @Test
    public void navigatingBackwardsMultipleTimesFiresMultipleEvents() {
        MultiEventCaptor.capture(eventSystem, NavigatedBackwardsEvent.class)
            .execute(() -> cut.backwards(10))
            .assertEventsWereFired(10);
    }

}
