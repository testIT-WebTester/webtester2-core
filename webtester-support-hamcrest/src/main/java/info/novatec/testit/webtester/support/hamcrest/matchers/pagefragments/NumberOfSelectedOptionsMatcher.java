package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import info.novatec.testit.webtester.pagefragments.MultiSelect;
import info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers;


/**
 * This {@link TypeSafeMatcher} checks whether or not a {@link MultiSelect} has a specific number of selected options.
 * Instances of this matcher should be initialized using the {@link WebTesterMatchers} factory class.
 * <p>
 * <b>Example:</b> assertThat(select, has(selectedOptions(5)));
 * <p>
 *
 * @param <T> the type of the checked select
 * @see WebTesterMatchers
 * @since 2.0
 */
public class NumberOfSelectedOptionsMatcher<T extends MultiSelect> extends TypeSafeMatcher<T> {

    private final int expected;

    /** The actual number of selected options for a possible mismatch description. */
    private int actual;

    /**
     * Creates a new instance for the given number of selected options.
     *
     * @param expected the number of selected options
     * @see NumberOfSelectedOptionsMatcher
     * @since 2.0
     */
    public NumberOfSelectedOptionsMatcher(int expected) {
        this.expected = expected;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("<" + expected + "> selected options");
    }

    @Override
    protected boolean matchesSafely(T item) {
        actual = item.getSelectionCount();
        return actual == expected;
    }

    @Override
    protected void describeMismatchSafely(T item, Description mismatchDescription) {
        mismatchDescription.appendText("has <" + actual + "> selected options");
    }

}
