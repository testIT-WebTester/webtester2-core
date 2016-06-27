package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import java.util.LinkedList;
import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import info.novatec.testit.webtester.pagefragments.MultiSelect;
import info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers;


/**
 * This {@link TypeSafeMatcher} checks weather or not a {@link MultiSelect} has selected options with a given list of
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

    /** The expected list of indices. */
    private final List<Integer> indices;

    /** The actual list of indices for a possible mismatch description. */
    private List<Integer> actualIndices;

    /**
     * Creates a new instance for the give list of indices.
     *
     * @param indices the expected indices
     * @see SelectionIndicesMatcher
     * @since 2.0
     */
    public SelectionIndicesMatcher(List<Integer> indices) {
        this.indices = new LinkedList<>(indices);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("selection with indices <" + indices + "> in order");
    }

    @Override
    protected boolean matchesSafely(T item) {
        actualIndices = item.getSelectionIndices();
        return indices.equals(actualIndices);
    }

    @Override
    protected void describeMismatchSafely(T item, Description mismatchDescription) {
        mismatchDescription.appendText("was <" + actualIndices + ">");
    }

}
