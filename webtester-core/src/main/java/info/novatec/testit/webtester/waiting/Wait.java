package info.novatec.testit.webtester.waiting;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import lombok.Setter;

import info.novatec.testit.webtester.pagefragments.PageFragment;


/**
 * This class provides a fluent API for wait operations.
 * <p>
 * <b>Example:</b>
 * <pre>
 * Wait.exactly(5, TimeUnit.SECONDS);
 * Wait.until(fragment).is(visible());
 * Wait.withTimeoutOf(5).until(fragment).is(visible());
 * Wait.withTimeoutOf(5, TimeUnit.SECONDS).until(fragment).is(visible());
 * Wait.until(fragment).is(visible()).but().not(editable());
 * </pre>
 *
 * @see Waiter
 * @since 2.0
 */
public final class Wait {

    /** The default {@link Waiter} supplier. Generates a new {@link DefaultWaiter} for each call. */
    public static final Supplier<Waiter> DEFAULT_WAITER = DefaultWaiter::new;

    /**
     * A supplier used to get a {@link Waiter} instance to use when executing any wait operations.
     * The supplier can be changed externally to customize the waiting behavior.
     * Since this is a static field you should keep in mind that this will have an JVM global effect!
     * <p>
     * The default supplier is {@link #DEFAULT_WAITER}.
     */
    @Setter
    private static Supplier<Waiter> waiter = DEFAULT_WAITER;

    private Wait() {
        // utility class constructor
    }

    /**
     * Creates a {@link ConfiguredWait configured fluent wait} with a custom timeout.
     * <p>
     * <b>Note:</b> see {@link WaitConfig} for defaults besides the timeout!
     *
     * @param timeout the timeout in seconds
     * @return the fluent wait instance
     * @see Wait
     * @see ConfiguredWait
     * @see WaitConfig
     * @since 2.0
     */
    public static ConfiguredWait withTimeoutOf(int timeout) {
        return new ConfiguredWait(waiter.get(), new WaitConfig().setTimeout(timeout));
    }

    /**
     * Creates a {@link ConfiguredWait configured fluent wait} with a custom timeout and time unit.
     * <p>
     * <b>Note:</b> see {@link WaitConfig} for defaults besides the timeout!
     *
     * @param timeout the timeout value
     * @param timeUnit the time unit the value is specified as
     * @return the fluent wait instance
     * @see Wait
     * @see ConfiguredWait
     * @see WaitConfig
     * @since 2.0
     */
    public static ConfiguredWait withTimeoutOf(int timeout, TimeUnit timeUnit) {
        return new ConfiguredWait(waiter.get(), new WaitConfig().setTimeout(timeout).setTimeUnit(timeUnit));
    }

    /**
     * Creates a {@link WaitUntil} with the default timeout configuration of {@link WaitConfig} for the given object.
     *
     * @param object the object for the wait until operation
     * @param <T> the type of the object
     * @return the fluent wait instance
     * @see Wait
     * @see WaitUntil
     * @see WaitConfig
     * @since 2.0
     */
    public static <T> WaitUntil<T> until(T object) {
        return new WaitUntil<>(waiter.get(), new WaitConfig(), object);
    }

    /**
     * Creates a {@link WaitUntil} with the default timeout from the given {@link PageFragment}'s configuration.
     *
     * @param fragment the fragment for the wait until operation
     * @param <T> the type of the page fragment subclass
     * @return the fluent wait instance
     * @see Wait
     * @see WaitUntil
     * @see WaitConfig
     * @since 2.0
     */
    public static <T extends PageFragment> WaitUntil<T> until(T fragment) {
        return new WaitUntil<>(waiter.get(), WaitConfig.from(fragment), fragment);
    }

    /**
     * Waits the given amount of time with the given unit.
     * <p>
     * <b>Example:</b>
     * <pre>
     * Wait.exactly(10, TimeUnit.SECONDS);
     * Wait.exactly(1, TimeUnit.MINUTE);
     * </pre>
     *
     * @param duration the amount of time to wait
     * @param timeUnit the time unit to us
     * @since 2.0
     */
    public static void exactly(long duration, TimeUnit timeUnit) {
        waiter.get().waitExactly(duration, timeUnit);
    }

}
