package info.novatec.testit.webtester.events.browser;

import info.novatec.testit.webtester.browser.operations.Navigator;
import info.novatec.testit.webtester.events.Event;
import info.novatec.testit.webtester.events.EventListener;
import info.novatec.testit.webtester.events.EventSystem;
import info.novatec.testit.webtester.events.AbstractEvent;


/**
 * This {@link Event event} occurs whenever a navigate forward was executed.
 *
 * @see Event
 * @see EventListener
 * @see EventSystem
 * @see Navigator#forwards()
 * @see Navigator#forwards(int)
 * @since 2.0
 */
@SuppressWarnings("serial")
public class NavigatedForwardsEvent extends AbstractEvent {

    @Override
    public String describe() {
        return "navigated forward in the browser history";
    }

}
