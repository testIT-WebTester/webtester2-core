package info.novatec.testit.webtester.conditions.pagefragments;

import java.util.function.Predicate;

import info.novatec.testit.webtester.pagefragments.PageFragment;


/**
 * {@link Predicate} to be used in order to check if a {@link PageFragment page fragment} is 'editable'.
 * <p>
 * A page fragment is considered editable if it is present, visible, enabled and NOT read-only.
 *
 * @see ReadOnly
 * @see Enabled
 * @see Visible
 * @since 2.0
 */
public class Editable implements Predicate<PageFragment> {

    private final Present present = new Present();
    private final Visible visible = new Visible();
    private final Enabled enabled = new Enabled();
    private final ReadOnly readOnly = new ReadOnly();

    @Override
    public boolean test(PageFragment fragment) {
        return isPresent(fragment) && isVisible(fragment) && isEnabled(fragment) && isNotReadOnly(fragment);
    }

    private boolean isPresent(PageFragment pageFragment) {
        return present.test(pageFragment);
    }

    private boolean isVisible(PageFragment pageFragment) {
        return visible.test(pageFragment);
    }

    private boolean isEnabled(PageFragment pageFragment) {
        return enabled.test(pageFragment);
    }

    private boolean isNotReadOnly(PageFragment pageFragment) {
        return !readOnly.test(pageFragment);
    }

    @Override
    public String toString() {
        return "editable";
    }

}
