package info.novatec.testit.webtester.events.browser;

import static java.lang.String.format;

import lombok.Getter;

import info.novatec.testit.webtester.browser.operations.Focus;
import info.novatec.testit.webtester.events.Event;
import info.novatec.testit.webtester.events.EventListener;
import info.novatec.testit.webtester.events.EventSystem;
import info.novatec.testit.webtester.events.AbstractEvent;


/**
 * This {@link Event event} occurs whenever a switch to another window occurred.
 * <p>
 * It includes the name or handle of that window as a property.
 *
 * @see Event
 * @see EventListener
 * @see EventSystem
 * @see Focus#onWindow(String)
 * @since 2.0
 */
@SuppressWarnings("serial")
@Getter
public class SwitchedToWindowEvent extends AbstractEvent {

    private final String nameOrHandle;

    public SwitchedToWindowEvent(String nameOrHandle) {
        this.nameOrHandle = nameOrHandle;
    }

    @Override
    public String describe() {
        return format("switched to frame using name or handle: %s", nameOrHandle);
    }

}
