package info.novatec.testit.webtester.events.browser;

import info.novatec.testit.webtester.browser.operations.FocusSetter;
import info.novatec.testit.webtester.events.Event;
import info.novatec.testit.webtester.events.EventListener;
import info.novatec.testit.webtester.events.EventSystem;
import info.novatec.testit.webtester.events.AbstractEvent;


/**
 * This {@link Event event} occurs whenever a switch to the default content occurred.
 *
 * @see Event
 * @see EventListener
 * @see EventSystem
 * @see FocusSetter#onDefaultContent()
 * @since 2.0
 */
@SuppressWarnings("serial")
public class SwitchedToDefaultContentEvent extends AbstractEvent {

    @Override
    public String describe() {
        return "switched to default content";
    }

}
