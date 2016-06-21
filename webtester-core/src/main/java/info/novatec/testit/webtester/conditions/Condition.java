package info.novatec.testit.webtester.conditions;

import java.util.function.Predicate;


/**
 * A condition is a special form of {@link Predicate}.
 * <p>
 * It is used in WebTester's API  in order to allow for the extension with additional features in the future.
 *
 * @param <T> the type of the input to the predicate
 * @see Predicate
 * @see Conditions
 * @since 2.0
 */
public interface Condition<T> extends Predicate<T> {

    @Override
    default Condition<T> negate() {
        return (t) -> !test(t);
    }

    @Override
    default Condition<T> and(Predicate<? super T> other) {
        return (t) -> test(t) && other.test(t);
    }

    @Override
    default Condition<T> or(Predicate<? super T> other) {
        return (t) -> test(t) || other.test(t);
    }

}
