package info.novatec.testit.webtester.conditions.pagefragments;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.unit.MockFactory.singleSelect;

import org.junit.Test;

import info.novatec.testit.webtester.pagefragments.SingleSelect;


public class SelectedValueTest {

    SelectedValue cut = new SelectedValue("foo");

    @Test
    public void matchingSelectionEvaluatesToTrue() {
        SingleSelect select = singleSelect().withSelectedValue("foo").build();
        assertThat(cut.test(select)).isTrue();
    }

    @Test
    public void differentSelectionEvaluatesToFalse() {
        SingleSelect select = singleSelect().withSelectedValue("bar").build();
        assertThat(cut.test(select)).isFalse();
    }

    @Test
    public void noSelectionEvaluatesToFalse() {
        SingleSelect select = singleSelect().withoutSelectedValue().build();
        assertThat(cut.test(select)).isFalse();
    }

    @Test
    public void toStringIsGeneratedCorrectly() {
        assertThat(cut).hasToString("selected value: foo");
    }

}
