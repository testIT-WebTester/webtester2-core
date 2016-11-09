package info.novatec.testit.webtester.conditions.syntax;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import info.novatec.testit.webtester.conditions.Condition;


/**
 * {@link Condition} which returns the result of any number of OR evaluated conditions.
 * The first positively evaluated condition will lead to <code>true</code> being
 * returned. This is intended to be used to enhance code readability.
 * <p>
 * <b>Example:</b>
 * <code>Waits.waitUntil(textfield, has(either(text("foo"), text("bar"))));</code>
 *
 * @param <T> type of the object to test against the conditions
 * @since 2.0
 */
public class Either<T> implements Condition<T> {

    private final List<Condition<T>> conditions = new ArrayList<>();

    public Either(Condition<T> condition) {
        this.conditions.add(condition);
    }

    public Either(Condition<T> condition1, Condition<T> condition2) {
        this.conditions.add(condition1);
        this.conditions.add(condition2);
    }

    @SafeVarargs
    public Either(Condition<T>... conditions) {
        this.conditions.addAll(Arrays.asList(conditions));
    }

    @Override
    public boolean test(T value) {
        return conditions.stream().filter(condition -> condition.test(value)).findFirst().isPresent();
    }

    @Override
    public String toString() {
        return "either(" + StringUtils.join(conditions, ", ") + ')';
    }

}
