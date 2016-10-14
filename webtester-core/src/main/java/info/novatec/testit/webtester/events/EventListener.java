package info.novatec.testit.webtester.events;

/**
 * Defines a listener that can be registered at the {@link DispatchingEventSystem event system}.
 * <p>
 * If an {@link Event event} is reported by the system the {@link #eventOccurred(Event)} method of all registered
 * listeners is invoked.
 *
 * @see Event
 * @see DispatchingEventSystem
 * @since 2.0
 */
public interface EventListener {

    /**
     * This method will be called if any {@link Event event} is reported by the {@link DispatchingEventSystem event system}.
     *
     * @param event the {@link Event event} that occurred.
     * @see Event
     * @see DispatchingEventSystem
     * @since 2.0
     */
    void eventOccurred(Event event);

}
