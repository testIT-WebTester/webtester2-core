package utils.events;

import static org.assertj.core.api.Assertions.assertThat;

import info.novatec.testit.webtester.events.Event;
import info.novatec.testit.webtester.events.EventListener;


class SingleEventCollectingListener implements EventListener {

    private Event event;

    @Override
    public void eventOccurred(Event event) {
        // only one event is allowed to be received!
        assertThat(this.event).isNull();
        this.event = event;
    }

    public void assertEventWasFired() {
        assertThat(event).isNotNull();
    }

    public void assertEventHasType(Class<? extends Event> eventType) {
        assertThat(event).isExactlyInstanceOf(eventType);
    }

    @SuppressWarnings("unchecked")
    public <T extends Event> T getEvent() {
        return ( T ) event;
    }

}
