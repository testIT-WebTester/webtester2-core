package info.novatec.testit.webtester.events.browser;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.events.Event;
import info.novatec.testit.webtester.events.EventListener;
import info.novatec.testit.webtester.events.EventSystem;
import info.novatec.testit.webtester.events.AbstractEvent;


/**
 * This {@link Event event} occurs whenever a browser was closed.
 *
 * @see Event
 * @see EventListener
 * @see EventSystem
 * @see Browser#close()
 * @since 2.0
 */
@SuppressWarnings("serial")
public class ClosedBrowserEvent extends AbstractEvent {

    @Override
    public String describe() {
        return "closed browser";
    }

}
