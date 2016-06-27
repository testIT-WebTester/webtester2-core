package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import info.novatec.testit.webtester.pagefragments.GenericTextField;
import info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers;


/**
 * This {@link TypeSafeMatcher} checks whether or not a {@link GenericTextField} has a certain text value.
 * Instances of this matcher should be initialized using the {@link WebTesterMatchers} factory class.
 * <p>
 * <b>Example:</b> assertThat(textField, has(text("foo bar")));
 * <p>
 *
 * @param <T> the type of the checked field
 * @see WebTesterMatchers
 * @since 2.0
 */
public class TextMatcher<T extends GenericTextField<?>> extends TypeSafeMatcher<T> {

    private final String expected;

    /** The actual text for a possible mismatch description. */
    private String actualText;

    /**
     * Creates a new instance for the given text.
     *
     * @param expected the text to check
     * @see TextMatcher
     * @since 2.0
     */
    public TextMatcher(String expected) {
        this.expected = expected;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("text <" + expected + ">");
    }

    @Override
    protected boolean matchesSafely(T item) {
        actualText = item.getText();
        return expected.equals(actualText);
    }

    @Override
    protected void describeMismatchSafely(T item, Description mismatchDescription) {
        mismatchDescription.appendText("was <" + actualText + ">");
    }

}
