package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import info.novatec.testit.webtester.conditions.Conditions;
import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers;


/**
 * This {@link TypeSafeMatcher} checks whether or not a {@link PageFragment}'s visible text contains a certain substring.
 * Instances of this matcher should be initialized using the {@link WebTesterMatchers} factory class.
 * <p>
 * <b>Example:</b> assertThat(fragment, has(visibleTextContaining("foo bar")));
 * <p>
 *
 * @param <T> the type of the checked fragment
 * @see WebTesterMatchers
 * @since 2.0
 */
public class VisibleTextContainingMatcher<T extends PageFragment> extends TypeSafeMatcher<T> {

    private final String expectedSubstring;

    /** The actual text for a possible mismatch description. */
    private String actual;

    /**
     * Creates a new instance for the given text part.
     *
     * @param expectedSubstring the text part to check
     * @see VisibleTextContainingMatcher
     * @since 2.0
     */
    public VisibleTextContainingMatcher(String expectedSubstring) {
        this.expectedSubstring = expectedSubstring;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("visible text containing <" + expectedSubstring + ">");
    }

    @Override
    protected boolean matchesSafely(T item) {
        actual = item.getVisibleText();
        return Conditions.visibleTextContaining(expectedSubstring).test(item);
    }

    @Override
    protected void describeMismatchSafely(T item, Description mismatchDescription) {
        mismatchDescription.appendText("<" + actual + "> does not contain <" + expectedSubstring + ">");
    }

}
