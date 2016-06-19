package info.novatec.testit.webtester.conditions.pagefragments;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.MockFactory.fragment;

import org.junit.Test;

import info.novatec.testit.webtester.pagefragments.PageFragment;


public class InteractableTest {

    Interactable cut = new Interactable();

    @Test
    public void presentEnabledVisiblePageFragmentEvaluatesToTrue() {
        PageFragment fragment = fragment().present().enabled().visible().build();
        assertThat(cut.test(fragment)).isTrue();
    }

    @Test
    public void notPresentEnabledVisiblePageFragmentEvaluatesToFalse() {
        PageFragment fragment = fragment().notPresent().enabled().visible().build();
        assertThat(cut.test(fragment)).isFalse();
    }

    @Test
    public void presentDisabledVisiblePageFragmentEvaluatesToFalse() {
        PageFragment fragment = fragment().present().disabled().visible().build();
        assertThat(cut.test(fragment)).isFalse();
    }

    @Test
    public void presentEnabledInvisiblePageFragmentEvaluatesToFalse() {
        PageFragment fragment = fragment().present().enabled().invisible().build();
        assertThat(cut.test(fragment)).isFalse();
    }

    @Test
    public void toStringIsGeneratedCorrectly() {
        assertThat(cut).hasToString("interactable");
    }

}
