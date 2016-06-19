package info.novatec.testit.webtester.events;

import java.util.LinkedList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import info.novatec.testit.webtester.browser.Browser;


@Slf4j
public class EventSystemImpl implements EventSystem {

    private final List<EventListener> listeners;
    private final Browser browser;

    public EventSystemImpl(Browser browser) {
        this.listeners = new LinkedList<>();
        this.browser = browser;
    }

    @Override
    public void register(EventListener listener) {
        listeners.add(listener);
        log.debug("registered listener: {}", listener);
    }

    @Override
    public void deregister(EventListener listener) {
        listeners.remove(listener);
        log.debug("deregistered listener: {}", listener);
    }

    @Override
    public void clearListeners() {
        listeners.clear();
        log.debug("cleared all listener");
    }

    @Override
    public void fireEvent(Event event) {
        if (isEnabled() || event instanceof ExceptionEvent) {
            doFireEvent(event);
        } else {
            log.warn("tried to fire event '{}' but event system is disabled", event);
        }
    }

    @Override
    public void fireExceptionEvent(Throwable e) {
        doFireEvent(new ExceptionEvent(e));
    }

    private void doFireEvent(Event event) {
        log.debug("firing event: {}", event.describe());
        listeners.forEach(listener -> tryToInformListenerOfEvent(event, listener));
    }

    @Override
    public boolean isEnabled() {
        return browser.configuration().isEventSystemEnabled();
    }

    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    private void tryToInformListenerOfEvent(Event event, EventListener listener) {
        log.trace("informing listener {} about event {}", listener, event);
        try {
            listener.eventOccurred(event);
        } catch (RuntimeException e) {
            log.warn("exception while calling event listener: " + listener, e);
        }
    }

}
