package info.novatec.testit.webtester.conditions.pagefragments;

import info.novatec.testit.webtester.conditions.Condition;
import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.pagefragments.traits.Selectable;


/**
 * {@link Condition} to be used in order to check if the {@link Selectable selectable} {@link PageFragment page fragment} is
 * selected.
 *
 * @see Condition
 * @see Selectable
 * @see Selectable#isSelected()
 * @since 2.0
 */
public class Selected implements Condition<Selectable> {

    @Override
    public boolean test(Selectable selectable) {
        return selectable.isSelected();
    }

    @Override
    public String toString() {
        return "selected";
    }

}
