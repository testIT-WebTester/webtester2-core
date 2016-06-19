package info.novatec.testit.webtester.events;

import lombok.Getter;


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
public class ExceptionEvent extends AbstractEvent {

    private final Throwable exception;

    public ExceptionEvent(Throwable exception) {
        this.exception = exception;
    }

    @Override
    public String describe() {
        return "exception occurred: '" + exception.getMessage() + "'";
    }

}
