package info.novatec.testit.webtester.conditions.syntax;

import java.util.function.Predicate;


/**
 * {@link Predicate} which returns the result of another predicate. This is intended to
 * be used to enhance code readability.
 * <p>
 * <b>Example:</b> <code>Waits.waitUntil(checkbox, is(selected()));</code> is
 * more readable then <code>Waits.waitUntil(checkbox, selected());</code>
 *
 * @param <T> type of the object to test against the predicate
 * @since 2.0
 */
public class Is<T> implements Predicate<T> {

    private final Predicate<T> predicate;

    public Is(Predicate<T> predicate) {
        this.predicate = predicate;
    }

    @Override
    public boolean test(T pageObject) {
        return predicate.test(pageObject);
    }

    @Override
    public String toString() {
        return "is(" + predicate + ')';
    }

}
