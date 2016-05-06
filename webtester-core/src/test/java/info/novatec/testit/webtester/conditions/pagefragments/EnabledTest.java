package info.novatec.testit.webtester.conditions.pagefragments;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.MockFactory.fragment;

import org.junit.Test;

import info.novatec.testit.webtester.pagefragments.PageFragment;


public class EnabledTest {

    Enabled cut = new Enabled();

    @Test
    public void enabledPageFragmentEvaluatesToTrue() {
        PageFragment fragment = fragment().enabled().build();
        assertThat(isEnabled(fragment)).isTrue();
    }

    @Test
    public void disabledPageFragmentEvaluatesToFalse() {
        PageFragment fragment = fragment().disabled().build();
        assertThat(isEnabled(fragment)).isFalse();
    }

    boolean isEnabled(PageFragment fragment) {
        return cut.test(fragment);
    }

}
