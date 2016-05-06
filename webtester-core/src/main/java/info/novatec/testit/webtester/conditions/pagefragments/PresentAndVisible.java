package info.novatec.testit.webtester.conditions.pagefragments;

import java.util.function.Predicate;

import info.novatec.testit.webtester.pagefragments.PageFragment;


/**
 * {@link Predicate} to be used in order to check if a {@link PageFragment page fragment} is present and visible.
 *
 * @see Present
 * @see Visible
 * @since 2.0
 */
public class PresentAndVisible implements Predicate<PageFragment> {

    private final Visible visible = new Visible();
    private final Present present = new Present();

    @Override
    public boolean test(PageFragment pageFragment) {
        return isPresent(pageFragment) && isVisible(pageFragment);
    }

    private boolean isPresent(PageFragment pageFragment) {
        return present.test(pageFragment);
    }

    private boolean isVisible(PageFragment pageFragment) {
        return visible.test(pageFragment);
    }

    @Override
    public String toString() {
        return "present and visible";
    }

}
