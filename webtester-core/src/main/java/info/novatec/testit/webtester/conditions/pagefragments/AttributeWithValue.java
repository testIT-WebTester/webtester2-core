package info.novatec.testit.webtester.conditions.pagefragments;

import java.util.Objects;

import info.novatec.testit.webtester.conditions.Condition;
import info.novatec.testit.webtester.pagefragments.PageFragment;


/**
 * {@link Condition} to be used in order to check if a specific attribute of a {@link PageFragment page fragment} has a
 * certain value.
 *
 * @see Condition
 * @see PageFragment#getAttribute(String)
 * @since 2.0
 */
public class AttributeWithValue implements Condition<PageFragment> {

    private final String attributeName;
    private final String expectedValue;

    /**
     * Creates a new {@link AttributeWithValue} condition. Using the given attribute name and expected value.
     * The value is an Object and it's toString method will be used to extract the value for the comparison.
     *
     * @param attributeName the name of the attribute to check
     * @param expectedValue the value to check
     */
    public AttributeWithValue(String attributeName, Object expectedValue) {
        this.attributeName = attributeName;
        this.expectedValue = String.valueOf(expectedValue);
    }

    @Override
    public boolean test(PageFragment pageFragment) {
        return pageFragment.getAttribute(attributeName).filter(this::isExpectedValue).isPresent();
    }

    private boolean isExpectedValue(String value) {
        return Objects.equals(expectedValue, value);
    }

    @Override
    public String toString() {
        return String.format("attribute '%s' with value '%s'", attributeName, expectedValue);
    }

}
