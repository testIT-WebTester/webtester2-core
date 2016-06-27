package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers;


/**
 * This {@link TypeSafeMatcher} checks weather or not a {@link PageFragment} has a certain tag.
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

    /** The expected tag. */
    private final String tag;

    /** The actual tag for a possible mismatch description. */
    private String actualTag;

    /**
     * Creates a new instance for the given tag.
     *
     * @param tag the tag to check
     * @see TagMatcher
     * @since 2.0
     */
    public TagMatcher(String tag) {
        this.tag = tag;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("tag <" + tag + ">");
    }

    @Override
    protected boolean matchesSafely(T item) {
        actualTag = item.getTagName();
        return tag.equals(actualTag);
    }

    @Override
    protected void describeMismatchSafely(T item, Description mismatchDescription) {
        mismatchDescription.appendText("was <" + actualTag + ">");
    }

}
