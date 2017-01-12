package info.novatec.testit.webtester.conditions.values;

import lombok.AllArgsConstructor;
import lombok.NonNull;

import info.novatec.testit.webtester.conditions.Condition;


/**
 * {@link Condition} which returns whether or not the checked value is equal to the given expected value.
 * <p>
 * <b>Example:</b> <code>Wait.untilSupplied(textField::getText).is(equalTo("foo"));</code>
 *
 * @param <T> type of the object to test against the condition
 * @see Condition
 * @since 2.0
 */
@AllArgsConstructor
public class EqualTo<T> implements Condition<T> {

    @NonNull
    private final T expected;

    @Override
    public boolean test(T value) {
        return expected.equals(value);
    }

    @Override
    public String toString() {
        return "equalTo('" + expected + "')";
    }

}
