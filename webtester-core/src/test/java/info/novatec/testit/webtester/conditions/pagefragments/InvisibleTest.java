package info.novatec.testit.webtester.conditions.pagefragments;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.unit.MockFactory.fragment;

import org.junit.Test;

import info.novatec.testit.webtester.pagefragments.PageFragment;


public class InvisibleTest {

    Invisible cut = new Invisible();

    @Test
    public void invisiblePageFragmentEvaluatesToTrue() {
        PageFragment fragment = fragment().invisible().build();
        assertThat(cut.test(fragment)).isTrue();
    }

    @Test
    public void visiblePageFragmentEvaluatesToFalse() {
        PageFragment fragment = fragment().visible().build();
        assertThat(cut.test(fragment)).isFalse();
    }

    @Test
    public void toStringIsGeneratedCorrectly() {
        assertThat(cut).hasToString("invisible");
    }

}
