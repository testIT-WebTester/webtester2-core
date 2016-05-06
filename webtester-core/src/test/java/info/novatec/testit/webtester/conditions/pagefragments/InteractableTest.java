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
        assertThat(isInteractable(fragment)).isTrue();
    }

    @Test
    public void notPresentEnabledVisiblePageFragmentEvaluatesToFalse() {
        PageFragment fragment = fragment().notPresent().enabled().visible().build();
        assertThat(isInteractable(fragment)).isFalse();
    }

    @Test
    public void presentDisabledVisiblePageFragmentEvaluatesToFalse() {
        PageFragment fragment = fragment().present().disabled().visible().build();
        assertThat(isInteractable(fragment)).isFalse();
    }

    @Test
    public void presentEnabledInvisiblePageFragmentEvaluatesToFalse() {
        PageFragment fragment = fragment().present().enabled().invisible().build();
        assertThat(isInteractable(fragment)).isFalse();
    }

    boolean isInteractable(PageFragment fragment) {
        return cut.test(fragment);
    }

}
