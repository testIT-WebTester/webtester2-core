package info.novatec.testit.webtester.conditions.pagefragments;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.unit.MockFactory.singleSelect;

import org.junit.Test;

import info.novatec.testit.webtester.pagefragments.SingleSelect;


public class SelectedTextTest {

    SelectedText cut = new SelectedText("foo");

    @Test
    public void matchingSelectionEvaluatesToTrue() {
        SingleSelect select = singleSelect().withSelectedText("foo").build();
        assertThat(cut.test(select)).isTrue();
    }

    @Test
    public void differentSelectionEvaluatesToFalse() {
        SingleSelect select = singleSelect().withSelectedText("bar").build();
        assertThat(cut.test(select)).isFalse();
    }

    @Test
    public void noSelectionEvaluatesToFalse() {
        SingleSelect select = singleSelect().withoutSelectedText().build();
        assertThat(cut.test(select)).isFalse();
    }

    @Test
    public void toStringIsGeneratedCorrectly() {
        assertThat(cut).hasToString("selected text: foo");
    }

}
