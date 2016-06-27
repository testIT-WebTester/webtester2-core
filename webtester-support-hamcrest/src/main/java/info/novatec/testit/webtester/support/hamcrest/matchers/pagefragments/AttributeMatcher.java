package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import info.novatec.testit.webtester.conditions.Conditions;
import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers;


/**
 * This {@link TypeSafeMatcher} checks whether or not a specific attribute is present on a {@link PageFragment}.
 * Instances of this matcher should be initialized using the {@link WebTesterMatchers} factory class.
 * <p>
 * <b>Example:</b> assertThat(fragment, has(attribute("type")));
 * <p>
 *
 * @param <T> the type of the checked page fragment
 * @see WebTesterMatchers
 * @since 2.0
 */
public class AttributeMatcher<T extends PageFragment> extends TypeSafeMatcher<T> {

    private final String expected;

    /**
     * Creates a new instance for the given attribute name.
     *
     * @param expected the name of the attribute to check
     * @see AttributeMatcher
     * @since 2.0
     */
    public AttributeMatcher(String expected) {
        this.expected = expected;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("attribute <" + expected + ">");
    }

    @Override
    protected boolean matchesSafely(T item) {
        return Conditions.attribute(expected).test(item);
    }

    @Override
    protected void describeMismatchSafely(T item, Description mismatchDescription) {
        mismatchDescription.appendText("was not present");
    }

}
