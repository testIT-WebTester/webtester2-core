package info.novatec.testit.webtester.conditions.syntax;

import java.util.function.Predicate;


/**
 * {@link Predicate} which returns the <b>negated</b> result of another predicate. This
 * is intended to be used to check for a condition to <u>not</u> be met.
 * <p>
 * <b>Example:</b> <code>Waits.waitUntil(checkbox, is(not(selected())));</code>
 *
 * @param <T> type of the object to test against the predicate
 * @since 2.0
 */
public class Not<T> implements Predicate<T> {

    private final Predicate<T> predicate;

    public Not(Predicate<T> predicate) {
        this.predicate = predicate;
    }

    @Override
    public boolean test(T pageObject) {
        return !predicate.test(pageObject);
    }

    @Override
    public String toString() {
        return "not(" + predicate + ')';
    }

}
