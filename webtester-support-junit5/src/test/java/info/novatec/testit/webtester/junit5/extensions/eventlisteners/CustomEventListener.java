package info.novatec.testit.webtester.junit5.extensions.eventlisteners;

import info.novatec.testit.webtester.events.Event;
import info.novatec.testit.webtester.events.EventListener;


public class CustomEventListener implements EventListener {

    private Event recentEvent = null;

    private boolean usedDefaultConstructor = false;

    public CustomEventListener() {
        usedDefaultConstructor = true;
    }

    public CustomEventListener(String aString) {
        //should be unused
    }

    @Override
    public void eventOccurred(Event event) {
        recentEvent = event;
    }

    Event getRecentEvent() {
        return recentEvent;
    }

    boolean isUsedDefaultConstructor() {
        return usedDefaultConstructor;
    }

    void clear() {
        recentEvent = null;
        usedDefaultConstructor = false;
    }
}
