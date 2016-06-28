package info.novatec.testit.webtester.waiting;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;


/**
 * Implementations of this interface provide different kinds of wait operations.
 * The two basic types are 'wait exactly X amount of time' and 'wait until X passes a certain condition check'.
 *
 * @since 2.0
 */
public interface Waiter {

    /**
     * Waits the given amount of time using the {@link TimeUnit time unit}.
     * <p>
     * <b>Note:</b> This is only accurate to a millisecond level. Using any unit smaller than milliseconds will result in
     * the method doing nothing!
     *
     * @param duration the duration
     * @param timeUnit the time unit to use
     * @since 2.0
     */
    void waitExactly(long duration, TimeUnit timeUnit);

    /**
     * Waits until the given boolean {@link Supplier} returns <code>true</code> or the {@link WaitConfig configured} timeout
     * is reached.
     *
     * @param config the configuration to use
     * @param condition the condition to evaluate
     * @see WaitConfig
     * @since 2.0
     */
    void waitUntil(WaitConfig config, Supplier<Boolean> condition);

}
