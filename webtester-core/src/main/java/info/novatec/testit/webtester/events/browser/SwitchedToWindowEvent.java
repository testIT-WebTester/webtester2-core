package info.novatec.testit.webtester.events.browser;

import static java.lang.String.format;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import info.novatec.testit.webtester.browser.operations.FocusSetter;
import info.novatec.testit.webtester.events.AbstractEvent;
import info.novatec.testit.webtester.events.Event;
import info.novatec.testit.webtester.events.EventListener;
import info.novatec.testit.webtester.events.EventSystem;


/**
 * This {@link Event event} occurs whenever a switch to another window occurred.
 * <p>
 * It includes the name or handle of that window as a property.
 *
 * @see Event
 * @see EventListener
 * @see EventSystem
 * @see FocusSetter#onWindow(String)
 * @since 2.0
 */
@Getter
@AllArgsConstructor
@SuppressWarnings("serial")
public class SwitchedToWindowEvent extends AbstractEvent {

    @NonNull
    private final String nameOrHandle;

    @Override
    public String describe() {
        return format("switched to frame using name or handle: '%s'", nameOrHandle);
    }

}
