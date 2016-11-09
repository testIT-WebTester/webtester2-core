package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import info.novatec.testit.webtester.pagefragments.MultiSelect;
import info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers;


/**
 * This {@link TypeSafeMatcher} checks whether or not a {@link MultiSelect} has selected options with a given list of
 * texts. The texts are validated in order, and all expected texts need to be specified!
 * Instances of this matcher should be initialized using the {@link WebTesterMatchers} factory class.
 * <p>
 * <b>Example:</b> assertThat(select, has(selectionWithTexts("foo", "bar")));
 * <p>
 *
 * @param <T> the type of the checked select
 * @see WebTesterMatchers
 * @since 2.0
 */
public class SelectionTextsMatcher<T extends MultiSelect> extends TypeSafeMatcher<T> {

    private final List<String> expected;

    /** The actual list of texts for a possible mismatch description. */
    private List<String> actual;

    /**
     * Creates a new instance for the give list of texts.
     *
     * @param expected the expected texts
     * @see SelectionIndicesMatcher
     * @since 2.0
     */
    public SelectionTextsMatcher(List<String> expected) {
        this.expected = new ArrayList<>(expected);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("selection with texts <" + expected + "> in order");
    }

    @Override
    protected boolean matchesSafely(T item) {
        actual = item.getSelectionTexts();
        return expected.equals(actual);
    }

    @Override
    protected void describeMismatchSafely(T item, Description mismatchDescription) {
        mismatchDescription.appendText("was <" + actual + ">");
    }

}
