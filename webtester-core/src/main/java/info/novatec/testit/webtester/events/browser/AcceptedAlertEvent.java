package info.novatec.testit.webtester.events.browser;

import static java.lang.String.format;

import lombok.Getter;

import info.novatec.testit.webtester.browser.operations.AlertHandler;
import info.novatec.testit.webtester.events.Event;
import info.novatec.testit.webtester.events.EventListener;
import info.novatec.testit.webtester.events.EventSystem;
import info.novatec.testit.webtester.events.AbstractEvent;


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
@SuppressWarnings("serial")
@Getter
public class AcceptedAlertEvent extends AbstractEvent {

    private final String alertMessage;

    public AcceptedAlertEvent(String alertMessage) {
        this.alertMessage = alertMessage;
    }

    @Override
    public String describe() {
        return format("accepted an alert message with message: %s", alertMessage);
    }

}
