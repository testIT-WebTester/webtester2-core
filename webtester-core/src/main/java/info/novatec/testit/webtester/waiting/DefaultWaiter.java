package info.novatec.testit.webtester.waiting;

import java.time.Clock;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;


/**
 * Default implementation of {@link Waiter} using a {@link Sleeper} and a {@link Clock} to wait and keep time.
 *
 * @see Waiter
 * @see Sleeper
 * @see Clock
 * @since 2.0
 */
@Slf4j
@Getter(AccessLevel.PROTECTED)
public class DefaultWaiter implements Waiter {

    /**
     * The {@link Sleeper} to use when actually needing to pass time.
     */
    private final Sleeper sleeper;
    /**
     * The {@link Clock} to use for tracking the passing of time.
     */
    private final Clock clock;

    /**
     * Creates a new default {@link DefaultWaiter}. It has a {@link CurrentThreadSleeper} as a sleeper and a {@link
     * Clock#systemDefaultZone()} clock.
     *
     * @see Sleeper
     * @see Clock
     * @since 2.0
     */
    public DefaultWaiter() {
        this(new CurrentThreadSleeper());
    }

    /**
     * Creates a new custom {@link DefaultWaiter}. It will use the given {@link Sleeper} and a {@link
     * Clock#systemDefaultZone()}
     * clock.
     *
     * @see Sleeper
     * @see Clock
     * @since 2.0
     */
    public DefaultWaiter(Sleeper sleeper) {
        this(sleeper, Clock.systemDefaultZone());
    }

    /**
     * Creates a new custom {@link DefaultWaiter}. It will use the given {@link Sleeper} and a {@link Clock}.
     *
     * @see Sleeper
     * @see Clock
     * @since 2.0
     */
    public DefaultWaiter(Sleeper sleeper, Clock clock) {
        this.sleeper = sleeper;
        this.clock = clock;
    }

    @Override
    public void waitExactly(long duration, TimeUnit timeUnit) {
        try {
            long millisToSleep = timeUnit.toMillis(duration);
            if (millisToSleep > 0) {
                sleeper.sleep(millisToSleep);
            }
        } catch (InterruptionException e) {
            log.debug("wait was interrupted", e);
        }
    }

    @Override
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
