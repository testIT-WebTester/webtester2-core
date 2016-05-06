package info.novatec.testit.webtester.conditions.pagefragments;

import java.util.function.Predicate;

import info.novatec.testit.webtester.pagefragments.PageFragment;


/**
 * {@link Predicate} to be used in order to check if a {@link PageFragment page fragments's} visible text matches a certain
 * string.
 *
 * @see PageFragment#getVisibleText()
 * @since 2.0
 */
public class VisibleTextEquals implements Predicate<PageFragment> {

    private final String expectedText;

    public VisibleTextEquals(String expectedText) {
        this.expectedText = expectedText;
    }

    @Override
    public boolean test(PageFragment pageFragment) {
        return pageFragment.getVisibleText().equals(expectedText);
    }

    @Override
    public String toString() {
        return String.format("visible text equals: %s", expectedText);
    }

}
