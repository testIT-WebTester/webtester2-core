package info.novatec.testit.webtester.events.browser;

import info.novatec.testit.webtester.browser.operations.Navigator;
import info.novatec.testit.webtester.events.AbstractEvent;
import info.novatec.testit.webtester.events.Event;
import info.novatec.testit.webtester.events.EventListener;
import info.novatec.testit.webtester.events.EventSystem;


/**
 * This {@link Event event} occurs whenever a navigate backwards was executed.
 *
 * @see Event
 * @see EventListener
 * @see EventSystem
 * @see Navigator#backwards()
 * @see Navigator#backwards(int)
 * @since 2.0
 */
@SuppressWarnings("serial")
public class NavigatedBackwardsEvent extends AbstractEvent {

    @Override
    public String describe() {
        return "navigated back in the browser history";
    }

}
