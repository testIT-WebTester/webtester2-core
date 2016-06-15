package info.novatec.testit.webtester.events.browser;

import info.novatec.testit.webtester.browser.operations.CurrentWindow;
import info.novatec.testit.webtester.events.Event;
import info.novatec.testit.webtester.events.EventListener;
import info.novatec.testit.webtester.events.EventSystem;
import info.novatec.testit.webtester.events.AbstractEvent;


/**
 * This {@link Event event} occurs whenever a page was refreshed.
 *
 * @see Event
 * @see EventListener
 * @see EventSystem
 * @see CurrentWindow#refresh()
 * @since 2.0
 */
@SuppressWarnings("serial")
public class RefreshedPageEvent extends AbstractEvent {

    @Override
    public String describe() {
        return "refreshed the page";
    }

}
