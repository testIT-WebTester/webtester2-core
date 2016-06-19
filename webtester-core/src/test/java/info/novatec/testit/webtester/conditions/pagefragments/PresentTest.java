package info.novatec.testit.webtester.conditions.pagefragments;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.MockFactory.fragment;

import org.junit.Test;

import info.novatec.testit.webtester.pagefragments.PageFragment;


public class PresentTest {

    Present cut = new Present();

    @Test
    public void presentPageFragmentEvaluatesToTrue() {
        PageFragment fragment = fragment().present().build();
        assertThat(cut.test(fragment)).isTrue();
    }

    @Test
    public void notPresentPageFragmentEvaluatesToFalse() {
        PageFragment fragment = fragment().notPresent().build();
        assertThat(cut.test(fragment)).isFalse();
    }

    @Test
    public void toStringIsGeneratedCorrectly() {
        assertThat(cut).hasToString("present");
    }

}
