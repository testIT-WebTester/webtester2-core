package info.novatec.testit.webtester.waiting;

import info.novatec.testit.webtester.config.Configuration;
import info.novatec.testit.webtester.config.builders.DefaultConfigurationBuilder;
import info.novatec.testit.webtester.pagefragments.PageFragment;
import lombok.Setter;
import lombok.experimental.UtilityClass;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;


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
@UtilityClass
public class Wait {

    /**
     * The default {@link Waiter} supplier. Generates a new {@link DefaultWaiter} for each call.
     */
    public static final Supplier<Waiter> DEFAULT_WAITER = DefaultWaiter::new;
    public static final Configuration DEFAULT_CONFIGURATION = new DefaultConfigurationBuilder().build();

    /**
     * A supplier used to get a {@link Waiter} instance to use when executing any wait operations.
     * The supplier can be changed externally to customize the waiting behavior.
     * Since this is a static field you should keep in mind that this will have an JVM global effect!
     * <p>
     * The default supplier is {@link #DEFAULT_WAITER}.
     */
    @Setter
    private static Supplier<Waiter> waiter = DEFAULT_WAITER;

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
        WaitConfig config = WaitConfig.from(DEFAULT_CONFIGURATION).setTimeout(timeout);
        return new ConfiguredWait(waiter.get(), config);
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
        WaitConfig config = WaitConfig.from(DEFAULT_CONFIGURATION).setTimeout(timeout).setTimeUnit(timeUnit);
        return new ConfiguredWait(waiter.get(), config);
    }

    /**
     * Creates a {@link WaitUntil} with the default timeout configuration of {@link WaitConfig} for the given object.
     *
     * @param object the object for the wait until operation
     * @param <T>    the type of the object
     * @return the fluent wait instance
     * @see Wait
     * @see WaitUntil
     * @see WaitConfig
     * @since 2.0
     */
    public static <T> WaitUntil<T> until(T object) {
        WaitConfig from = WaitConfig.from(DEFAULT_CONFIGURATION);
        return new WaitUntil<>(waiter.get(), from, object);
    }

    /**
     * Creates a {@link WaitUntil} with the default timeout configuration of {@link WaitConfig} for the given object {@link
     * Supplier}.
     *
     * @param objectSupplier the object supplier for the wait until operation
     * @param <T>            the type of the object
     * @return the fluent wait instance
     * @see Wait
     * @see WaitUntil
     * @see WaitConfig
     * @since 2.0
     */
    public static <T> WaitUntil<T> untilSupplied(Supplier<T> objectSupplier) {
        WaitConfig from = WaitConfig.from(DEFAULT_CONFIGURATION);
        return new WaitUntil<>(waiter.get(), from, objectSupplier);
    }

    /**
     * Creates a {@link WaitUntil} with the default timeout from the given {@link PageFragment}'s configuration.
     *
     * @param fragment the fragment for the wait until operation
     * @param <T>      the type of the page fragment subclass
     * @return the fluent wait instance
     * @see Wait
     * @see WaitUntil
     * @see WaitConfig
     * @since 2.0
     */
    public static <T extends PageFragment> WaitUntil<T> until(T fragment) {
        WaitConfig from = WaitConfig.from(fragment);
        return new WaitUntil<>(waiter.get(), from, fragment);
    }

    /**
     * Executes a wait operation based on the default timeout configuration of {@link WaitConfig} and the given boolean
     * supplier. The wait is executed until either the supplier returns <code>true</code> or the timeout is reached.
     *
     * @param condition the supplier for the wait until operation
     * @see Wait
     * @see WaitUntil
     * @see Waiter
     * @see WaitConfig
     * @since 2.0
     */
    public static void until(Supplier<Boolean> condition) {
        WaitConfig from = WaitConfig.from(DEFAULT_CONFIGURATION);
        waiter.get().waitUntil(from, condition);
    }

    /**
     * Executes a wait operation based on the default timeout configuration of {@link WaitConfig} and the given boolean
     * supplier. The wait is executed until either the supplier returns <code>true</code> or the timeout is reached.
     * Additionally executes a {@link WaitAction} configured by the given {@link WaitingAction}.
     *
     * @param condition     the supplier for the wait until operation
     * @param waitingAction the configured condition and action to execute during waiting
     * @see Wait
     * @see WaitUntil
     * @see Waiter
     * @see WaitConfig
     * @see WaitingAction
     * @see WaitAction
     * @since 2.8
     */
    public static void untilWithAction(Supplier<Boolean> condition, WaitingAction waitingAction) {
        WaitConfig from = WaitConfig.from(DEFAULT_CONFIGURATION);
        waiter.get().waitUntilWithAction(from, condition, waitingAction);
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
