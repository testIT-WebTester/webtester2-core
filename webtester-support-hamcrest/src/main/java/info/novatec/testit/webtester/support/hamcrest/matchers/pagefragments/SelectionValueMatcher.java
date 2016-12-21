package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import java.util.Optional;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import info.novatec.testit.webtester.pagefragments.SingleSelect;
import info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers;


/**
 * This {@link TypeSafeMatcher} checks whether or not a {@link SingleSelect} has a selection with a given value.
 * Instances of this matcher should be initialized using the {@link WebTesterMatchers} factory class.
 * <p>
 * <b>Example:</b> assertThat(select, has(selectionWithValue("foo")));
 * <p>
 *
 * @param <T> the type of the checked select
 * @see WebTesterMatchers
 * @since 2.0
 */
public class SelectionValueMatcher<T extends SingleSelect> extends TypeSafeMatcher<T> {

    private final String expected;

    /** The actual value for a possible mismatch description. */
    private String actual;

    /**
     * Creates a new instance for the given value.
     *
     * @param expected the expected value
     * @see SelectionValueMatcher
     * @since 2.0
     */
    public SelectionValueMatcher(String expected) {
        this.expected = expected;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("selection with value <" + expected + ">");
    }

    @Override
    protected boolean matchesSafely(T item) {
        Optional<String> selectionValue = item.getSelectionValue();
        actual = selectionValue.orElse(null);
        return selectionValue.filter(expected::equals).isPresent();
    }

    @Override
    protected void describeMismatchSafely(T item, Description mismatchDescription) {
        mismatchDescription.appendText("was <" + actual + ">");
    }

}
