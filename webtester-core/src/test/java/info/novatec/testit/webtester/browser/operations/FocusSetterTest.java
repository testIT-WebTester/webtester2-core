package info.novatec.testit.webtester.browser.operations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.testit.testutils.mockito.junit5.EnableMocking;

import utils.unit.MockFactory;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.events.EventSystem;
import info.novatec.testit.webtester.events.browser.SwitchedToDefaultContentEvent;
import info.novatec.testit.webtester.events.browser.SwitchedToFrameEvent;
import info.novatec.testit.webtester.events.browser.SwitchedToWindowEvent;
import info.novatec.testit.webtester.pagefragments.PageFragment;


@EnableMocking
class FocusSetterTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    WebDriver webDriver;
    @Mock
    EventSystem events;
    @Mock
    Browser browser;

    @InjectMocks
    FocusSetter cut;

    @BeforeEach
    void init() throws IOException {
        doReturn(webDriver).when(browser).webDriver();
        doReturn(events).when(browser).events();
        doReturn(true).when(events).isEnabled();
    }

    @Nested
    class FocusOnFrameByIndex {

        @Captor
        ArgumentCaptor<SwitchedToFrameEvent> eventCaptor;

        @Test
        void switchingToFrameByIndexInvokesCorrectLocatorMethod() {
            cut.onFrame(42);
            verify(webDriver.switchTo()).frame(42);
        }

        @Test
        void switchingToFrameByIndexFiresEvent() {
            cut.onFrame(42);
            verify(events).fireEvent(eventCaptor.capture());
            SwitchedToFrameEvent event = eventCaptor.getValue();
            assertThat(event.getTarget()).isEqualTo("42");
        }

        @Test
        void switchingToFrameByUnknownIndexThrowsException() {
            NoSuchFrameException exception = mock(NoSuchFrameException.class);
            when(webDriver.switchTo().frame(42)).thenThrow(exception);
            assertThrows(NoSuchFrameException.class, () -> {
                cut.onFrame(42);
            });
            verify(events).fireExceptionEvent(exception);
            verifyNoMoreInteractions(events);
        }

    }

    @Nested
    class FocusOnFrameByNameOrId {

        @Captor
        ArgumentCaptor<SwitchedToFrameEvent> eventCaptor;

        @Test
        void switchingToFrameByNameOrIdInvokesCorrectLocatorMethod() {
            cut.onFrame("fooBar");
            verify(webDriver.switchTo()).frame("fooBar");
        }

        @Test
        void switchingToFrameByNameOrIdFiresEvent() {
            cut.onFrame("fooBar");
            verify(events).fireEvent(eventCaptor.capture());
            SwitchedToFrameEvent event = eventCaptor.getValue();
            assertThat(event.getTarget()).isEqualTo("fooBar");
        }

        @Test
        void switchingToFrameByUnknownNameOrIdThrowsException() {
            NoSuchFrameException exception = mock(NoSuchFrameException.class);
            when(webDriver.switchTo().frame("fooBar")).thenThrow(exception);
            assertThrows(NoSuchFrameException.class, () -> {
                cut.onFrame("fooBar");
            });
            verify(events).fireExceptionEvent(exception);
            verifyNoMoreInteractions(events);
        }

    }

    @Nested
    class FocusOnFrameByPageFragment {

        @Captor
        ArgumentCaptor<SwitchedToFrameEvent> eventCaptor;

        @Test
        void switchingToFrameByPageFragmentInvokesCorrectLocatorMethod() {
            PageFragment fragment = MockFactory.fragment().build();
            cut.onFrame(fragment);
            verify(webDriver.switchTo()).frame(fragment.webElement());
        }

        @Test
        void switchingToFrameByPageFragmentFiresEvent() {
            PageFragment fragment = MockFactory.fragment().withName("The Name").build();
            cut.onFrame(fragment);
            verify(events).fireEvent(eventCaptor.capture());
            SwitchedToFrameEvent event = eventCaptor.getValue();
            assertThat(event.getTarget()).isEqualTo("The Name");
        }

        @Test
        void switchingToFrameByNonFrameFragmentThrowsException() {
            PageFragment fragment = MockFactory.fragment().withName("The Name").build();
            NoSuchFrameException exception = mock(NoSuchFrameException.class);
            when(webDriver.switchTo().frame(fragment.webElement())).thenThrow(exception);
            assertThrows(NoSuchFrameException.class, () -> {
                cut.onFrame(fragment);
            });
            verify(events).fireExceptionEvent(exception);
            verifyNoMoreInteractions(events);
        }

    }

    @Nested
    class FocusOnWindowByNameOrHandle {

        @Captor
        ArgumentCaptor<SwitchedToWindowEvent> eventCaptor;

        @Test
        void switchingToWindowByNameOrHandleInvokesCorrectLocatorMethod() {
            cut.onWindow("fooBar");
            verify(webDriver.switchTo()).window("fooBar");
        }

        @Test
        void switchingToWindowByNameOrHandleFiresEvent() {
            cut.onWindow("fooBar");
            verify(events).fireEvent(eventCaptor.capture());
            SwitchedToWindowEvent event = eventCaptor.getValue();
            assertThat(event.getNameOrHandle()).isEqualTo("fooBar");
        }

        @Test
        void switchingToWindowByUnknownNameOrHandleThrowsException() {
            NoSuchWindowException exception = mock(NoSuchWindowException.class);
            when(webDriver.switchTo().window("fooBar")).thenThrow(exception);
            assertThrows(NoSuchWindowException.class, () -> {
                cut.onWindow("fooBar");
            });
            verify(events).fireExceptionEvent(exception);
            verifyNoMoreInteractions(events);
        }

    }

    @Nested
    class FocusOnDefaultContent {

        @Captor
        ArgumentCaptor<SwitchedToDefaultContentEvent> eventCaptor;

        @Test
        void switchingToDefaultContentInvokesCorrectLocatorMethod() {
            cut.onDefaultContent();
            verify(webDriver.switchTo()).defaultContent();
        }

        @Test
        void switchingToDefaultContentFiresEvent() {
            cut.onDefaultContent();
            verify(events).fireEvent(eventCaptor.capture());
            assertThat(eventCaptor.getValue()).isNotNull();
        }

    }

}
