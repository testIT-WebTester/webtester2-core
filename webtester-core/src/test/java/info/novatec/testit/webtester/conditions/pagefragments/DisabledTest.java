package info.novatec.testit.webtester.conditions.pagefragments;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.unit.MockFactory.fragment;

import org.junit.Test;

import info.novatec.testit.webtester.pagefragments.PageFragment;


public class DisabledTest {

    Disabled cut = new Disabled();

    @Test
    public void disabledPageFragmentEvaluatesToTrue() {
        PageFragment fragment = fragment().disabled().build();
        assertThat(cut.test(fragment)).isTrue();
    }

    @Test
    public void enabledPageFragmentEvaluatesToFalse() {
        PageFragment fragment = fragment().enabled().build();
        assertThat(cut.test(fragment)).isFalse();
    }

    @Test
    public void toStringIsGeneratedCorrectly() {
        assertThat(cut).hasToString("disabled");
    }

}
