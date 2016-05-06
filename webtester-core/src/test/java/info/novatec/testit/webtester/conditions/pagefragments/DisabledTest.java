package info.novatec.testit.webtester.conditions.pagefragments;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.MockFactory.fragment;

import org.junit.Test;

import info.novatec.testit.webtester.pagefragments.PageFragment;


public class DisabledTest {

    Disabled cut = new Disabled();

    @Test
    public void disabledPageFragmentEvaluatesToTrue() {
        PageFragment fragment = fragment().disabled().build();
        assertThat(isDisabled(fragment)).isTrue();
    }

    @Test
    public void enabledPageFragmentEvaluatesToFalse() {
        PageFragment fragment = fragment().enabled().build();
        assertThat(isDisabled(fragment)).isFalse();
    }

    boolean isDisabled(PageFragment fragment) {
        return cut.test(fragment);
    }

}
