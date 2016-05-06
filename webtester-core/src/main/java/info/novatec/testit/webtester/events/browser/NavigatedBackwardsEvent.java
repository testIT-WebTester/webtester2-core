package info.novatec.testit.webtester.events.browser;

import info.novatec.testit.webtester.browser.operations.Navigate;
import info.novatec.testit.webtester.events.Event;
import info.novatec.testit.webtester.events.EventListener;
import info.novatec.testit.webtester.events.EventSystem;
import info.novatec.testit.webtester.events.AbstractEvent;


/**
 * This {@link Event event} occurs whenever a navigate backwards was executed.
 *
 * @see Event
 * @see EventListener
 * @see EventSystem
 * @see Navigate#backwards()
 * @see Navigate#backwards(int)
 * @since 2.0
 */
@SuppressWarnings("serial")
public class NavigatedBackwardsEvent extends AbstractEvent {

    @Override
    public String describe() {
        return "navigated back in the browser history";
    }

}
