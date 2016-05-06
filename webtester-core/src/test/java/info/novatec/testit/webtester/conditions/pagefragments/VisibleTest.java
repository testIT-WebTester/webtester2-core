package info.novatec.testit.webtester.conditions.pagefragments;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.MockFactory.fragment;

import org.junit.Test;

import info.novatec.testit.webtester.pagefragments.PageFragment;


public class VisibleTest {

    Visible cut = new Visible();

    @Test
    public void visiblePageFragmentEvaluatesToTrue() {
        PageFragment fragment = fragment().visible().build();
        assertThat(isVisible(fragment)).isTrue();
    }

    @Test
    public void invisiblePageFragmentEvaluatesToFalse() {
        PageFragment fragment = fragment().invisible().build();
        assertThat(isVisible(fragment)).isFalse();
    }

    boolean isVisible(PageFragment fragment) {
        return cut.test(fragment);
    }

}
