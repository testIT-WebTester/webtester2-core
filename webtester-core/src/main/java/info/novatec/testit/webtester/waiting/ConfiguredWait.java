package info.novatec.testit.webtester.waiting;

import java.util.function.Supplier;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;


/**
 * This class is used to configure {@link Wait} operations with custom settings like a {@code timeout}.
 *
 * @see Wait
 * @see WaitUntil
 * @see Waiter
 * @see WaitConfig
 * @since 2.0
 */
@Getter(AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ConfiguredWait {

    /** The {@link Waiter} to use when executing wait operations. */
    @NonNull
    private final Waiter waiter;
    /** The {@link WaitConfig} to use when deciding how long to wait. */
    @NonNull
    private final WaitConfig config;

    /**
     * Creates a {@link WaitUntil} with this {@link ConfiguredWait}'s {@link ConfiguredWait} for the given object.
     *
     * @param object the object for the wait until operation
     * @param <T> the type of object
     * @return the fluent wait instance
     * @see Wait
     * @see WaitUntil
     * @see Waiter
     * @see WaitConfig
     * @since 2.0
     */
    public <T> WaitUntil<T> until(T object) {
        return new WaitUntil<>(waiter, config, object);
    }

    /**
     * Executes a wait operation based on this {@link ConfiguredWait}'s {@link ConfiguredWait} and the given boolean
     * supplier. The wait is executed until either the supplier returns <code>true</code> or the timeout is reached.
     *
     * @param condition the supplier for the wait until operation
     * @see Wait
     * @see WaitUntil
     * @see Waiter
     * @see WaitConfig
     * @since 2.0
     */
    public void until(Supplier<Boolean> condition) {
        waiter.waitUntil(config, condition);
    }

}
