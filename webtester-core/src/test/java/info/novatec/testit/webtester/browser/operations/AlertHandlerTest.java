package info.novatec.testit.webtester.browser.operations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;

import utils.MockFactory;
import utils.events.EventCaptor;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.browser.WebDriverBrowser;
import info.novatec.testit.webtester.events.EventSystem;
import info.novatec.testit.webtester.events.browser.AcceptedAlertEvent;
import info.novatec.testit.webtester.events.browser.DeclinedAlertEvent;


@RunWith(MockitoJUnitRunner.class)
public class AlertHandlerTest {

    WebDriver webDriver;
    Browser browser;
    EventSystem eventSystem;
    AlertHandler cut;

    @Before
    public void init() {
        webDriver = MockFactory.webDriver();
        browser = WebDriverBrowser.forWebDriver(webDriver).build();
        eventSystem = browser.events();
        cut = new AlertHandler(browser);
    }

    /* accept */

    @Test
    public void alertsCanBeAccepted() {
        Alert alert = alertIsPresent("hello alert!");
        cut.accept();
        verify(alert).accept();
    }

    @Test(expected = NoAlertPresentException.class)
    public void acceptingNonExistentAlertThrowsException() {
        alertIsNotPresent();
        cut.accept();
    }

    @Test
    public void acceptingAnAlertFiresEvent() {
        alertIsPresent("hello alert!");
        EventCaptor.capture(eventSystem, AcceptedAlertEvent.class)
            .execute(() -> cut.accept())
            .assertEventWasFired()
            .assertEvent(event -> assertThat(event.getAlertMessage()).isEqualTo("hello alert!"));
    }

    /* accept if present */

    @Test
    public void alertsCanBeAcceptedIfTheyArePresent() {
        Alert alert = alertIsPresent("hello alert!");
        cut.acceptIfPresent();
        verify(alert).accept();
    }

    @Test
    public void acceptingAnAlertIfItsPresentDoesNotThrowExceptionIfNoAlertExists() {
        alertIsNotPresent();
        cut.acceptIfPresent();
        // no exception
    }

    /* decline */

    @Test
    public void alertsCanBeDeclined() {
        Alert alert = alertIsPresent("hello alert!");
        cut.decline();
        verify(alert).dismiss();
    }

    @Test(expected = NoAlertPresentException.class)
    public void decliningNonExistentAlertThrowsException() {
        alertIsNotPresent();
        cut.decline();
    }

    @Test
    public void decliningAnAlertFiresEvent() {
        alertIsPresent("hello alert!");
        EventCaptor.capture(eventSystem, DeclinedAlertEvent.class)
            .execute(() -> cut.decline())
            .assertEventWasFired()
            .assertEvent(event -> assertThat(event.getAlertMessage()).isEqualTo("hello alert!"));
    }

    /* decline if present */

    @Test
    public void alertsCanBeDeclinedIfTheyArePresent() {
        Alert alert = alertIsPresent("hello alert!");
        cut.declineIfPresent();
        verify(alert).dismiss();
    }

    @Test
    public void decliningAnAlertIfItsPresentDoesNotThrowExceptionIfNoAlertExists() {
        alertIsNotPresent();
        cut.declineIfPresent();
        // no exception
    }

    /* presence */

    @Test
    public void presenceOfNonExistentAlertReturnsFalse() {
        alertIsNotPresent();
        assertThat(cut.isPresent()).isFalse();
    }

    @Test
    public void presenceOfExistentAlertReturnsTrue() {
        alertIsPresent("hello alert!");
        assertThat(cut.isPresent()).isTrue();
    }

    /* utilities */

    Alert alertIsPresent(String message) {
        WebDriver.TargetLocator targetLocator = webDriver.switchTo();
        Alert alert = mock(Alert.class);
        doReturn(message).when(alert).getText();
        doReturn(alert).when(targetLocator).alert();
        return alert;
    }

    void alertIsNotPresent() {
        WebDriver.TargetLocator targetLocator = webDriver.switchTo();
        doThrow(new NoAlertPresentException()).when(targetLocator).alert();
    }

}
