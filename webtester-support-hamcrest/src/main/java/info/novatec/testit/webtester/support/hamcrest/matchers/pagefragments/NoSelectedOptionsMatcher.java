package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import info.novatec.testit.webtester.pagefragments.MultiSelect;
import info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers;


/**
 * This {@link TypeSafeMatcher} checks whether or not a {@link MultiSelect} has no selected options.
 * Instances of this matcher should be initialized using the {@link WebTesterMatchers} factory class.
 * <p>
 * <b>Example:</b> assertThat(select, has(noSelectedOptions()));
 * <p>
 *
 * @param <T> the type of the checked select
 * @see WebTesterMatchers
 * @since 2.0
 */
public class NoSelectedOptionsMatcher<T extends MultiSelect> extends TypeSafeMatcher<T> {

    /** The actual number of selected options for a possible mismatch description. */
    private int actual;

    @Override
    public void describeTo(Description description) {
        description.appendText("no selected options");
    }

    @Override
    protected boolean matchesSafely(T item) {
        actual = item.getSelectionCount();
        return actual == 0;
    }

    @Override
    protected void describeMismatchSafely(T item, Description mismatchDescription) {
        mismatchDescription.appendText("has <" + actual + "> selected options");
    }

}
