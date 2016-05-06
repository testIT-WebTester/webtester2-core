package info.novatec.testit.webtester.conditions.pagefragments;

import java.util.function.Predicate;

import info.novatec.testit.webtester.pagefragments.SingleSelect;


/**
 * {@link Predicate} to be used in order to check if a {@link SingleSelect single-select} has a certain selected index.
 *
 * @see SingleSelect
 * @see SingleSelect#getSelectionIndex()
 * @since 2.0
 */
public class SelectedIndex implements Predicate<SingleSelect> {

    private final Integer expectedIndex;

    public SelectedIndex(Integer expectedIndex) {
        this.expectedIndex = expectedIndex;
    }

    @Override
    public boolean test(SingleSelect select) {
        return select.getSelectionIndex().filter(expectedIndex::equals).isPresent();
    }

    @Override
    public String toString() {
        return String.format("selected index: %s", expectedIndex);
    }

}
