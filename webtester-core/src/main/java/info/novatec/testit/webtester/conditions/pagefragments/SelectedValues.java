package info.novatec.testit.webtester.conditions.pagefragments;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;

import info.novatec.testit.webtester.pagefragments.MultiSelect;


/**
 * {@link Predicate} to be used in order to check if a {@link MultiSelect multi-select} has a certain set of selected
 * values.
 *
 * @see MultiSelect
 * @see MultiSelect#getSelectionTexts()
 * @since 2.0
 */
public class SelectedValues implements Predicate<MultiSelect> {

    private final Set<String> expectedValues = new HashSet<>();

    public SelectedValues(String value) {
        this.expectedValues.add(value);
    }

    public SelectedValues(String... values) {
        this.expectedValues.addAll(Arrays.asList(values));
    }

    public SelectedValues(Collection<String> values) {
        this.expectedValues.addAll(values);
    }

    @Override
    public boolean test(MultiSelect select) {
        return expectedValues.equals(select.streamSelectionValues().collect(Collectors.toSet()));
    }

    @Override
    public String toString() {
        return String.format("selected values: %s", StringUtils.join(expectedValues, ", "));
    }

}
