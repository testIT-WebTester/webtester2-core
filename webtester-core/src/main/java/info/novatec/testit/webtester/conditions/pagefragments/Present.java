package info.novatec.testit.webtester.conditions.pagefragments;

import info.novatec.testit.webtester.conditions.Condition;
import info.novatec.testit.webtester.pagefragments.PageFragment;


/**
 * {@link Condition} to be used in order to check if a {@link PageFragment page fragment} is present.
 *
 * @see Condition
 * @see PageFragment#isPresent()
 * @since 2.0
 */
public class Present implements Condition<PageFragment> {

    @Override
    public boolean test(PageFragment pageFragment) {
        return pageFragment.isPresent();
    }

    @Override
    public String toString() {
        return "present";
    }

}
