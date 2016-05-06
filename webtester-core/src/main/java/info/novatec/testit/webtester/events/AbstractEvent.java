package info.novatec.testit.webtester.events;

import java.time.LocalDateTime;

/**
 * Base class for all events.
 * <p>
 * It stores the event's creation timestamp as a property.
 *
 * @see Event
 * @see EventListener
 * @see EventSystem
 * @since 2.0
 */
public abstract class AbstractEvent implements Event {

    private final LocalDateTime timestamp = LocalDateTime.now();

    @Override
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

}
