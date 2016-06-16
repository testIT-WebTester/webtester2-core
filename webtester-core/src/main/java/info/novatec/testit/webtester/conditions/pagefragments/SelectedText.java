package info.novatec.testit.webtester.conditions.pagefragments;

import info.novatec.testit.webtester.conditions.Condition;
import info.novatec.testit.webtester.pagefragments.SingleSelect;


/**
 * {@link Condition} to be used in order to check if a {@link SingleSelect single-select} has a certain selected text.
 *
 * @see Condition
 * @see SingleSelect
 * @see SingleSelect#getSelectionText()
 * @since 2.0
 */
public class SelectedText implements Condition<SingleSelect> {

    private final String expectedText;

    public SelectedText(String expectedText) {
        this.expectedText = expectedText;
    }

    @Override
    public boolean test(SingleSelect select) {
        return select.getSelectionText().filter(expectedText::equals).isPresent();
    }

    @Override
    public String toString() {
        return String.format("selected text: %s", expectedText);
    }

}
