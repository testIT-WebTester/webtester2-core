package utils.events;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import info.novatec.testit.webtester.events.Event;
import info.novatec.testit.webtester.events.EventSystem;


public class MultiEventCaptor<E extends Event> {

    private final EventSystem eventSystem;
    private final Class<E> eventClass;
    private MultiEventCollectingListener listener;

    private MultiEventCaptor(EventSystem eventSystem, Class<E> eventClass) {
        this.eventSystem = eventSystem;
        this.eventClass = eventClass;
    }

    public MultiEventCaptor<E> execute(Runnable runnable) {
        listener = new MultiEventCollectingListener();
        eventSystem.register(listener);
        try {
            runnable.run();
        } finally {
            eventSystem.deregister(listener);
        }
        return this;
    }

    public MultiEventCaptor<E> assertEventsWereFired(int count) throws ClassCastException {
        List<Event> events = listener.getEvents();
        assertThat(events).hasSize(count);
        events.forEach(event -> assertThat(event).isExactlyInstanceOf(eventClass));
        return this;
    }

    public void assertEventsWereNotFired() {
        List<Event> events = listener.getEvents();
        assertThat(events).isEmpty();
    }

    public static <E extends Event> MultiEventCaptor<E> capture(EventSystem eventSystem, Class<E> eventClass) {
        return new MultiEventCaptor<>(eventSystem, eventClass);
    }

}
