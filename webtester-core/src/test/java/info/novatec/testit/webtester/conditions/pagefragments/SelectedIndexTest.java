package info.novatec.testit.webtester.conditions.pagefragments;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.MockFactory.singleSelect;

import org.junit.Test;

import info.novatec.testit.webtester.pagefragments.SingleSelect;


public class SelectedIndexTest {

    SelectedIndex cut = new SelectedIndex(0);

    @Test
    public void matchingSelectionEvaluatesToTrue() {
        SingleSelect select = singleSelect().withSelectedIndex(0).build();
        assertThat(cut.test(select)).isTrue();
    }

    @Test
    public void differentSelectionEvaluatesToFalse() {
        SingleSelect select = singleSelect().withSelectedIndex(1).build();
        assertThat(cut.test(select)).isFalse();
    }

    @Test
    public void noSelectionEvaluatesToFalse() {
        SingleSelect select = singleSelect().withoutSelectedIndex().build();
        assertThat(cut.test(select)).isFalse();
    }

    @Test
    public void toStringIsGeneratedCorrectly() {
        assertThat(cut).hasToString("selected index: 0");
    }

}
