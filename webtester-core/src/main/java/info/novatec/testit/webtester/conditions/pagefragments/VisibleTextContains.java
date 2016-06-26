package info.novatec.testit.webtester.conditions.pagefragments;

import lombok.Getter;

import info.novatec.testit.webtester.conditions.Condition;
import info.novatec.testit.webtester.pagefragments.PageFragment;


/**
 * {@link Condition} to be used in order to check if a {@link PageFragment page fragments's} visible text contains a certain
 * string.
 *
 * @see Condition
 * @see PageFragment#getVisibleText()
 * @since 2.0
 */
@Getter
public class VisibleTextContains implements Condition<PageFragment> {

    private final String expectedPartialText;

    public VisibleTextContains(String expectedPartialText) {
        this.expectedPartialText = expectedPartialText;
    }

    @Override
    public boolean test(PageFragment pageFragment) {
        return pageFragment.getVisibleText().contains(expectedPartialText);
    }

    @Override
    public String toString() {
        return String.format("visible text contains: %s", expectedPartialText);
    }

}
