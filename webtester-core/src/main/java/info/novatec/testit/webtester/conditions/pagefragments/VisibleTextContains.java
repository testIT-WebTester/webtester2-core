package info.novatec.testit.webtester.conditions.pagefragments;

import java.util.function.Predicate;

import info.novatec.testit.webtester.pagefragments.PageFragment;


/**
 * {@link Predicate} to be used in order to check if a {@link PageFragment page fragments's} visible text contains a certain
 * string.
 *
 * @see PageFragment#getVisibleText()
 * @since 2.0
 */
public class VisibleTextContains implements Predicate<PageFragment> {

    private final String partialText;

    public VisibleTextContains(String partialText) {
        this.partialText = partialText;
    }

    @Override
    public boolean test(PageFragment pageFragment) {
        return pageFragment.getVisibleText().contains(partialText);
    }

    @Override
    public String toString() {
        return String.format("visible text contains: %s", partialText);
    }

}
