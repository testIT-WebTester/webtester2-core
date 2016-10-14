package info.novatec.testit.webtester.conditions.pagefragments;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

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
@AllArgsConstructor
public class Attribute implements Condition<PageFragment> {

    @NonNull
    private final String expectedAttributeName;

    @Override
    public boolean test(PageFragment pageFragment) {
        return pageFragment.getAttribute(expectedAttributeName).isPresent();
    }

    @Override
    public String toString() {
        return String.format("attribute '%s'", expectedAttributeName);
    }

}
