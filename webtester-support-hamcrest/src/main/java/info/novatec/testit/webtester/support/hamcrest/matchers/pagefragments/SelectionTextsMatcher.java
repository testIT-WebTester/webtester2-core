package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import java.util.LinkedList;
import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import info.novatec.testit.webtester.pagefragments.MultiSelect;
import info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers;


/**
 * This {@link TypeSafeMatcher} checks weather or not a {@link MultiSelect} has selected options with a given list of
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

    /** The expected list of texts. */
    private final List<String> texts;

    /** The actual list of texts for a possible mismatch description. */
    private List<String> actualTexts;

    /**
     * Creates a new instance for the give list of texts.
     *
     * @param texts the expected texts
     * @see SelectionIndicesMatcher
     * @since 2.0
     */
    public SelectionTextsMatcher(List<String> texts) {
        this.texts = new LinkedList<>(texts);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("selection with texts <" + texts + "> in order");
    }

    @Override
    protected boolean matchesSafely(T item) {
        actualTexts = item.getSelectionTexts();
        return texts.equals(actualTexts);
    }

    @Override
    protected void describeMismatchSafely(T item, Description mismatchDescription) {
        mismatchDescription.appendText("was <" + actualTexts + ">");
    }

}
