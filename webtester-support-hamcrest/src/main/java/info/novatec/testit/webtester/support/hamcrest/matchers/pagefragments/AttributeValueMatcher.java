package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers;


/**
 * This {@link TypeSafeMatcher} checks whether or not a specific attribute has a given value on a {@link PageFragment}.
 * Instances of this matcher should be initialized using the {@link WebTesterMatchers} factory class.
 * <p>
 * <b>Example:</b> assertThat(fragment, has(attributeValue("type", "radio")));
 * <p>
 * <b>Note:</b> This matcher has two possible failure scenarios:
 * <ol>
 * <li>The attribute does not exist.</li>
 * <li>The attribute has a different value.</li>
 * </ol>
 *
 * @param <T> the type of the checked page fragment
 * @see WebTesterMatchers
 * @since 2.0
 */
public class AttributeValueMatcher<T extends PageFragment> extends TypeSafeMatcher<T> {

    private final String expectedAttributeName;
    private final String expectedValue;

    /** Boolean to remember if the attribute was present for a possible mismatch description. */
    private boolean attributePresent;
    /** The actual value for a possible mismatch description. */
    private String actualValue;

    /**
     * Creates a new instance for the given attribute name and value.
     *
     * @param expectedAttributeName the name of the attribute to check
     * @param expectedValue the expected value of the attribute
     * @see AttributeValueMatcher
     * @since 2.0
     */
    public AttributeValueMatcher(String expectedAttributeName, String expectedValue) {
        this.expectedAttributeName = expectedAttributeName;
        this.expectedValue = expectedValue;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("attribute <" + expectedAttributeName + "> with value <" + expectedValue + ">");
    }

    @Override
    protected boolean matchesSafely(T item) {
        item.getAttribute(expectedAttributeName).ifPresent(value -> {
            attributePresent = true;
            actualValue = value;
        });
        return attributePresent && expectedValue.equals(actualValue);
    }

    @Override
    protected void describeMismatchSafely(T item, Description mismatchDescription) {
        if (!attributePresent) {
            mismatchDescription.appendText("attribute was not present");
        } else {
            mismatchDescription.appendText("value was <" + actualValue + ">");
        }
    }

}
