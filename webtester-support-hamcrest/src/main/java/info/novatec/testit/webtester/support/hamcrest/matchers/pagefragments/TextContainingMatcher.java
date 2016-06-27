package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import info.novatec.testit.webtester.pagefragments.GenericTextField;
import info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers;


/**
 * This {@link TypeSafeMatcher} checks whether or not a {@link GenericTextField}'s text contains a certain substring.
 * Instances of this matcher should be initialized using the {@link WebTesterMatchers} factory class.
 * <p>
 * <b>Example:</b> assertThat(textField, has(textContaining("foo bar")));
 * <p>
 *
 * @param <T> the type of the checked field
 * @see WebTesterMatchers
 * @since 2.0
 */
public class TextContainingMatcher<T extends GenericTextField<?>> extends TypeSafeMatcher<T> {

    private final String expectedSubstring;

    /** The actual text for a possible mismatch description. */
    private String actual;

    /**
     * Creates a new instance for the given text part.
     *
     * @param expectedSubstring the text part to check
     * @see TextContainingMatcher
     * @since 2.0
     */
    public TextContainingMatcher(String expectedSubstring) {
        this.expectedSubstring = expectedSubstring;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("text containing <" + expectedSubstring + ">");
    }

    @Override
    protected boolean matchesSafely(T item) {
        actual = item.getText();
        return actual.contains(expectedSubstring);
    }

    @Override
    protected void describeMismatchSafely(T item, Description mismatchDescription) {
        mismatchDescription.appendText("<" + actual + "> does not contain <" + expectedSubstring + ">");
    }

}
