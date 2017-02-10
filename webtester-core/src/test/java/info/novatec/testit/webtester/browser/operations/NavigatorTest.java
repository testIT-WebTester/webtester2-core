package info.novatec.testit.webtester.browser.operations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.openqa.selenium.WebDriver;
import org.testit.testutils.mockito.junit5.EnableMocking;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.events.EventSystem;
import info.novatec.testit.webtester.events.browser.NavigatedBackwardsEvent;
import info.novatec.testit.webtester.events.browser.NavigatedForwardsEvent;


@EnableMocking
class NavigatorTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    WebDriver webDriver;
    @Mock
    EventSystem events;
    @Mock
    Browser browser;

    @InjectMocks
    Navigator cut;

    @BeforeEach
    void init() throws IOException {
        doReturn(webDriver).when(browser).webDriver();
        doReturn(events).when(browser).events();
        doReturn(true).when(events).isEnabled();
    }

    @Nested
    class Forwards {

        @Captor
        ArgumentCaptor<NavigatedForwardsEvent> eventCaptor;

        @Test
        void navigatingForwardsDelegatesToCorrectWebDriverCall() {
            cut.forwards();
            verify(webDriver.navigate()).forward();
        }

        @Test
        void navigatingForwardsFiresEvent() {
            cut.forwards();
            verify(events).fireEvent(eventCaptor.capture());
            assertThat(eventCaptor.getValue()).isNotNull();
        }

    }

    @Nested
    class MultipleForwards {

        @Captor
        ArgumentCaptor<NavigatedForwardsEvent> eventCaptor;

        @Test
        void navigatingForwardsMultipleTimesDelegatesToCorrectWebDriverCall() {
            cut.forwards(10);
            verify(webDriver.navigate(), times(10)).forward();
        }

        @Test
        void navigatingForwardsMultipleTimesFiresMultipleEvents() {
            cut.forwards(10);
            verify(events, times(10)).fireEvent(eventCaptor.capture());
            eventCaptor.getAllValues().forEach(event -> assertThat(event).isNotNull());
        }

    }

    @Nested
    class Backwards {

        @Captor
        ArgumentCaptor<NavigatedBackwardsEvent> eventCaptor;

        @Test
        void navigatingBackwardsDelegatesToCorrectWebDriverCall() {
            cut.backwards();
            verify(webDriver.navigate()).back();
        }

        @Test
        void navigatingBackwardsFiresEvent() {
            cut.backwards();
            verify(events).fireEvent(eventCaptor.capture());
            assertThat(eventCaptor.getValue()).isNotNull();
        }

    }

    @Nested
    class MultipleBackwards {

        @Captor
        ArgumentCaptor<NavigatedBackwardsEvent> eventCaptor;

        @Test
        void navigatingBackwardsMultipleTimesDelegatesToCorrectWebDriverCall() {
            cut.backwards(10);
            verify(webDriver.navigate(), times(10)).back();
        }

        @Test
        void navigatingBackwardsMultipleTimesFiresMultipleEvents() {
            cut.backwards(10);
            verify(events, times(10)).fireEvent(eventCaptor.capture());
            eventCaptor.getAllValues().forEach(event -> assertThat(event).isNotNull());
        }

    }

}
