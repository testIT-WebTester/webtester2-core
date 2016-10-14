package info.novatec.testit.webtester.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;


/**
 * This event is thrown whenever an exception inside the framework occurred.
 * It contains the exception as a property.
 * <p>
 * This event should only be fired using {@link EventSystem#fireExceptionEvent(Throwable)}.
 * If it is fired using {@link EventSystem#fireEvent(Event)} it might be ignored in case the event system is not enabled.
 *
 * @see Event
 * @see EventListener
 * @see EventSystem
 * @since 2.0
 */
@Getter
@AllArgsConstructor
public class ExceptionEvent extends AbstractEvent {

    @NonNull
    private final Throwable exception;

    @Override
    public String describe() {
        return "exception occurred: '" + exception.getMessage() + "'";
    }

}
