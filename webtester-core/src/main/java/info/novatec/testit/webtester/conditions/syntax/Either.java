package info.novatec.testit.webtester.conditions.syntax;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

import org.apache.commons.lang.StringUtils;


/**
 * {@link Predicate} which returns the result of any number of OR evaluated predicates.
 * The first positively evaluated predicate will lead to <code>true</code> being
 * returned. This is intended to be used to enhance code readability.
 * <p>
 * <b>Example:</b>
 * <code>Waits.waitUntil(textfield, has(either(text("foo"), text("bar"))));</code>
 *
 * @param <T> type of the object to test against the predicates
 * @since 2.0
 */
public class Either<T> implements Predicate<T> {

    private final List<Predicate<T>> predicates = new LinkedList<>();

    public Either(Predicate<T> predicate) {
        this.predicates.add(predicate);
    }

    public Either(Predicate<T> predicate1, Predicate<T> predicate2) {
        this.predicates.add(predicate1);
        this.predicates.add(predicate2);
    }

    @SafeVarargs
    public Either(Predicate<T>... predicates) {
        this.predicates.addAll(Arrays.asList(predicates));
    }

    @Override
    public boolean test(T pageObject) {
        return predicates.stream().filter(predicate -> predicate.test(pageObject)).findFirst().isPresent();
    }

    @Override
    public String toString() {
        return "either(" + StringUtils.join(predicates, ", ") + ')';
    }

}
