package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import info.novatec.testit.webtester.conditions.Conditions;
import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers;


/**
 * This {@link TypeSafeMatcher} checks weather or not a {@link PageFragment} has a certain visible text.
 * Instances of this matcher should be initialized using the {@link WebTesterMatchers} factory class.
 * <p>
 * <b>Example:</b> assertThat(fragment, has(visibleText("foo bar")));
 * <p>
 *
 * @param <T> the type of the checked fragment
 * @see WebTesterMatchers
 * @since 2.0
 */
public class VisibleTextMatcher<T extends PageFragment> extends TypeSafeMatcher<T> {

    /** The expected visible text. */
    private final String text;

    /** The actual visible text for a possible mismatch description. */
    private String actualText;

    /**
     * Creates a new instance for the given text.
     *
     * @param text the text to check
     * @see VisibleTextMatcher
     * @since 2.0
     */
    public VisibleTextMatcher(String text) {
        this.text = text;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("visible text <" + text + ">");
    }

    @Override
    protected boolean matchesSafely(T item) {
        actualText = item.getVisibleText();
        return Conditions.visibleText(text).test(item);
    }

    @Override
    protected void describeMismatchSafely(T item, Description mismatchDescription) {
        mismatchDescription.appendText("was <" + actualText + ">");
    }

}
