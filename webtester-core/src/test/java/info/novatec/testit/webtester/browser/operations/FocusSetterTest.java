package info.novatec.testit.webtester.browser.operations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

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
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;

import utils.MockFactory;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.events.EventSystem;
import info.novatec.testit.webtester.events.browser.SwitchedToDefaultContentEvent;
import info.novatec.testit.webtester.events.browser.SwitchedToFrameEvent;
import info.novatec.testit.webtester.events.browser.SwitchedToWindowEvent;
import info.novatec.testit.webtester.pagefragments.PageFragment;


@RunWith(Enclosed.class)
public class FocusSetterTest {

    @RunWith(MockitoJUnitRunner.class)
    public static abstract class AbstractFocusSetterTest {

        @Mock(answer = Answers.RETURNS_DEEP_STUBS)
        WebDriver webDriver;
        @Mock
        EventSystem events;
        @Mock
        Browser browser;

        @InjectMocks
        FocusSetter cut;

        @Before
        public void init() throws IOException {
            doReturn(webDriver).when(browser).webDriver();
            doReturn(events).when(browser).events();
            doReturn(true).when(events).isEnabled();
        }

    }

    public static class FocusOnFrameByIndex extends AbstractFocusSetterTest {

        @Captor
        ArgumentCaptor<SwitchedToFrameEvent> eventCaptor;

        @Test
        public void switchingToFrameByIndexInvokesCorrectLocatorMethod() {
            cut.onFrame(42);
            verify(webDriver.switchTo()).frame(42);
        }

        @Test
        public void switchingToFrameByIndexFiresEvent() {
            cut.onFrame(42);
            verify(events).fireEvent(eventCaptor.capture());
            SwitchedToFrameEvent event = eventCaptor.getValue();
            assertThat(event.getTarget()).isEqualTo("42");
        }

        @Test(expected = NoSuchFrameException.class)
        public void switchingToFrameByUnknownIndexThrowsException() {
            NoSuchFrameException exception = mock(NoSuchFrameException.class);
            when(webDriver.switchTo().frame(42)).thenThrow(exception);
            try {
                cut.onFrame(42);
            } finally {
                verify(events).fireExceptionEvent(exception);
                verifyNoMoreInteractions(events);
            }
        }

    }

    public static class FocusOnFrameByNameOrId extends AbstractFocusSetterTest {

        @Captor
        ArgumentCaptor<SwitchedToFrameEvent> eventCaptor;

        @Test
        public void switchingToFrameByNameOrIdInvokesCorrectLocatorMethod() {
            cut.onFrame("fooBar");
            verify(webDriver.switchTo()).frame("fooBar");
        }

        @Test
        public void switchingToFrameByNameOrIdFiresEvent() {
            cut.onFrame("fooBar");
            verify(events).fireEvent(eventCaptor.capture());
            SwitchedToFrameEvent event = eventCaptor.getValue();
            assertThat(event.getTarget()).isEqualTo("fooBar");
        }

        @Test(expected = NoSuchFrameException.class)
        public void switchingToFrameByUnknownNameOrIdThrowsException() {
            NoSuchFrameException exception = mock(NoSuchFrameException.class);
            when(webDriver.switchTo().frame("fooBar")).thenThrow(exception);
            try {
                cut.onFrame("fooBar");
            } finally {
                verify(events).fireExceptionEvent(exception);
                verifyNoMoreInteractions(events);
            }
        }

    }

    public static class FocusOnFrameByPageFragment extends AbstractFocusSetterTest {

        @Captor
        ArgumentCaptor<SwitchedToFrameEvent> eventCaptor;

        @Test
        public void switchingToFrameByPageFragmentInvokesCorrectLocatorMethod() {
            PageFragment fragment = MockFactory.fragment().build();
            cut.onFrame(fragment);
            verify(webDriver.switchTo()).frame(fragment.webElement());
        }

        @Test
        public void switchingToFrameByPageFragmentFiresEvent() {
            PageFragment fragment = MockFactory.fragment().withName("The Name").build();
            cut.onFrame(fragment);
            verify(events).fireEvent(eventCaptor.capture());
            SwitchedToFrameEvent event = eventCaptor.getValue();
            assertThat(event.getTarget()).isEqualTo("The Name");
        }

        @Test(expected = NoSuchFrameException.class)
        public void switchingToFrameByNonFrameFragmentThrowsException() {
            PageFragment fragment = MockFactory.fragment().withName("The Name").build();
            NoSuchFrameException exception = mock(NoSuchFrameException.class);
            when(webDriver.switchTo().frame(fragment.webElement())).thenThrow(exception);
            try {
                cut.onFrame(fragment);
            } finally {
                verify(events).fireExceptionEvent(exception);
                verifyNoMoreInteractions(events);
            }
        }

    }

    public static class FocusOnWindowByNameOrHandle extends AbstractFocusSetterTest {

        @Captor
        ArgumentCaptor<SwitchedToWindowEvent> eventCaptor;

        @Test
        public void switchingToWindowByNameOrHandleInvokesCorrectLocatorMethod() {
            cut.onWindow("fooBar");
            verify(webDriver.switchTo()).window("fooBar");
        }

        @Test
        public void switchingToWindowByNameOrHandleFiresEvent() {
            cut.onWindow("fooBar");
            verify(events).fireEvent(eventCaptor.capture());
            SwitchedToWindowEvent event = eventCaptor.getValue();
            assertThat(event.getNameOrHandle()).isEqualTo("fooBar");
        }

        @Test(expected = NoSuchWindowException.class)
        public void switchingToWindowByUnknownNameOrHandleThrowsException() {
            NoSuchWindowException exception = mock(NoSuchWindowException.class);
            when(webDriver.switchTo().window("fooBar")).thenThrow(exception);
            try {
                cut.onWindow("fooBar");
            } finally {
                verify(events).fireExceptionEvent(exception);
                verifyNoMoreInteractions(events);
            }
        }

    }

    public static class FocusOnDefaultContent extends AbstractFocusSetterTest {

        @Captor
        ArgumentCaptor<SwitchedToDefaultContentEvent> eventCaptor;

        @Test
        public void switchingToDefaultContentInvokesCorrectLocatorMethod() {
            cut.onDefaultContent();
            verify(webDriver.switchTo()).defaultContent();
        }

        @Test
        public void switchingToDefaultContentFiresEvent() {
            cut.onDefaultContent();
            verify(events).fireEvent(eventCaptor.capture());
            assertThat(eventCaptor.getValue()).isNotNull();
        }

    }

}
