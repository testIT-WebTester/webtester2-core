package info.novatec.testit.webtester.conditions.pagefragments;

import java.util.function.Predicate;

import info.novatec.testit.webtester.pagefragments.PageFragment;


/**
 * {@link Predicate} to be used in order to check if a {@link PageFragment page fragment} is visible.
 *
 * @see PageFragment#isVisible()
 * @since 2.0
 */
public class Visible implements Predicate<PageFragment> {

    @Override
    public boolean test(PageFragment pageFragment) {
        return pageFragment.isVisible();
    }

    @Override
    public String toString() {
        return "visible";
    }

}
