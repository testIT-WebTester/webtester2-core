package info.novatec.testit.webtester.conditions.collections;

import java.util.Collection;

import info.novatec.testit.webtester.conditions.Condition;
import info.novatec.testit.webtester.pagefragments.PageFragment;


/**
 * {@link Condition} to be used in order to check if a collection of {@link PageFragment page fragments} is NOT empty.
 *
 * @see Condition
 * @since 2.3
 */
public class NotEmpty implements Condition<Collection<PageFragment>> {

    @Override
    public boolean test(Collection<PageFragment> pageFragments) {
        return !pageFragments.isEmpty();
    }

    @Override
    public String toString() {
        return "not empty";
    }

}
