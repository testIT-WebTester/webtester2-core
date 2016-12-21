package info.novatec.testit.webtester.events.browser;

import static java.lang.String.format;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import info.novatec.testit.webtester.browser.operations.AlertHandler;
import info.novatec.testit.webtester.events.AbstractEvent;
import info.novatec.testit.webtester.events.Event;
import info.novatec.testit.webtester.events.EventListener;
import info.novatec.testit.webtester.events.EventSystem;


/**
 * This {@link Event event} occurs whenever an alert dialog was accepted.
 * <p>
 * It includes the alert's message as a property.
 *
 * @see Event
 * @see EventListener
 * @see EventSystem
 * @see AlertHandler#accept()
 * @see AlertHandler#acceptIfPresent()
 * @since 2.0
 */
@Getter
@AllArgsConstructor
@SuppressWarnings("serial")
public class AcceptedAlertEvent extends AbstractEvent {

    @NonNull
    private final String alertMessage;

    @Override
    public String describe() {
        return format("accepted an alert with message: '%s'", alertMessage);
    }

}
