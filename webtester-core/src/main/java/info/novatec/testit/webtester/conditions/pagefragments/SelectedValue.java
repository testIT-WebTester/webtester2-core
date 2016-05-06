package info.novatec.testit.webtester.conditions.pagefragments;

import java.util.function.Predicate;

import info.novatec.testit.webtester.pagefragments.SingleSelect;


/**
 * {@link Predicate} to be used in order to check if a {@link SingleSelect single-select} has a certain selected value.
 *
 * @see SingleSelect
 * @see SingleSelect#getSelectionValue()
 * @since 2.0
 */
public class SelectedValue implements Predicate<SingleSelect> {

    private final String expectedValue;

    public SelectedValue(String expectedValue) {
        this.expectedValue = expectedValue;
    }

    @Override
    public boolean test(SingleSelect select) {
        return select.getSelectionValue().filter(expectedValue::equals).isPresent();
    }

    @Override
    public String toString() {
        return String.format("selected value: %s", expectedValue);
    }

}
