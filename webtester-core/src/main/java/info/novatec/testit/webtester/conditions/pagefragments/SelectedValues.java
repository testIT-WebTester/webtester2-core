package info.novatec.testit.webtester.conditions.pagefragments;

import java.util.Arrays;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import lombok.Getter;

import info.novatec.testit.webtester.conditions.Condition;
import info.novatec.testit.webtester.pagefragments.MultiSelect;


/**
 * {@link Condition} to be used in order to check if a {@link MultiSelect multi-select} has a certain set of selected
 * values.
 *
 * @see Condition
 * @see MultiSelect
 * @see MultiSelect#getSelectionTexts()
 * @since 2.0
 */
@Getter
public class SelectedValues implements Condition<MultiSelect> {

    private final List<String> expectedValues = new ArrayList<>();

    public SelectedValues(String expectedValue) {
        this.expectedValues.add(expectedValue);
    }

    public SelectedValues(String... expectedValues) {
        this.expectedValues.addAll(Arrays.asList(expectedValues));
    }

    public SelectedValues(Collection<String> expectedValues) {
        this.expectedValues.addAll(expectedValues);
    }

    @Override
    public boolean test(MultiSelect select) {
        List<String> selectionValues = select.getSelectionValues();
        return expectedValues.containsAll(selectionValues) && selectionValues.containsAll(expectedValues);
    }

    @Override
    public String toString() {
        return String.format("selected values: %s", StringUtils.join(expectedValues, ", "));
    }

}
