package info.novatec.testit.webtester.conditions.pagefragments;

import info.novatec.testit.webtester.conditions.Condition;
import info.novatec.testit.webtester.pagefragments.PageFragment;


/**
 * {@link Condition} to be used in order to check if a {@link PageFragment page fragment} is enabled.
 *
 * @see Condition
 * @see PageFragment#isEnabled()
 * @since 2.0
 */
public class Enabled implements Condition<PageFragment> {

    @Override
    public boolean test(PageFragment canBeDisabled) {
        return canBeDisabled.isEnabled();
    }

    @Override
    public String toString() {
        return "enabled";
    }

}
