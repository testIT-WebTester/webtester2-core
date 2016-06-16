package info.novatec.testit.webtester.waiting;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import lombok.extern.slf4j.Slf4j;

import info.novatec.testit.webtester.conditions.Condition;
import info.novatec.testit.webtester.pagefragments.PageFragment;


/**
 * Provides methods for waiting. Among these are standard 'Wait a given amount of time' waits as well as more complicated
 * 'wait for a specific state of an page fragment waits'.
 * <p>
 * <b>Note:</b> This class is package protected because the {@link Wait} class is the primary entry point for the waiting
 * API.
 *
 * @since 2.0
 */
@Slf4j
final class WaitOperations {

    private static final String TIMEOUT_MESSAGE = "condition not met within the given timeout";

    /**
     * Waits the given duration using the given {@link TimeUnit time unit}.
     *
     * @param duration the duration
     * @param timeUnit the time unit to use when converting the duration
     */
    public static void wait(long duration, TimeUnit timeUnit) {
        try {
            Thread.sleep(timeUnit.toMillis(duration));
        } catch (InterruptedException e) {
            log.debug("wait was interrupted", e);
        }
    }

    /**
     * Waits until the given {@link Condition condition} is met by the provided
     * {@link PageFragment page fragment}. Allows for the configuration of the
     * timeout's {@link TimeUnit time unit}. The check interval of the page
     * object's browser's configuration is used.
     * <p>
     * All runtime exceptions occurring within the callback's method will be
     * ignored until the timeout is reached. At which point the latest exception
     * (if any) will be used as the cause of the thrown {@link TimeoutException}
     * .
     *
     * @param timeout the maximum amount of time the operation is retried - the
     * unit decides if its milliseconds, seconds or weeks
     * @param unit the time unit to use when interpreting the timeout
     * @param fragment the page fragment on which the condition is invoked
     * @param condition the callback logic to invoke in order to check
     * of the condition is met
     * @param <T> type of the page fragment
     * @return the page fragment instance from the parameters for use in fluent API calls
     * @throws TimeoutException in case the callback did not return
     * <code>true</code> within the allowed time frame
     */
    public static <T extends PageFragment> T waitUntil(long timeout, TimeUnit unit, T fragment,
        Condition<? super T> condition) {
        return waitUntil(timeout, unit, getWaitInterval(fragment), fragment, condition);
    }

    /**
     * Waits until the given {@link Condition condition} is met by the provided
     * {@link PageFragment page fragment}. Allows for the configuration of the
     * timeout's {@link TimeUnit time unit} and check interval.
     * <p>
     * All runtime exceptions occurring within the callback's method will be
     * ignored until the timeout is reached. At which point the latest exception
     * (if any) will be used as the cause of the thrown {@link TimeoutException}
     * .
     *
     * @param timeout the maximum amount of time the operation is retried - the
     * unit decides if its milliseconds, seconds or weeks
     * @param unit the time unit to use when interpreting the timeout
     * @param interval the interval (in ms) in which to check if the condition
     * is met
     * @param fragment the page fragment on which the condition is invoked
     * @param condition the callback logic to invoke in order to check
     * of the condition is met
     * @param <T> type of the page fragment
     * @return the page fragment instance from the parameters for use in fluent API calls
     * @throws TimeoutException in case the callback did not return
     * <code>true</code> within the allowed time frame
     */
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    public static <T extends PageFragment> T waitUntil(long timeout, TimeUnit unit, long interval, final T fragment,
        final Condition<? super T> condition) {
        try {
            waitUntil(timeout, unit, interval, () -> condition.test(fragment));
        } catch (RuntimeException e) {
            fragment.getBrowser().events().fireExceptionEvent(e);
            throw e;
        }
        return fragment;
    }

    /**
     * Waits until the given {@link Supplier condition} is met within the
     * allowed time frame (timeout). ALlows for the configuration of the used
     * {@link TimeUnit time unit} and check interval.
     * <p>
     * All runtime exceptions occurring within the callback's method will be
     * ignored until the timeout is reached. At which point the latest exception
     * (if any) will be used as the cause of the thrown {@link TimeoutException}
     * .
     *
     * @param timeout the maximum amount of time the operation is retried - the
     * unit decides if its milliseconds, seconds or weeks
     * @param unit the time unit to use when interpreting the timeout
     * @param interval the interval (in ms) in which to check if the condition
     * is met
     * @param condition the callback logic to invoke in order to check
     * of the condition is met
     * @throws TimeoutException in case the callback did not return
     * <code>true</code> within the allowed time frame
     */
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    public static void waitUntil(long timeout, TimeUnit unit, long interval, Supplier<Boolean> condition) {

        long effectiveTimeout = unit.toMillis(timeout);
        long start = now();

        boolean conditionMet = false;
        RuntimeException lastException = null;

        do {
            try {
                conditionMet = condition.get();
                log.trace("condition '{}' met: {}", condition, conditionMet);
                wait(interval, TimeUnit.MILLISECONDS);
            } catch (RuntimeException e) {
                lastException = e;
            }
        } while (!conditionMet && timeSince(start) < effectiveTimeout);

        if (!conditionMet) {
            log.debug("condition not met: {}", condition);
            if (lastException != null) {
                throw new TimeoutException(TIMEOUT_MESSAGE, lastException);
            }
            throw new TimeoutException(TIMEOUT_MESSAGE);
        } else {
            log.debug("condition met: {}", condition);
        }

    }

    private static long timeSince(long start) {
        return now() - start;
    }

    private static long now() {
        return System.currentTimeMillis();
    }

    private static long getWaitInterval(PageFragment fragment) {
        return fragment.getBrowser().configuration().getWaitInterval();
    }

    private WaitOperations() {
        // utility class constructor
    }

}
