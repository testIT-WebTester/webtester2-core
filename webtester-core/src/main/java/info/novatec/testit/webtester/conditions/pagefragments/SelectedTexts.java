package info.novatec.testit.webtester.conditions.pagefragments;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

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
public class SelectedTexts implements Condition<MultiSelect> {

    private final List<String> expectedTexts = new LinkedList<>();

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
        List<String> selectionTexts = select.getSelectionTexts();
        return expectedTexts.containsAll(selectionTexts) && selectionTexts.containsAll(expectedTexts);
    }

    @Override
    public String toString() {
        return String.format("selected texts: %s", StringUtils.join(expectedTexts, ", "));
    }

}
