package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import java.util.Optional;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import info.novatec.testit.webtester.pagefragments.SingleSelect;
import info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers;


/**
 * This {@link TypeSafeMatcher} checks whether or not a {@link SingleSelect} has a selection with a given index.
 * Instances of this matcher should be initialized using the {@link WebTesterMatchers} factory class.
 * <p>
 * <b>Example:</b> assertThat(select, has(selectionWithIndex(2)));
 * <p>
 *
 * @param <T> the type of the checked select
 * @see WebTesterMatchers
 * @since 2.0
 */
public class SelectionIndexMatcher<T extends SingleSelect> extends TypeSafeMatcher<T> {

    private final Integer index;

    /** The actual index for a possible mismatch description. */
    private Integer actualIndex;

    /**
     * Creates a new instance for the given index.
     *
     * @param index the expected index
     * @see SelectionIndexMatcher
     * @since 2.0
     */
    public SelectionIndexMatcher(Integer index) {
        this.index = index;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("selection with index <" + index + ">");
    }

    @Override
    protected boolean matchesSafely(T item) {
        Optional<Integer> selectionIndex = item.getSelectionIndex();
        actualIndex = selectionIndex.orElse(null);
        return selectionIndex.filter(index::equals).isPresent();
    }

    @Override
    protected void describeMismatchSafely(T item, Description mismatchDescription) {
        mismatchDescription.appendText("was <" + actualIndex + ">");
    }

}
