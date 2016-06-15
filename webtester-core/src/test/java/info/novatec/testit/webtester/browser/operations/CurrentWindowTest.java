package info.novatec.testit.webtester.browser.operations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;

import utils.MockFactory;
import utils.events.EventCaptor;

import info.novatec.testit.webtester.browser.WebDriverBrowser;
import info.novatec.testit.webtester.events.EventSystem;
import info.novatec.testit.webtester.events.browser.ClosedWindowEvent;
import info.novatec.testit.webtester.events.browser.MaximizedWindowEvent;
import info.novatec.testit.webtester.events.browser.RefreshedPageEvent;
import info.novatec.testit.webtester.events.browser.SetWindowPositionEvent;
import info.novatec.testit.webtester.events.browser.SetWindowSizeEvent;


@RunWith(MockitoJUnitRunner.class)
public class CurrentWindowTest {

    private WebDriver webDriver;
    private CurrentWindow cut;

    @Before
    public void init() throws IOException {
        webDriver = MockFactory.webDriver();
        cut = new CurrentWindow(WebDriverBrowser.forWebDriver(webDriver).build());
    }

    /* window handle */

    @Test
    public void handleOfWindowCanBeReturned() {
        doReturn("fooBar").when(webDriver).getWindowHandle();
        String handle = cut.getHandle();
        assertThat(handle).isEqualTo("fooBar");
    }

    /* refresh */

    @Test
    public void refreshingWindowDelegatesToCorrectWebDriverMethod() {
        cut.refresh();
        verify(navigation()).refresh();
    }

    @Test
    public void refreshingWindowFiresEvent() {
        EventCaptor.capture(eventSystem(), RefreshedPageEvent.class)
            .execute(() -> cut.refresh())
            .assertEventWasFired();
    }

    /* maximizing */

    @Test
    public void maximizingWindowDelegatesToCorrectWebDriverMethod() {
        cut.maximize();
        verify(window()).maximize();
    }

    @Test
    public void maximizingWindowFiresEvent() {
        EventCaptor.capture(eventSystem(), MaximizedWindowEvent.class)
            .execute(() -> cut.maximize())
            .assertEventWasFired();
    }

    /* position */

    @Test
    public void settingWindowPositionDelegatesToCorrectWebDriverMethod() {
        cut.setPosition(42, 84);
        verify(window()).setPosition(new Point(42, 84));
    }

    @Test
    public void settingWindowPositionFiresEvent() {
        EventCaptor.capture(eventSystem(), SetWindowPositionEvent.class)
            .execute(() -> cut.setPosition(42, 84))
            .assertEventWasFired()
            .assertEvent(event -> {
                assertThat(event.getX()).isEqualTo(42);
                assertThat(event.getY()).isEqualTo(84);
            });
    }

    /* size */

    @Test
    public void settingWindowSizeDelegatesToCorrectWebDriverMethod() {
        cut.setSize(1024, 768);
        verify(window()).setSize(new Dimension(1024, 768));
    }

    @Test
    public void settingWindowSizeFiresEvent() {
        EventCaptor.capture(eventSystem(), SetWindowSizeEvent.class)
            .execute(() -> cut.setSize(1024, 768))
            .assertEventWasFired()
            .assertEvent(event -> {
                assertThat(event.getWidth()).isEqualTo(1024);
                assertThat(event.getHeight()).isEqualTo(768);
            });
    }

    /* closing */

    @Test
    public void closingWindowDelegatesToCorrectWebDriverMethods() {
        cut.close();
        verify(webDriver).close();
    }

    @Test
    public void closingWindowFiresEvent() {
        EventCaptor.capture(eventSystem(), ClosedWindowEvent.class)
            .execute(() -> cut.close())
            .assertEventWasFired();
    }

    /* utilities */

    private EventSystem eventSystem() {
        return cut.browser().events();
    }

    private WebDriver.Window window() {
        return webDriver.manage().window();
    }

    private WebDriver.Navigation navigation() {
        return webDriver.navigate();
    }

    private WebDriver.TargetLocator targetLocator() {
        return webDriver.switchTo();
    }

}
