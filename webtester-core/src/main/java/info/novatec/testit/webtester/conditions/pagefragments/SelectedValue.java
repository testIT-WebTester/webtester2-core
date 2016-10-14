package info.novatec.testit.webtester.conditions.pagefragments;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import info.novatec.testit.webtester.conditions.Condition;
import info.novatec.testit.webtester.pagefragments.SingleSelect;


/**
 * {@link Condition} to be used in order to check if a {@link SingleSelect single-select} has a certain selected value.
 *
 * @see Condition
 * @see SingleSelect
 * @see SingleSelect#getSelectionValue()
 * @since 2.0
 */
@Getter
@AllArgsConstructor
public class SelectedValue implements Condition<SingleSelect> {

    @NonNull
    private final String expectedValue;

    @Override
    public boolean test(SingleSelect select) {
        return select.getSelectionValue().filter(expectedValue::equals).isPresent();
    }

    @Override
    public String toString() {
        return String.format("selected value: %s", expectedValue);
    }

}
