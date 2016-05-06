package info.novatec.testit.webtester.conditions.pagefragments;

import java.util.function.Predicate;

import info.novatec.testit.webtester.pagefragments.PageFragment;


/**
 * {@link Predicate} to be used in order to check if a {@link PageFragment page fragment} is interactable.
 * <p>
 * A page fragment is considered interactable if it is present, visible and enabled.
 *
 * @see Present
 * @see Visible
 * @see Enabled
 * @since 2.0
 */
public class Interactable implements Predicate<PageFragment> {

    private final Present present = new Present();
    private final Visible visible = new Visible();
    private final Enabled enabled = new Enabled();

    @Override
    public boolean test(PageFragment pageFragment) {
        return isPresent(pageFragment) && isVisible(pageFragment) && isEnabled(pageFragment);
    }

    private boolean isPresent(PageFragment pageFragment) {
        return present.test(pageFragment);
    }

    private boolean isEnabled(PageFragment pageFragment) {
        return enabled.test(pageFragment);
    }

    private boolean isVisible(PageFragment pageFragment) {
        return visible.test(pageFragment);
    }

    @Override
    public String toString() {
        return "interactable";
    }

}
