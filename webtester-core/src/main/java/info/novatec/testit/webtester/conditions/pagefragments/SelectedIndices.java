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
 * indices.
 *
 * @see MultiSelect
 * @see MultiSelect#getSelectionIndices()
 * @since 2.0
 */
public class SelectedIndices implements Predicate<MultiSelect> {

    private final Set<Integer> expectedIndices = new HashSet<>();

    public SelectedIndices(Integer index) {
        this.expectedIndices.add(index);
    }

    public SelectedIndices(Integer... indices) {
        this.expectedIndices.addAll(Arrays.asList(indices));
    }

    public SelectedIndices(Collection<Integer> indices) {
        this.expectedIndices.addAll(indices);
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
