package utils.events;

import java.util.ArrayList;
import java.util.List;

import info.novatec.testit.webtester.events.Event;
import info.novatec.testit.webtester.events.EventListener;


public class MultiEventCollectingListener implements EventListener {

    private List<Event> events = new ArrayList<>();

    @Override
    public void eventOccurred(Event event) {
        events.add(event);
    }

    @SuppressWarnings("unchecked")
    public <T extends Event> List<T> getEvents() {
        return ( List<T> ) events;
    }

}
