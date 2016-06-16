package info.novatec.testit.webtester.conditions.pagefragments;

import info.novatec.testit.webtester.conditions.Condition;
import info.novatec.testit.webtester.pagefragments.PageFragment;


/**
 * {@link Condition} to be used in order to check if a {@link PageFragment page fragment} is disabled.
 *
 * @see Condition
 * @see PageFragment#isDisabled()
 * @since 2.0
 */
public class Disabled implements Condition<PageFragment> {

    @Override
    public boolean test(PageFragment canBeDisabled) {
        return canBeDisabled.isDisabled();
    }

    @Override
    public String toString() {
        return "disabled";
    }

}
