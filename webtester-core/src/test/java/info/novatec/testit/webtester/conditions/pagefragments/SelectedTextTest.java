package info.novatec.testit.webtester.conditions.pagefragments;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.MockFactory.singleSelect;

import org.junit.Test;

import info.novatec.testit.webtester.pagefragments.SingleSelect;


public class SelectedTextTest {

    SelectedText cut = new SelectedText("foo");

    @Test
    public void matchingSelectionEvaluatesToTrue() {
        SingleSelect select = singleSelect().withSelectedText("foo").build();
        assertThat(hasSelectedText(select)).isTrue();
    }

    @Test
    public void differentSelectionEvaluatesToFalse() {
        SingleSelect select = singleSelect().withSelectedText("bar").build();
        assertThat(hasSelectedText(select)).isFalse();
    }

    @Test
    public void noSelectionEvaluatesToFalse() {
        SingleSelect select = singleSelect().withoutSelectedText().build();
        assertThat(hasSelectedText(select)).isFalse();
    }

    boolean hasSelectedText(SingleSelect select) {
        return cut.test(select);
    }

}
