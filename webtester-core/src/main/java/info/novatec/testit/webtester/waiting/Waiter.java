package info.novatec.testit.webtester.waiting;

import java.time.Clock;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;


/**
 * Provides different kinds of wait operations. The two basic types are 'wait exactly X amount of time' and 'wait until X
 * passes a certain condition check'.
 *
 * @since 2.0
 */
@Slf4j
@Getter(AccessLevel.PROTECTED)
class Waiter {

    /**
     * The {@link Sleeper} to use when actually needing to pass time.
     */
    private final Sleeper sleeper;
    /**
     * The {@link Clock} to use for tracking the passing of time.
     */
    private final Clock clock;

    /**
     * Creates a new default {@link Waiter}. It has a {@link CurrentThreadSleeper} as a sleeper and a {@link
     * Clock#systemDefaultZone()} clock.
     *
     * @see Sleeper
     * @see Clock
     * @since 2.0
     */
    public Waiter() {
        this(new CurrentThreadSleeper());
    }

    /**
     * Creates a new custom {@link Waiter}. It will use the given {@link Sleeper} and a {@link Clock#systemDefaultZone()}
     * clock.
     *
     * @see Sleeper
     * @see Clock
     * @since 2.0
     */
    public Waiter(Sleeper sleeper) {
        this(sleeper, Clock.systemDefaultZone());
    }

    /**
     * Creates a new custom {@link Waiter}. It will use the given {@link Sleeper} and a {@link Clock}.
     *
     * @see Sleeper
     * @see Clock
     * @since 2.0
     */
    public Waiter(Sleeper sleeper, Clock clock) {
        this.sleeper = sleeper;
        this.clock = clock;
    }

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
    public void waitExactly(long duration, TimeUnit timeUnit) {
        try {
            long millisToSleep = timeUnit.toMillis(duration);
            if (millisToSleep > 0) {
                sleeper.sleep(millisToSleep);
            }
        } catch (InterruptedException e) {
            log.debug("wait was interrupted", e);
        }
    }

    /**
     * Waits until the given boolean {@link Supplier} returns <code>true</code> or the {@link WaitConfig configured} timeout
     * is reached.
     *
     * @param config the configuration to use
     * @param condition the condition to evaluate
     * @see WaitConfig
     * @since 2.0
     */
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    public void waitUntil(WaitConfig config, Supplier<Boolean> condition) {

        long effectiveTimeout = config.getTimeoutInMillis();
        long start = now();

        boolean conditionMet = false;
        RuntimeException lastException = null;

        do {
            try {
                conditionMet = condition.get();
            } catch (RuntimeException e) {
                lastException = e;
            }
            log.trace("condition '{}' met: {}", condition, conditionMet);
            if (!conditionMet) {
                waitExactly(config.getInterval(), TimeUnit.MILLISECONDS);
            }
        } while (!conditionMet && timeSince(start) < effectiveTimeout);

        if (!conditionMet) {
            String message = "condition not met within the given timeout";
            log.debug("condition not met: {}", condition);
            if (lastException != null) {
                throw new TimeoutException(message, lastException);
            }
            throw new TimeoutException(message);
        } else {
            log.debug("condition met: {}", condition);
        }

    }

    private long timeSince(long start) {
        return now() - start;
    }

    private long now() {
        return clock.millis();
    }

}
