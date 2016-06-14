package info.novatec.testit.webtester.conditions.pagefragments;

import info.novatec.testit.webtester.conditions.Condition;
import info.novatec.testit.webtester.pagefragments.PageFragment;


/**
 * {@link Condition} to be used in order to check if a {@link PageFragment page fragment} is visible.
 *
 * @see Condition
 * @see PageFragment#isVisible()
 * @since 2.0
 */
public class Visible implements Condition<PageFragment> {

    @Override
    public boolean test(PageFragment pageFragment) {
        return pageFragment.isVisible();
    }

    @Override
    public String toString() {
        return "visible";
    }

}
