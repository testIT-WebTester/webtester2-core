package info.novatec.testit.webtester.events.browser;

import info.novatec.testit.webtester.browser.operations.Window;
import info.novatec.testit.webtester.events.Event;
import info.novatec.testit.webtester.events.EventListener;
import info.novatec.testit.webtester.events.EventSystem;
import info.novatec.testit.webtester.events.AbstractEvent;


/**
 * This {@link Event event} occurs whenever a window was closed.
 *
 * @see Event
 * @see EventListener
 * @see EventSystem
 * @see Window#close()
 * @since 2.0
 */
@SuppressWarnings("serial")
public class ClosedWindowEvent extends AbstractEvent {

    @Override
    public String describe() {
        return "closed window";
    }

}
