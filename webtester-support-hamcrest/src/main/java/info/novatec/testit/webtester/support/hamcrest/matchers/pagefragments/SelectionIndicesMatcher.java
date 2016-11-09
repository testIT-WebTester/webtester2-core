package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import info.novatec.testit.webtester.pagefragments.MultiSelect;
import info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers;


/**
 * This {@link TypeSafeMatcher} checks whether or not a {@link MultiSelect} has selected options with a given list of
 * indices. The indices are validated in order, and all expected indices need to be specified!
 * Instances of this matcher should be initialized using the {@link WebTesterMatchers} factory class.
 * <p>
 * <b>Example:</b> assertThat(select, has(selectionWithIndices(1, 2)));
 * <p>
 *
 * @param <T> the type of the checked select
 * @see WebTesterMatchers
 * @since 2.0
 */
public class SelectionIndicesMatcher<T extends MultiSelect> extends TypeSafeMatcher<T> {

    private final List<Integer> expected;

    /** The actual list of indices for a possible mismatch description. */
    private List<Integer> actual;

    /**
     * Creates a new instance for the give list of indices.
     *
     * @param expected the expected indices
     * @see SelectionIndicesMatcher
     * @since 2.0
     */
    public SelectionIndicesMatcher(List<Integer> expected) {
        this.expected = new ArrayList<>(expected);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("selection with indices <" + expected + "> in order");
    }

    @Override
    protected boolean matchesSafely(T item) {
        actual = item.getSelectionIndices();
        return expected.equals(actual);
    }

    @Override
    protected void describeMismatchSafely(T item, Description mismatchDescription) {
        mismatchDescription.appendText("was <" + actual + ">");
    }

}
