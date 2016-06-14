package info.novatec.testit.webtester.conditions.syntax;

import info.novatec.testit.webtester.conditions.Condition;


/**
 * {@link Condition} which returns the <b>negated</b> result of another condition. This
 * is intended to be used to check for a condition to <u>not</u> be met.
 * <p>
 * <b>Example:</b> <code>Waits.waitUntil(checkbox, is(not(selected())));</code>
 *
 * @param <T> type of the object to test against the condition
 * @see Condition
 * @since 2.0
 */
public class Not<T> implements Condition<T> {

    private final Condition<T> condition;

    public Not(Condition<T> condition) {
        this.condition = condition;
    }

    @Override
    public boolean test(T value) {
        return !condition.test(value);
    }

    @Override
    public String toString() {
        return "not(" + condition + ')';
    }

}
