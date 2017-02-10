package info.novatec.testit.webtester.conditions.pagefragments;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.unit.MockFactory.fragment;

import org.junit.Test;

import info.novatec.testit.webtester.pagefragments.PageFragment;


public class PresentAndVisibleTest {

    PresentAndVisible cut = new PresentAndVisible();

    @Test
    public void presentAndVisiblePageFragmentEvaluatesToTrue() {
        PageFragment fragment = fragment().present().visible().build();
        assertThat(cut.test(fragment)).isTrue();
    }

    /**
     * The constellation of this test should be impossible, but it is still tested in order to validate the general logic of
     * the condition.
     */
    @Test
    public void notPresentButVisiblePageFragmentEvaluatesToFalse() {
        PageFragment fragment = fragment().notPresent().visible().build();
        assertThat(cut.test(fragment)).isFalse();
    }

    @Test
    public void presentButInvisiblePageFragmentEvaluatesToFalse() {
        PageFragment fragment = fragment().present().invisible().build();
        assertThat(cut.test(fragment)).isFalse();
    }

    @Test
    public void toStringIsGeneratedCorrectly() {
        assertThat(cut).hasToString("present and visible");
    }

}
