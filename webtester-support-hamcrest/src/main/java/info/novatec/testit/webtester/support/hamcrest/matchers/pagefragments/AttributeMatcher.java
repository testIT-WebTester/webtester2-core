package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import info.novatec.testit.webtester.conditions.Conditions;
import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers;


/**
 * This {@link TypeSafeMatcher} checks weather or not a specific attribute is present on a {@link PageFragment}.
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

    /** The name of the expected attribute. */
    private final String attributeName;

    /**
     * Creates a new instance for the given attribute name.
     *
     * @param attributeName the name of the attribute to check
     * @see AttributeMatcher
     * @since 2.0
     */
    public AttributeMatcher(String attributeName) {
        this.attributeName = attributeName;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("attribute <" + attributeName + ">");
    }

    @Override
    protected boolean matchesSafely(T item) {
        return Conditions.attribute(attributeName).test(item);
    }

    @Override
    protected void describeMismatchSafely(T item, Description mismatchDescription) {
        mismatchDescription.appendText("was not present");
    }

}
