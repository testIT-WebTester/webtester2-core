package info.novatec.testit.webtester.events;

import java.time.LocalDateTime;


/**
 * Represents an event in the framework. An event can be something simple as clicking a button or something as complex as
 * taking a screenshot.
 * <p>
 * Events usually contain information about what happened to prompt the firing of the event.
 * I.e. this could be the text before and after for a text set event.
 *
 * @see EventListener
 * @see EventSystemImpl
 * @since 2.0
 */
public interface Event {

    /**
     * The exact point in time the event occurred.
     *
     * @return the timestamp
     * @see LocalDateTime
     * @since 2.0
     */
    LocalDateTime getTimestamp();

    /**
     * A textual description of the event. Intended to be understandable to humans.
     *
     * @return the event description
     * @since 2.0
     */
    String describe();

}
