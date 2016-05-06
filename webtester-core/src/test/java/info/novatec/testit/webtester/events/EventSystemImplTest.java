package info.novatec.testit.webtester.events;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.config.Configuration;


@RunWith(MockitoJUnitRunner.class)
public class EventSystemImplTest {

    @Mock
    private EventListener listener1;
    @Mock
    private EventListener listener2;
    @Mock
    private Event event;

    private EventSystemImpl cut;

    @Before
    public void init(){

        Configuration configuration = mock(Configuration.class);
        doReturn(true).when(configuration).isEventSystemEnabled();

        Browser browser = mock(Browser.class);
        doReturn(configuration).when(browser).configuration();

        cut = new EventSystemImpl(browser);

    }

    @Test
    public void testThatListenersAreNotifiedOfEventsInOrderOfTheirRegistration() {

        cut.register(listener1);
        cut.register(listener2);
        cut.fireEvent(event);

        InOrder inOrder = inOrder(listener1, listener2);
        inOrder.verify(listener1).eventOccurred(event);
        inOrder.verify(listener2).eventOccurred(event);
        inOrder.verifyNoMoreInteractions();

    }

    @Test
    public void testThatUnregisteredListenersAreNoLongerNotifiedOfEvents() {

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
    public void testThatAllExceptionsWhileInformingAListenerOfAnEventAreIgnored() {

        doThrow(RuntimeException.class).when(listener1).eventOccurred(event);

        cut.register(listener1);
        cut.register(listener2);
        cut.fireEvent(event);

        verify(listener1).eventOccurred(event);
        verify(listener2).eventOccurred(event);
        verifyNoMoreInteractions(listener1, listener2);

    }

    @Test
    public void testThatClearingAllListenersNoLongerNotifiesPreviouslyRegisteredListeners() {

        cut.register(listener1);
        cut.register(listener2);
        cut.clearListeners();
        cut.fireEvent(event);

        verifyZeroInteractions(listener1, listener2);

    }

}
