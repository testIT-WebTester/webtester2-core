package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import info.novatec.testit.webtester.conditions.Conditions;
import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers;


/**
 * This {@link TypeSafeMatcher} checks weather or not a {@link PageFragment}'s visible text contains a certain substring.
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

    /** The expected text part. */
    private final String textPart;

    /** The actual text for a possible mismatch description. */
    private String actualText;

    /**
     * Creates a new instance for the given text part.
     *
     * @param textPart the text part to check
     * @see VisibleTextContainingMatcher
     * @since 2.0
     */
    public VisibleTextContainingMatcher(String textPart) {
        this.textPart = textPart;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("visible text containing <" + textPart + ">");
    }

    @Override
    protected boolean matchesSafely(T item) {
        actualText = item.getVisibleText();
        return Conditions.visibleTextContaining(textPart).test(item);
    }

    @Override
    protected void describeMismatchSafely(T item, Description mismatchDescription) {
        mismatchDescription.appendText("<" + actualText + "> does not contain <" + textPart + ">");
    }

}
