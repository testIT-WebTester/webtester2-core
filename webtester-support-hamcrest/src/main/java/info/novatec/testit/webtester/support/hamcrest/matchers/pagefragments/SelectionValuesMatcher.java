package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import info.novatec.testit.webtester.pagefragments.MultiSelect;
import info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers;


/**
 * This {@link TypeSafeMatcher} checks whether or not a {@link MultiSelect} has selected options with a given list of
 * values. The values are validated in order, and all expected values need to be specified!
 * Instances of this matcher should be initialized using the {@link WebTesterMatchers} factory class.
 * <p>
 * <b>Example:</b> assertThat(select, has(selectionWithValues("foo", "bar")));
 * <p>
 *
 * @param <T> the type of the checked select
 * @see WebTesterMatchers
 * @since 2.0
 */
public class SelectionValuesMatcher<T extends MultiSelect> extends TypeSafeMatcher<T> {

    private final List<String> expected;

    /** The actual list of values for a possible mismatch description. */
    private List<String> actual;

    /**
     * Creates a new instance for the give list of values.
     *
     * @param expected the expected values
     * @see SelectionValuesMatcher
     * @since 2.0
     */
    public SelectionValuesMatcher(List<String> expected) {
        this.expected = new ArrayList<>(expected);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("selection with values <" + expected + "> in order");
    }

    @Override
    protected boolean matchesSafely(T item) {
        actual = item.getSelectionValues();
        return expected.equals(actual);
    }

    @Override
    protected void describeMismatchSafely(T item, Description mismatchDescription) {
        mismatchDescription.appendText("was <" + actual + ">");
    }

}
