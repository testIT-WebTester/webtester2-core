package info.novatec.testit.webtester.waiting;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.function.Supplier;

/**
 * This class is used to store configuration parameters for a wait action. This includes a condition for the action
 * as well as the action to be executed.
 *
 * @see Waiter
 * @since 2.8
 */
@AllArgsConstructor
@Getter(AccessLevel.PROTECTED)
class WaitingAction {

    /**
     * The condition provided via {@link Supplier} to check if the {@link WaitAction} should be executed.
     */
    @NonNull
    private final Supplier<Boolean> actionCondition;

    /**
     * The action to be executed, given the actionCondition is true.
     */
    private final WaitAction action;

}
