package info.novatec.testit.webtester.conditions.pagefragments;

import java.util.function.Predicate;

import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.pagefragments.traits.Selectable;


/**
 * {@link Predicate} to be used in order to check if the {@link Selectable selectable} {@link PageFragment page fragment} is
 * selected.
 *
 * @see Selectable
 * @see Selectable#isSelected()
 * @since 2.0
 */
public class Selected implements Predicate<Selectable> {

    @Override
    public boolean test(Selectable selectable) {
        return selectable.isSelected();
    }

    @Override
    public String toString() {
        return "selected";
    }

}
