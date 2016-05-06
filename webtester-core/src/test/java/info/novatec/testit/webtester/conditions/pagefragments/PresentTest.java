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
        assertThat(isPresent(fragment)).isTrue();
    }

    @Test
    public void notPresentPageFragmentEvaluatesToFalse() {
        PageFragment fragment = fragment().notPresent().build();
        assertThat(isPresent(fragment)).isFalse();
    }

    boolean isPresent(PageFragment fragment) {
        return cut.test(fragment);
    }

}
