package info.novatec.testit.webtester.junit5.extensions.eventlistener;

import info.novatec.testit.webtester.events.Event;
import info.novatec.testit.webtester.events.EventListener;


public class MyEventListener implements EventListener {

    @Override
    public void eventOccurred(Event event) {
        System.out.println("EVENT OCCURED:" + event.describe());
    }

}
