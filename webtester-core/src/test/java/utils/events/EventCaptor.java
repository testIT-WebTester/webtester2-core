package utils.events;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

import info.novatec.testit.webtester.events.Event;
import info.novatec.testit.webtester.events.EventSystem;


public class EventCaptor<E extends Event> {

    private final EventSystem eventSystem;
    private final Class<E> eventClass;
    private SingleEventCollectingListener listener;
    private Optional<Object> returnValue = Optional.empty();

    private EventCaptor(EventSystem eventSystem, Class<E> eventClass) {
        this.eventSystem = eventSystem;
        this.eventClass = eventClass;
    }

    public EventCaptor<E> execute(Supplier<?> supplier) {
        listener = new SingleEventCollectingListener();
        eventSystem.register(listener);
        try {
            returnValue = Optional.ofNullable(supplier.get());
        } finally {
            eventSystem.deregister(listener);
        }
        return this;
    }

    public EventCaptor<E> execute(Runnable runnable) {
        listener = new SingleEventCollectingListener();
        eventSystem.register(listener);
        try {
            runnable.run();
        } finally {
            eventSystem.deregister(listener);
        }
        return this;
    }

    public EventCaptor<E> assertEventWasFired() throws ClassCastException {
        Event event = listener.getEvent();
        assertThat(event).withFailMessage("no event was captured").isNotNull();
        assertThat(event).isExactlyInstanceOf(eventClass);
        return this;
    }

    public void assertEventWasNotFired() {
        Event event = listener.getEvent();
        assertThat(event).isNull();
    }

    public EventCaptor<E> assertEvent(Consumer<E> consumer) {
        consumer.accept(listener.getEvent());
        return this;
    }

    public static <E extends Event> EventCaptor<E> capture(EventSystem eventSystem, Class<E> eventClass) {
        return new EventCaptor<>(eventSystem, eventClass);
    }

}
