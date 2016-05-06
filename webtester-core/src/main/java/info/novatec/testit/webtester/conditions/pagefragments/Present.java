package info.novatec.testit.webtester.conditions.pagefragments;

import java.util.function.Predicate;

import info.novatec.testit.webtester.pagefragments.PageFragment;


/**
 * {@link Predicate} to be used in order to check if a {@link PageFragment page fragment} is present.
 *
 * @see PageFragment#isPresent()
 * @since 2.0
 */
public class Present implements Predicate<PageFragment> {

    @Override
    public boolean test(PageFragment pageFragment) {
        return pageFragment.isPresent();
    }

    @Override
    public String toString() {
        return "present";
    }

}
