package info.novatec.testit.webtester.waiting;

import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

import info.novatec.testit.webtester.conditions.Conditions;
import info.novatec.testit.webtester.config.Configuration;
import info.novatec.testit.webtester.pagefragments.PageFragment;


/**
 * This class offers a number of methods which allow for the waiting until a specific condition is met for a {@link
 * PageFragment}.
 *
 * @see Wait
 * @since 2.0
 */
public class WaitUntil<T extends PageFragment> {

    private final T fragment;
    private final long timeout;
    private final TimeUnit timeUnit;

    /**
     * Creates a new {@link WaitUntil} instance for the given {@link PageFragment}.
     * The timeout configuration will be taken from the fragment's browser's {@link Configuration}.
     *
     * @param fragment the fragment to use
     * @see Wait
     * @since 2.0
     */
    public WaitUntil(T fragment) {
        this.fragment = fragment;
        this.timeout = fragment.getBrowser().configuration().getWaitTimeout();
        this.timeUnit = TimeUnit.SECONDS;
    }

    /**
     * Creates a new {@link WaitUntil} instance for the given {@link PageFragment} an timeout with {@link TimeUnit}.
     *
     * @param fragment the fragment to use
     * @param timeout the timeout to use
     * @param timeUnit the time unit to use
     * @see Wait
     * @since 2.0
     */
    public WaitUntil(T fragment, long timeout, TimeUnit timeUnit) {
        this.fragment = fragment;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
    }

    /**
     * Waits until the given condition is met. A set of default conditions can be initialized from {@link Conditions}.
     *
     * @param condition the condition to wait for
     * @return the same instance for fluent API use
     * @throws TimeoutException in case the condition is not met within the configured timeout
     * @see Wait
     * @see Conditions
     * @since 2.0
     */
    public WaitUntil<T> has(Predicate<? super T> condition) throws TimeoutException {
        return is(condition);
    }

    /**
     * Waits until the given condition is met. A set of default conditions can be initialized from {@link Conditions}.
     *
     * @param condition the condition to wait for
     * @return the same instance for fluent API use
     * @throws TimeoutException in case the condition is not met within the configured timeout
     * @see Wait
     * @see Conditions
     * @since 2.0
     */
    public WaitUntil<T> is(Predicate<? super T> condition) throws TimeoutException {
        WaitOperations.waitUntil(timeout, timeUnit, fragment, condition);
        return this;
    }

    /**
     * Waits until the given condition is NOT met. A set of default conditions can be initialized from {@link Conditions}.
     *
     * @param condition the condition to wait for
     * @return the same instance for fluent API use
     * @throws TimeoutException in case the condition is not met within the configured timeout
     * @see Wait
     * @see Conditions
     * @since 2.0
     */
    public WaitUntil<T> isNot(Predicate<? super T> condition) throws TimeoutException {
        return not(condition);
    }

    /**
     * Waits until the given condition is NOT met. A set of default conditions can be initialized from {@link Conditions}.
     *
     * @param condition the condition to wait for
     * @return the same instance for fluent API use
     * @throws TimeoutException in case the condition is not met within the configured timeout
     * @see Wait
     * @see Conditions
     * @since 2.0
     */
    public WaitUntil<T> not(Predicate<? super T> condition) throws TimeoutException {
        WaitOperations.waitUntil(timeout, timeUnit, fragment, Conditions.not(condition));
        return this;
    }

    /**
     * This method does nothing by it's own. It is intended to be used in order to write more expressive linked wait
     * statements.
     * <p>
     * <b>Example:</b> {@code Wait.until(button).is(visible()).and().not(editable());}
     *
     * @return the same instance for fluent API use
     * @see Wait
     * @since 2.0
     */
    public WaitUntil<T> and() {
        return this;
    }

    /**
     * This method does nothing by it's own. It is intended to be used in order to write more expressive linked wait
     * statements.
     * <p>
     * <b>Example:</b> {@code Wait.until(button).is(visible()).but().not(editable());}
     *
     * @return the same instance for fluent API use
     * @see Wait
     * @since 2.0
     */
    public WaitUntil<T> but() {
        return this;
    }

}
