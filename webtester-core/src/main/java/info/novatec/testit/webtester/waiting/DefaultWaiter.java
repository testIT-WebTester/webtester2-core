package info.novatec.testit.webtester.waiting;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.time.Clock;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import static java.util.Optional.empty;
import static java.util.Optional.of;


/**
 * Default implementation of {@link Waiter} using a {@link Sleeper} and a {@link Clock} to wait and keep time.
 *
 * @see Waiter
 * @see Sleeper
 * @see Clock
 * @since 2.0
 */
@Slf4j
@AllArgsConstructor
@Getter(AccessLevel.PROTECTED)
class DefaultWaiter implements Waiter {

    /**
     * The {@link Sleeper} to use when actually needing to pass time.
     */
    @NonNull
    private final Sleeper sleeper;
    /**
     * The {@link Clock} to use for tracking the passing of time.
     */
    @NonNull
    private final Clock clock;

    /**
     * Creates a new default {@link DefaultWaiter}. It has a {@link CurrentThreadSleeper} as a sleeper and a
     * {@link Clock#systemDefaultZone()} clock.
     *
     * @see Sleeper
     * @see Clock
     * @since 2.0
     */
    DefaultWaiter() {
        this(new CurrentThreadSleeper());
    }

    /**
     * Creates a new custom {@link DefaultWaiter}. It will use the given {@link Sleeper} and a
     * {@link Clock#systemDefaultZone()} clock.
     *
     * @param sleeper the sleeper to use
     * @see Sleeper
     * @see Clock
     * @since 2.0
     */
    DefaultWaiter(Sleeper sleeper) {
        this(sleeper, Clock.systemDefaultZone());
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
    public void waitUntil(WaitConfig config, Supplier<Boolean> condition) {
        waitUntilWithAction(config, condition, WaitingAction.doNothing());
    }

    @Override
    @SuppressWarnings({"PMD.AvoidCatchingGenericException", "PMD.AvoidRethrowingException"})
    public void waitUntilWithAction(WaitConfig config, Supplier<Boolean> condition, WaitingAction waitingAction) {
        long effectiveTimeout = config.getTimeoutInMillis();
        long start = now();

        boolean conditionMet = false;
        Optional<RuntimeException> lastException = empty();

        do {
            try {
                conditionMet = checkCondition(condition, waitingAction);
                runWaitAction(conditionMet, waitingAction);
            } catch (ConditionParameterMismatchException e) {
                throw e;
            } catch (RuntimeException e) {
                lastException = lastException.isPresent() ?
                        lastException.map(ex -> handleLastException(ex, e)) : of(e);
            }
            log.trace("condition '{}' met: {}", condition, conditionMet);
            if (!conditionMet) {
                waitExactly(config.getInterval(), TimeUnit.MILLISECONDS);
            }
        } while (!conditionMet && timeSince(start) < effectiveTimeout);

        if (!conditionMet) {
            String message = "condition not met within the given timeout";
            log.debug("condition not met: {}", condition);
            throw lastException
                    .map(lastEx -> new TimeoutException(message, lastEx))
                    .orElse(new TimeoutException(message));
        } else {
            log.debug("condition met: {}", condition);
        }
    }

    private Boolean checkCondition(Supplier<Boolean> condition, WaitingAction waitingAction) {
        try {
            return condition.get();
        } catch (RuntimeException e) {
            runWaitAction(false, waitingAction);
            throw e;
        }
    }

    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    private void runWaitAction(boolean conditionMet, WaitingAction waitingAction) throws WaitActionFailedException {
        if (!conditionMet && waitingAction.getActionCondition().get()) {
            log.trace("condition for WaitAction met by '{}'", waitingAction.getActionCondition());
            try {
                waitingAction.getAction().perform();
                log.debug("wait action performed: {}", waitingAction.getAction());
            } catch (Exception e) {
                throw new WaitActionFailedException(waitingAction.getAction().getClass(), e);
            }
        }
    }

    private RuntimeException handleLastException(RuntimeException lastEx, RuntimeException newEx) {
        if (newEx instanceof WaitActionFailedException && !(lastEx instanceof WaitActionFailedException)) {
            lastEx.addSuppressed(newEx);
        }
        return lastEx;
    }

    private long timeSince(long start) {
        return now() - start;
    }

    private long now() {
        return clock.millis();
    }
}
