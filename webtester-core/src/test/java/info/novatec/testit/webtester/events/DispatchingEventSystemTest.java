package info.novatec.testit.webtester.events;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import info.novatec.testit.webtester.browser.Browser;


@RunWith(MockitoJUnitRunner.class)
public class DispatchingEventSystemTest {

    @Mock
    EventListener listener1;
    @Mock
    EventListener listener2;
    @Mock
    Event event;
    @Mock
    ExceptionEvent exceptionEvent;
    @Captor
    ArgumentCaptor<Event> eventCaptor;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    Browser browser;
    @InjectMocks
    DispatchingEventSystem cut;

    @Before
    public void eventSystemIsEnabledByDefault() {
        when(browser.configuration().isEventSystemEnabled()).thenReturn(true);
    }

    @Test
    public void listenersAreNotifiedInOrderOfRegistration() {

        cut.register(listener1);
        cut.register(listener2);
        cut.fireEvent(event);

        InOrder inOrder = inOrder(listener1, listener2);
        inOrder.verify(listener1).eventOccurred(event);
        inOrder.verify(listener2).eventOccurred(event);
        inOrder.verifyNoMoreInteractions();

    }

    @Test
    public void unregisteredListenersAreNoLongerNotified() {

        cut.register(listener1);
        cut.register(listener2);
        cut.fireEvent(event);
        cut.deregister(listener1);
        cut.fireEvent(event);

        InOrder inOrder = inOrder(listener1, listener2);
        inOrder.verify(listener1).eventOccurred(event);
        inOrder.verify(listener2, times(2)).eventOccurred(event);
        inOrder.verifyNoMoreInteractions();

    }

    @Test
    public void allListenersAreNotifiedEventWhenAnExceptionOccurs() {

        doThrow(RuntimeException.class).when(listener1).eventOccurred(event);

        cut.register(listener1);
        cut.register(listener2);
        cut.fireEvent(event);

        verify(listener1).eventOccurred(event);
        verify(listener2).eventOccurred(event);
        verifyNoMoreInteractions(listener1, listener2);

    }

    @Test
    public void allListenersCanBeClearedAtOnce() {

        cut.register(listener1);
        cut.register(listener2);
        cut.clearListeners();
        cut.fireEvent(event);

        verifyNoInteractions(listener1, listener2);

    }

    @Test
    public void onlyExceptionEventsAreFiredWhenEventsAreDisabled() {

        when(browser.configuration().isEventSystemEnabled()).thenReturn(false);

        cut.register(listener1);
        cut.fireEvent(event);
        cut.fireEvent(exceptionEvent);

        verify(listener1).eventOccurred(exceptionEvent);
        verifyNoMoreInteractions(listener1);

    }

    @Test
    public void exceptionsCanBeFiredAsEvents() {

        Throwable exception = mock(Throwable.class);

        cut.register(listener1);
        cut.fireExceptionEvent(exception);

        verify(listener1).eventOccurred(eventCaptor.capture());

        Event value = eventCaptor.getValue();
        assertThat(value).isInstanceOf(ExceptionEvent.class);
        assertThat((( ExceptionEvent ) value).getException()).isSameAs(exception);

    }

}
