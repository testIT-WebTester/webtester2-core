package info.novatec.testit.webtester.conditions.pagefragments;

import lombok.Getter;

import info.novatec.testit.webtester.conditions.Condition;
import info.novatec.testit.webtester.pagefragments.PageFragment;


/**
 * {@link Condition} to be used in order to check if a specific attribute of a {@link PageFragment page fragment} is present.
 *
 * @see Condition
 * @see PageFragment#getAttribute(String)
 * @since 2.0
 */
@Getter
public class Attribute implements Condition<PageFragment> {

    private final String expectedAttributeName;

    /**
     * Creates a new {@link Attribute} condition. Using the given attribute.
     *
     * @param expectedAttributeName the name of the attribute to check
     */
    public Attribute(String expectedAttributeName) {
        this.expectedAttributeName = expectedAttributeName;
    }

    @Override
    public boolean test(PageFragment pageFragment) {
        return pageFragment.getAttribute(expectedAttributeName).isPresent();
    }

    @Override
    public String toString() {
        return String.format("attribute '%s'", expectedAttributeName);
    }

}
