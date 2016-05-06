package info.novatec.testit.webtester.events;

/**
 * Provides methods to {@link #register(EventListener) register} and {@link #deregister(EventListener)
 * deregister} an {@link EventListener event listener}.
 * <p>
 * These listeners are informed of {@link Event events} which are {@link #fireEvent(Event) fired} in via this class as well.
 * <p>
 * Event listeners will be informed of events in the order they were registered. Each call to an event listener is done
 * synchronously.
 *
 * @see Event
 * @see EventListener
 * @since 2.0
 */
public interface EventSystem {

    /**
     * Registers an {@link EventListener event listener}.
     * <p>
     * The listener will then be informed of any {@link Event events} that are reported by the framework.
     *
     * @param listener the {@link EventListener event listener} to register
     * @see Event
     * @see EventListener
     * @since 2.0
     */
    void register(EventListener listener);

    /**
     * Deregisters an {@link EventListener event listener}.
     * <p>
     * The listener will no longer be informed of any {@link Event events} that are reported by the framework.
     *
     * @param listener the {@link EventListener event listener} to deregister
     * @see Event
     * @see EventListener
     * @since 2.0
     */
    void deregister(EventListener listener);

    /**
     * Removes all {@link EventListener event listeners} from the registry.
     *
     * @see EventListener
     * @since 2.0
     */
    void clearListeners();

    /**
     * Informs all of the currently registered {@link EventListener event listeners} of the given {@link Event event}.
     * <p>
     * Any {@link RuntimeException undeclared exceptions} thrown by any of the registered listeners will be logged but
     * otherwise ignored.
     *
     * @param event the {@link Event event} to fire
     * @see Event
     * @see EventListener
     * @since 2.0
     */
    void fireEvent(Event event);

    /**
     * Informs all of the currently registered {@link EventListener event listeners} of an exception by creating and firing
     * a new {@link ExceptionEvent}.
     *
     * @param e the exception to package as an exception event
     * @see #fireEvent(Event)
     * @see ExceptionEvent
     * @see Event
     * @see EventListener
     * @since 2.0
     */
    void fireExceptionEvent(Throwable e);

    /**
     * Returns whether or not the event system is enabled.
     * This is a hint for event firing code to check before creating an event.
     * <p>
     * <b>Important:</b> {@link ExceptionEvent exception events} will still be fired!
     *
     * @return true if the event system is enabled
     * @see Event
     * @see EventListener
     * @since 2.0
     */
    boolean isEnabled();

}
