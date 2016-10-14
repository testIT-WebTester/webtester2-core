package info.novatec.testit.webtester.conditions.pagefragments;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

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
@AllArgsConstructor
public class VisibleTextContains implements Condition<PageFragment> {

    @NonNull
    private final String expectedPartialText;

    @Override
    public boolean test(PageFragment pageFragment) {
        return pageFragment.getVisibleText().contains(expectedPartialText);
    }

    @Override
    public String toString() {
        return String.format("visible text contains: %s", expectedPartialText);
    }

}
