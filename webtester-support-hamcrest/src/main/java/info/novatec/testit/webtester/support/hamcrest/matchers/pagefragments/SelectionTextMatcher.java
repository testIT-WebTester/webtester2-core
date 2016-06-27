package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import java.util.Optional;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import info.novatec.testit.webtester.pagefragments.SingleSelect;
import info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers;


/**
 * This {@link TypeSafeMatcher} checks whether or not a {@link SingleSelect} has a selection with a given text.
 * Instances of this matcher should be initialized using the {@link WebTesterMatchers} factory class.
 * <p>
 * <b>Example:</b> assertThat(select, has(selectionWithText("foo")));
 * <p>
 *
 * @param <T> the type of the checked select
 * @see WebTesterMatchers
 * @since 2.0
 */
public class SelectionTextMatcher<T extends SingleSelect> extends TypeSafeMatcher<T> {

    private final String expected;

    /** The actual text for a possible mismatch description. */
    private Optional<String> actual;

    /**
     * Creates a new instance for the given text.
     *
     * @param expected the expected text
     * @see SelectionTextMatcher
     * @since 2.0
     */
    public SelectionTextMatcher(String expected) {
        this.expected = expected;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("selection with text <" + expected + ">");
    }

    @Override
    protected boolean matchesSafely(T item) {
        actual = item.getSelectionText();
        return actual.filter(expected::equals).isPresent();
    }

    @Override
    protected void describeMismatchSafely(T item, Description mismatchDescription) {
        mismatchDescription.appendText("was <" + actual + ">");
    }

}
