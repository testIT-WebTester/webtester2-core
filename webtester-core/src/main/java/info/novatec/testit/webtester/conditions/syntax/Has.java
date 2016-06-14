package info.novatec.testit.webtester.conditions.syntax;

import info.novatec.testit.webtester.conditions.Condition;


/**
 * {@link Condition} which returns the result of another condition. This is intended to
 * be used to enhance code readability.
 * <p>
 * <b>Example:</b> <code>Waits.waitUntil(textfield, has(text("foo")));</code> is
 * more readable then <code>Waits.waitUntil(textfield, text("foo"));</code>
 *
 * @param <T> type of the object to test against the condition
 * @see Condition
 * @since 2.0
 */
public class Has<T> implements Condition<T> {

    private final Condition<T> condition;

    public Has(Condition<T> condition) {
        this.condition = condition;
    }

    @Override
    public boolean test(T value) {
        return condition.test(value);
    }

    @Override
    public String toString() {
        return "has(" + condition + ')';
    }

}
