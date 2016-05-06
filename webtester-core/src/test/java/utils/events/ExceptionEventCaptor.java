package utils.events;

import static org.assertj.core.api.Assertions.assertThat;

import info.novatec.testit.webtester.events.Event;
import info.novatec.testit.webtester.events.EventSystem;
import info.novatec.testit.webtester.events.ExceptionEvent;


public class ExceptionEventCaptor {

    private final EventSystem eventSystem;
    private final Class<? extends RuntimeException> exceptionClass;
    private SingleEventCollectingListener listener;
    private RuntimeException exception;

    private ExceptionEventCaptor(EventSystem eventSystem, Class<? extends RuntimeException> exceptionClass) {
        this.eventSystem = eventSystem;
        this.exceptionClass = exceptionClass;
    }

    public ExceptionEventCaptor execute(Runnable runnable) {
        listener = new SingleEventCollectingListener();
        eventSystem.register(listener);
        try {
            runnable.run();
        } catch (RuntimeException e) {
            exception = e;
        } finally {
            eventSystem.deregister(listener);
        }
        return this;
    }

    public ExceptionEventCaptor assertExceptionWasThrown() {
        assertThat(exception).isNotNull();
        assertThat(exception).isInstanceOf(exceptionClass);
        return this;
    }

    public ExceptionEventCaptor assertExceptionEventWasFired() {
        Event event = listener.getEvent();
        assertThat(event).isNotNull();
        assertThat(event).isExactlyInstanceOf(ExceptionEvent.class);
        assertThat((( ExceptionEvent ) event).getException()).isSameAs(exception);
        return this;
    }

    public static ExceptionEventCaptor capture(EventSystem eventSystem, Class<? extends RuntimeException> exceptionClass) {
        return new ExceptionEventCaptor(eventSystem, exceptionClass);
    }

}
