package info.novatec.testit.webtester.conditions.pagefragments;

import static utils.MockFactory.singleSelect;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import info.novatec.testit.webtester.pagefragments.SingleSelect;


public class SelectedIndexTest {

    SelectedIndex cut = new SelectedIndex(0);

    @Test
    public void matchingSelectionEvaluatesToTrue() {
        SingleSelect select = singleSelect().withSelectedIndex(0).build();
        Assertions.assertThat(hasSelectedIndex(select)).isTrue();
    }

    @Test
    public void differentSelectionEvaluatesToFalse() {
        SingleSelect select = singleSelect().withSelectedIndex(1).build();
        Assertions.assertThat(hasSelectedIndex(select)).isFalse();
    }

    @Test
    public void noSelectionEvaluatesToFalse() {
        SingleSelect select = singleSelect().withoutSelectedIndex().build();
        Assertions.assertThat(hasSelectedIndex(select)).isFalse();
    }

    boolean hasSelectedIndex(SingleSelect select) {
        return cut.test(select);
    }

}
