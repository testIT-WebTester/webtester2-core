package info.novatec.testit.webtester.browser.operations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.TargetLocator;

import utils.MockFactory;
import utils.events.EventCaptor;
import utils.events.ExceptionEventCaptor;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.browser.WebDriverBrowser;
import info.novatec.testit.webtester.config.Configuration;
import info.novatec.testit.webtester.events.EventSystem;
import info.novatec.testit.webtester.events.browser.SwitchedToDefaultContentEvent;
import info.novatec.testit.webtester.events.browser.SwitchedToFrameEvent;
import info.novatec.testit.webtester.events.browser.SwitchedToWindowEvent;
import info.novatec.testit.webtester.pagefragments.PageFragment;


@RunWith(MockitoJUnitRunner.class)
public class FocusSetterTest {

    private static final int INDEX = 42;
    private static final String NAME_OR_ID = "fooBar";
    private static final String NAME_OR_HANDLE = NAME_OR_ID;

    @Mock
    Configuration configuration;
    @Mock
    WebDriver webDriver;
    @Mock
    TargetLocator targetLocator;

    Browser browser;
    EventSystem eventSystem;
    FocusSetter cut;

    @Before
    public void init() throws IOException {
        doReturn(targetLocator).when(webDriver).switchTo();
        doReturn(true).when(configuration).isEventSystemEnabled();
        browser = WebDriverBrowser.forWebDriver(webDriver).withConfiguration(configuration).build();
        eventSystem = browser.events();
        cut = new FocusSetter(browser);
    }

    /* focusing on frame by index */

    @Test
    public void switchingToFrameByIndexInvokesCorrectLocatorMethod() {
        executeOnFrameByIndex();
        verify(targetLocator).frame(INDEX);
    }

    @Test
    public void switchingToFrameByIndexFiresEvent() {
        EventCaptor.capture(eventSystem, SwitchedToFrameEvent.class)
            .execute(this::executeOnFrameByIndex)
            .assertEventWasFired()
            .assertEvent(event -> assertThat(event.getTarget()).isEqualTo(String.valueOf(INDEX)));
    }

    @Test
    public void switchingToFrameByUnknownIndexThrowsException() {
        doThrow(mock(NoSuchFrameException.class)).when(targetLocator).frame(anyInt());
        ExceptionEventCaptor.capture(eventSystem, NoSuchFrameException.class)
            .execute(this::executeOnFrameByIndex)
            .assertExceptionWasThrown()
            .assertExceptionEventWasFired();
    }

    /* focusing on frame by name or id */

    @Test
    public void switchingToFrameByNameOrIdInvokesCorrectLocatorMethod() {
        executeOnFrameByNameOrId();
        verify(targetLocator).frame(NAME_OR_ID);
    }

    @Test
    public void switchingToFrameByNameOrIdFiresEvent() {
        EventCaptor.capture(eventSystem, SwitchedToFrameEvent.class)
            .execute(this::executeOnFrameByNameOrId)
            .assertEventWasFired()
            .assertEvent(event -> assertThat(event.getTarget()).isEqualTo(NAME_OR_ID));
    }

    @Test
    public void switchingToFrameByUnknownNameOrIdThrowsException() {
        doThrow(mock(NoSuchFrameException.class)).when(targetLocator).frame(anyString());
        ExceptionEventCaptor.capture(eventSystem, NoSuchFrameException.class)
            .execute(this::executeOnFrameByNameOrId)
            .assertExceptionWasThrown()
            .assertExceptionEventWasFired();
    }

    /* focusing on frame by page fragment */

    @Test
    public void switchingToFrameByPageFragmentInvokesCorrectLocatorMethod() {
        PageFragment fragment = MockFactory.fragment().build();
        cut.onFrame(fragment);
        verify(targetLocator).frame(fragment.webElement());
    }

    @Test
    public void switchingToFrameByPageFragmentFiresEvent() {
        PageFragment fragment = MockFactory.fragment().withName("The Name").build();
        EventCaptor.capture(eventSystem, SwitchedToFrameEvent.class)
            .execute(() ->  cut.onFrame(fragment))
            .assertEventWasFired()
            .assertEvent(event -> assertThat(event.getTarget()).isEqualTo("The Name"));
    }

    /* focusing on window by name or handle */

    @Test
    public void switchingToWindowByNameOrHandleInvokesCorrectLocatorMethod() {
        executeOnWindowByNameOrHandle();
        verify(targetLocator).window(NAME_OR_HANDLE);
    }

    @Test
    public void switchingToWindowByNameOrHandleFiresEvent() {
        EventCaptor.capture(eventSystem, SwitchedToWindowEvent.class)
            .execute(this::executeOnWindowByNameOrHandle)
            .assertEventWasFired()
            .assertEvent(event -> assertThat(event.getNameOrHandle()).isEqualTo(NAME_OR_HANDLE));
    }

    @Test
    public void switchingToWindowByUnknownNameOrHandleThrowsException() {
        doThrow(mock(NoSuchWindowException.class)).when(targetLocator).window(anyString());
        ExceptionEventCaptor.capture(eventSystem, NoSuchWindowException.class)
            .execute(this::executeOnWindowByNameOrHandle)
            .assertExceptionWasThrown()
            .assertExceptionEventWasFired();
    }

    /* focusing on default content */

    @Test
    public void switchingToDefaultContentInvokesCorrectLocatorMethod() {
        executeOnDefaultContent();
        verify(targetLocator).defaultContent();
    }

    @Test
    public void switchingToDefaultContentFiresEvent() {
        EventCaptor.capture(eventSystem, SwitchedToDefaultContentEvent.class)
            .execute(this::executeOnDefaultContent)
            .assertEventWasFired();
    }

    /* method execution */

    void executeOnFrameByIndex() {
        cut.onFrame(INDEX);
    }

    void executeOnFrameByNameOrId() {
        cut.onFrame(NAME_OR_ID);
    }

    void executeOnWindowByNameOrHandle() {
        cut.onWindow(NAME_OR_HANDLE);
    }

    void executeOnDefaultContent() {
        cut.onDefaultContent();
    }

}
