package info.novatec.testit.webtester.browser.operations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.security.Credentials;
import org.openqa.selenium.security.UserAndPassword;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.events.EventSystem;
import info.novatec.testit.webtester.events.browser.AcceptedAlertEvent;
import info.novatec.testit.webtester.events.browser.DeclinedAlertEvent;


@RunWith(Enclosed.class)
public class AlertHandlerTest {

    @RunWith(MockitoJUnitRunner.Silent.class)
    static abstract class AbstractAlertHandlerTest {

        @Mock(answer = Answers.RETURNS_DEEP_STUBS)
        WebDriver webDriver;
        @Mock
        EventSystem events;
        @Mock
        Browser browser;

        @InjectMocks
        AlertHandler cut;

        @Before
        public void init() {
            doReturn(webDriver).when(browser).webDriver();
            doReturn(events).when(browser).events();
            doReturn(true).when(events).isEnabled();
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

        @Captor
        ArgumentCaptor<AcceptedAlertEvent> eventCaptor;

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
            cut.accept();
            verify(events).fireEvent(eventCaptor.capture());
            AcceptedAlertEvent event = eventCaptor.getValue();
            assertThat(event.getAlertMessage()).isEqualTo("hello alert!");
        }

    }

    public static class AcceptIfPresent extends AbstractAlertHandlerTest {

        @Captor
        ArgumentCaptor<AcceptedAlertEvent> eventCaptor;

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

        @Test
        public void acceptingAnAlertFiresEvent() {
            alertIsPresent("hello alert!");
            cut.acceptIfPresent();
            verify(events).fireEvent(eventCaptor.capture());
            AcceptedAlertEvent event = eventCaptor.getValue();
            assertThat(event.getAlertMessage()).isEqualTo("hello alert!");
        }

    }

    public static class Decline extends AbstractAlertHandlerTest {

        @Captor
        ArgumentCaptor<DeclinedAlertEvent> eventCaptor;

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
            cut.decline();
            verify(events).fireEvent(eventCaptor.capture());
            DeclinedAlertEvent event = eventCaptor.getValue();
            assertThat(event.getAlertMessage()).isEqualTo("hello alert!");
        }

    }

    public static class DeclineIfPresent extends AbstractAlertHandlerTest {

        @Captor
        ArgumentCaptor<DeclinedAlertEvent> eventCaptor;

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

        @Test
        public void decliningAnAlertFiresEvent() {
            alertIsPresent("hello alert!");
            cut.declineIfPresent();
            verify(events).fireEvent(eventCaptor.capture());
            DeclinedAlertEvent event = eventCaptor.getValue();
            assertThat(event.getAlertMessage()).isEqualTo("hello alert!");
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
            cut.authenticateWith(credentials);

            verify(alert).authenticateUsing(credentials);

        }

        @Test
        public void canAuthenticateWithUsernameAndPassword() {

            Alert alert = alertIsPresent("please sign in");
            cut.authenticateWith("foo", "bar");
            verify(alert).authenticateUsing(credentialsCaptor.capture());

            Credentials credentials = credentialsCaptor.getValue();
            assertThat(credentials).isInstanceOf(UserAndPassword.class);
            assertThat((( UserAndPassword ) credentials).getUsername()).isEqualTo("foo");
            assertThat((( UserAndPassword ) credentials).getPassword()).isEqualTo("bar");

        }

    }

}
