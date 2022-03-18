package info.novatec.testit.webtester.events.listeners;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.events.AbstractEvent;
import info.novatec.testit.webtester.events.ExceptionEvent;


@RunWith(MockitoJUnitRunner.class)
public class TakeScreenshotOnExceptionListenerTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    Browser browser;
    @InjectMocks
    TakeScreenshotOnExceptionListener cut;

    @Test
    public void screenshotIsTakenForExceptionEvents() {
        ExceptionEvent event = mock(ExceptionEvent.class);
        cut.eventOccurred(event);
        verify(browser.screenshot()).takeAndStore();
    }

    @Test
    public void screenshotIsNotTakenForNonExceptionEvents() {
        AbstractEvent event = mock(AbstractEvent.class);
        cut.eventOccurred(event);
        verifyNoInteractions(browser);
    }

}
