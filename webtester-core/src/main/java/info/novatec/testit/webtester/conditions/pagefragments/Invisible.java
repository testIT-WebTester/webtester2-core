package info.novatec.testit.webtester.conditions.pagefragments;

import info.novatec.testit.webtester.conditions.Condition;
import info.novatec.testit.webtester.pagefragments.PageFragment;


/**
 * {@link Condition} to be used in order to check if a {@link PageFragment page fragment} is invisible.
 *
 * @see Condition
 * @see PageFragment#isVisible()
 * @since 2.0
 */
public class Invisible implements Condition<PageFragment> {

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
