package info.novatec.testit.webtester.conditions.pagefragments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import lombok.Getter;

import info.novatec.testit.webtester.conditions.Condition;
import info.novatec.testit.webtester.pagefragments.MultiSelect;


/**
 * {@link Condition} to be used in order to check if a {@link MultiSelect multi-select} has a certain set of selected
 * texts.
 *
 * @see Condition
 * @see MultiSelect
 * @see MultiSelect#getSelectionTexts()
 * @since 2.0
 */
@Getter
public class SelectedTexts implements Condition<MultiSelect> {

    private final List<String> expectedTexts = new ArrayList<>();

    public SelectedTexts(String expectedText) {
        this.expectedTexts.add(expectedText);
    }

    public SelectedTexts(String... expectedTexts) {
        this.expectedTexts.addAll(Arrays.asList(expectedTexts));
    }

    public SelectedTexts(Collection<String> expectedTexts) {
        this.expectedTexts.addAll(expectedTexts);
    }

    @Override
    public boolean test(MultiSelect select) {
        List<String> selectionTexts = select.getSelectionTexts();
        return expectedTexts.containsAll(selectionTexts) && selectionTexts.containsAll(expectedTexts);
    }

    @Override
    public String toString() {
        return String.format("selected texts: %s", StringUtils.join(expectedTexts, ", "));
    }

}
