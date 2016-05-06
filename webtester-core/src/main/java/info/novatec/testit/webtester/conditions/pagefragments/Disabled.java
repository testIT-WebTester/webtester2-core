package info.novatec.testit.webtester.conditions.pagefragments;

import java.util.function.Predicate;

import info.novatec.testit.webtester.pagefragments.PageFragment;


/**
 * {@link Predicate} to be used in order to check if a {@link PageFragment page fragment} is disabled.
 *
 * @see PageFragment#isDisabled()
 * @since 2.0
 */
public class Disabled implements Predicate<PageFragment> {

    @Override
    public boolean test(PageFragment canBeDisabled) {
        return canBeDisabled.isDisabled();
    }

    @Override
    public String toString() {
        return "disabled";
    }

}
