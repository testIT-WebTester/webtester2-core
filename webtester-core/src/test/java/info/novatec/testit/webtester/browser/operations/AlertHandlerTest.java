package info.novatec.testit.webtester.browser.operations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.security.Credentials;
import org.openqa.selenium.security.UserAndPassword;

import utils.events.EventCaptor;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.browser.WebDriverBrowser;
import info.novatec.testit.webtester.events.EventSystem;
import info.novatec.testit.webtester.events.browser.AcceptedAlertEvent;
import info.novatec.testit.webtester.events.browser.DeclinedAlertEvent;


@RunWith(Enclosed.class)
public class AlertHandlerTest {

    @RunWith(MockitoJUnitRunner.class)
    static abstract class AbstractAlertHandlerTest {

        @Mock(answer = Answers.RETURNS_DEEP_STUBS)
        WebDriver webDriver;

        Browser browser;
        EventSystem eventSystem;
        AlertHandler cut;

        @Before
        public void initializeClassUnderTest() {
            browser = WebDriverBrowser.forWebDriver(webDriver).build();
            eventSystem = browser.events();
            cut = new AlertHandler(browser);
        }

        Alert alertIsPresent(String message) {
            Alert alert = mock(Alert.class);
            when(alert.getText()).thenReturn(message);
            when(webDriver.switchTo().alert()).thenReturn(alert);
            return alert;
        }

        void alertIsNotPresent() {
            NoAlertPresentException exception = new NoAlertPresentException();
            when(webDriver.switchTo().alert()).thenThrow(exception);
        }

    }

    public static class Accept extends AbstractAlertHandlerTest {

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

    }

    public static class AcceptIfPresent extends AbstractAlertHandlerTest {

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

    }

    public static class Decline extends AbstractAlertHandlerTest {

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

    }

    public static class DeclineIfPresent extends AbstractAlertHandlerTest {

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

    }

    public static class IsPresent extends AbstractAlertHandlerTest {

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

    }

    public static class Get extends AbstractAlertHandlerTest {

        @Test
        public void gettingNonExistentAlertReturnsEmptyOptional() {
            alertIsNotPresent();
            assertThat(cut.get()).isEmpty();
        }

        @Test
        public void gettingExistentAlertReturnsItAsAnOptional() {
            alertIsPresent("hello alert!");
            assertThat(cut.get()).isPresent();
        }

    }

    public static class AuthenticateWith extends AbstractAlertHandlerTest {

        @Captor
        ArgumentCaptor<Credentials> credentialsCaptor;

        @Test
        public void canAuthenticateWithCredentials() {

            Alert alert = alertIsPresent("please sign in");
            Credentials credentials = new UserAndPassword("foo", "bar");
            browser.alert().authenticateWith(credentials);

            verify(alert).authenticateUsing(credentials);

        }

        @Test
        public void canAuthenticateWithUsernameAndPassword() {

            Alert alert = alertIsPresent("please sign in");
            browser.alert().authenticateWith("foo", "bar");
            verify(alert).authenticateUsing(credentialsCaptor.capture());

            Credentials credentials = credentialsCaptor.getValue();
            assertThat(credentials).isInstanceOf(UserAndPassword.class);
            assertThat((( UserAndPassword ) credentials).getUsername()).isEqualTo("foo");
            assertThat((( UserAndPassword ) credentials).getPassword()).isEqualTo("bar");

        }

    }

}
