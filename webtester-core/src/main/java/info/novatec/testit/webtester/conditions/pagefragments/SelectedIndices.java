package info.novatec.testit.webtester.conditions.pagefragments;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;

import lombok.Getter;

import info.novatec.testit.webtester.conditions.Condition;
import info.novatec.testit.webtester.pagefragments.MultiSelect;


/**
 * {@link Condition} to be used in order to check if a {@link MultiSelect multi-select} has a certain set of selected
 * indices.
 *
 * @see Condition
 * @see MultiSelect
 * @see MultiSelect#getSelectionIndices()
 * @since 2.0
 */
@Getter
public class SelectedIndices implements Condition<MultiSelect> {

    private final Set<Integer> expectedIndices = new HashSet<>();

    public SelectedIndices(Integer expectedIndex) {
        this.expectedIndices.add(expectedIndex);
    }

    public SelectedIndices(Integer... expectedIndices) {
        this.expectedIndices.addAll(Arrays.asList(expectedIndices));
    }

    public SelectedIndices(Collection<Integer> expectedIndices) {
        this.expectedIndices.addAll(expectedIndices);
    }

    @Override
    public boolean test(MultiSelect select) {
        return expectedIndices.equals(select.streamSelectionIndices().collect(Collectors.toSet()));
    }

    @Override
    public String toString() {
        return String.format("selected indices: %s", StringUtils.join(expectedIndices, ", "));
    }

}
