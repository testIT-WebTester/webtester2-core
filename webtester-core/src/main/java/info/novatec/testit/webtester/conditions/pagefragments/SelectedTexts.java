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
 * texts.
 *
 * @see MultiSelect
 * @see MultiSelect#getSelectionTexts()
 * @since 2.0
 */
public class SelectedTexts implements Predicate<MultiSelect> {

    private final Set<String> expectedTexts = new HashSet<>();

    public SelectedTexts(String text) {
        this.expectedTexts.add(text);
    }

    public SelectedTexts(String... texts) {
        this.expectedTexts.addAll(Arrays.asList(texts));
    }

    public SelectedTexts(Collection<String> texts) {
        this.expectedTexts.addAll(texts);
    }

    @Override
    public boolean test(MultiSelect select) {
        return expectedTexts.equals(select.streamSelectionTexts().collect(Collectors.toSet()));
    }

    @Override
    public String toString() {
        return String.format("selected texts: %s", StringUtils.join(expectedTexts, ", "));
    }

}
