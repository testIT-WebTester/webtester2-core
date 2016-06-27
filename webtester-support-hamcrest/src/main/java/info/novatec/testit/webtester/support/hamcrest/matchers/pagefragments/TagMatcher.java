package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers;


/**
 * This {@link TypeSafeMatcher} checks whether or not a {@link PageFragment} has a certain tag.
 * Instances of this matcher should be initialized using the {@link WebTesterMatchers} factory class.
 * <p>
 * <b>Example:</b> assertThat(textField, has(tag("input")));
 * <p>
 *
 * @param <T> the type of the checked fragment
 * @see WebTesterMatchers
 * @since 2.0
 */
public class TagMatcher<T extends PageFragment> extends TypeSafeMatcher<T> {

    private final String expected;

    /** The actual tag for a possible mismatch description. */
    private String actual;

    /**
     * Creates a new instance for the given tag.
     *
     * @param expected the tag to check
     * @see TagMatcher
     * @since 2.0
     */
    public TagMatcher(String expected) {
        this.expected = expected;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("tag <" + expected + ">");
    }

    @Override
    protected boolean matchesSafely(T item) {
        actual = item.getTagName();
        return expected.equals(actual);
    }

    @Override
    protected void describeMismatchSafely(T item, Description mismatchDescription) {
        mismatchDescription.appendText("was <" + actual + ">");
    }

}
