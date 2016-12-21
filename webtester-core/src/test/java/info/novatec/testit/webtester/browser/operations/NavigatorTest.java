package info.novatec.testit.webtester.browser.operations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;

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
import org.openqa.selenium.WebDriver;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.events.EventSystem;
import info.novatec.testit.webtester.events.browser.NavigatedBackwardsEvent;
import info.novatec.testit.webtester.events.browser.NavigatedForwardsEvent;


@RunWith(Enclosed.class)
public class NavigatorTest {

    @RunWith(MockitoJUnitRunner.class)
    public static abstract class AbstractNavigatorTest {

        @Mock(answer = Answers.RETURNS_DEEP_STUBS)
        WebDriver webDriver;
        @Mock
        EventSystem events;
        @Mock
        Browser browser;

        @InjectMocks
        Navigator cut;

        @Before
        public void init() throws IOException {
            doReturn(webDriver).when(browser).webDriver();
            doReturn(events).when(browser).events();
            doReturn(true).when(events).isEnabled();
        }

    }

    public static class Forwards extends AbstractNavigatorTest {

        @Captor
        ArgumentCaptor<NavigatedForwardsEvent> eventCaptor;

        @Test
        public void navigatingForwardsDelegatesToCorrectWebDriverCall() {
            cut.forwards();
            verify(webDriver.navigate()).forward();
        }

        @Test
        public void navigatingForwardsFiresEvent() {
            cut.forwards();
            verify(events).fireEvent(eventCaptor.capture());
            assertThat(eventCaptor.getValue()).isNotNull();
        }

    }

    public static class MultipleForwards extends AbstractNavigatorTest {

        @Captor
        ArgumentCaptor<NavigatedForwardsEvent> eventCaptor;

        @Test
        public void navigatingForwardsMultipleTimesDelegatesToCorrectWebDriverCall() {
            cut.forwards(10);
            verify(webDriver.navigate(), times(10)).forward();
        }

        @Test
        public void navigatingForwardsMultipleTimesFiresMultipleEvents() {
            cut.forwards(10);
            verify(events, times(10)).fireEvent(eventCaptor.capture());
            eventCaptor.getAllValues().forEach(event -> assertThat(event).isNotNull());
        }

    }

    public static class Backwards extends AbstractNavigatorTest {

        @Captor
        ArgumentCaptor<NavigatedBackwardsEvent> eventCaptor;

        @Test
        public void navigatingBackwardsDelegatesToCorrectWebDriverCall() {
            cut.backwards();
            verify(webDriver.navigate()).back();
        }

        @Test
        public void navigatingBackwardsFiresEvent() {
            cut.backwards();
            verify(events).fireEvent(eventCaptor.capture());
            assertThat(eventCaptor.getValue()).isNotNull();
        }

    }

    public static class MultipleBackwards extends AbstractNavigatorTest {

        @Captor
        ArgumentCaptor<NavigatedBackwardsEvent> eventCaptor;

        @Test
        public void navigatingBackwardsMultipleTimesDelegatesToCorrectWebDriverCall() {
            cut.backwards(10);
            verify(webDriver.navigate(), times(10)).back();
        }

        @Test
        public void navigatingBackwardsMultipleTimesFiresMultipleEvents() {
            cut.backwards(10);
            verify(events, times(10)).fireEvent(eventCaptor.capture());
            eventCaptor.getAllValues().forEach(event -> assertThat(event).isNotNull());
        }

    }

}
