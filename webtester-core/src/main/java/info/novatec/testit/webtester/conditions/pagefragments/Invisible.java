package info.novatec.testit.webtester.conditions.pagefragments;

import java.util.function.Predicate;

import info.novatec.testit.webtester.pagefragments.PageFragment;


/**
 * {@link Predicate} to be used in order to check if a {@link PageFragment page fragment} is invisible.
 *
 * @see PageFragment#isVisible()
 * @since 2.0
 */
public class Invisible implements Predicate<PageFragment> {

    private Visible visible = new Visible();

    @Override
    public boolean test(PageFragment pageFragment) {
        return !visible.test(pageFragment);
    }

    @Override
    public String toString() {
        return "invisible";
    }

}
