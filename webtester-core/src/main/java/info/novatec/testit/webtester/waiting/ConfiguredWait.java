package info.novatec.testit.webtester.waiting;

import java.util.concurrent.TimeUnit;

import info.novatec.testit.webtester.pagefragments.PageFragment;


/**
 * This class is used to configure {@link Wait} operations with custom settings like the {@code timeout}.
 *
 * @see Wait
 * @see WaitUntil
 * @since 2.0
 */
public class ConfiguredWait {

    private final long timeout;
    private final TimeUnit timeUnit;

    /**
     * Creates a new {@link ConfiguredWait} instance with the given timeout in seconds.
     *
     * @param timeout the timeout to use
     * @since 2.0
     */
    public ConfiguredWait(long timeout) {
        this(timeout, TimeUnit.SECONDS);
    }

    /**
     * Creates a new {@link ConfiguredWait} instance with the given timeout in the given {@link TimeUnit}.
     *
     * @param timeout the timeout to use
     * @param timeUnit the unit of time to use
     * @since 2.0
     */
    public ConfiguredWait(long timeout, TimeUnit timeUnit) {
        this.timeout = timeout;
        this.timeUnit = timeUnit;
    }

    /**
     * Creates a {@link WaitUntil fluent wait until} with the timeout settings of this {@link ConfiguredWait} for the given
     * {@link PageFragment}.
     *
     * @param fragment the fragment for the wait until operation
     * @return the fluent wait instance
     * @see Wait
     * @see WaitUntil
     * @since 2.0
     */
    public <T extends PageFragment> WaitUntil<T> until(T fragment) {
        return new WaitUntil<>(fragment, timeout, timeUnit);
    }

}
