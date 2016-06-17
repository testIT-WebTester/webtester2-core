package info.novatec.testit.webtester.waiting;

import java.util.concurrent.TimeUnit;

import info.novatec.testit.webtester.pagefragments.PageFragment;


/**
 * This class aims to provide a fluent API for wait operations.
 * It is currently considered 'beta' and is subject to (possibly breaking) changes.
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
 * @see WaitOperations
 * @since 2.0
 */
public final class Wait {

    private Wait() {
        // utility class constructor
    }

    /**
     * Creates a {@link ConfiguredWait configured fluent wait} with a custom timeout.
     *
     * @param timeout the timeout in seconds
     * @return the fluent wait instance
     * @see Wait
     * @see ConfiguredWait
     * @since 2.0
     */
    public static ConfiguredWait withTimeoutOf(long timeout) {
        return withTimeoutOf(timeout, TimeUnit.SECONDS);
    }

    /**
     * Creates a {@link ConfiguredWait configured fluent wait} with a custom timeout and time unit.
     *
     * @param timeout the timeout value
     * @param timeUnit the time unit the value is specified as
     * @return the fluent wait instance
     * @see Wait
     * @see ConfiguredWait
     * @since 2.0
     */
    public static ConfiguredWait withTimeoutOf(long timeout, TimeUnit timeUnit) {
        return new ConfiguredWait(timeout, timeUnit);
    }

    /**
     * Creates a {@link WaitUntil fluent wait until} with the default timeout of the browser the given
     * {@link PageFragment fragment} was created in.
     *
     * @param fragment the fragment for the wait until operation
     * @param <T> the type of the page fragment subclass
     * @return the fluent wait instance
     * @see Wait
     * @see WaitUntil
     * @since 2.0
     */
    public static <T extends PageFragment> WaitUntil<T> until(T fragment) {
        return new WaitUntil<>(fragment);
    }

    /**
     * Waits the given amount of time with the given unit.
     *
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
        WaitOperations.wait(duration, timeUnit);
    }

}
