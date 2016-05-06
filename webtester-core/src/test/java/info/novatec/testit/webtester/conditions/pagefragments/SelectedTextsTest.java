package info.novatec.testit.webtester.conditions.pagefragments;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.MockFactory.multiSelect;

import org.junit.Test;

import info.novatec.testit.webtester.pagefragments.MultiSelect;


public class SelectedTextsTest {

    @Test
    public void matchingAllTextsEvaluatesToTrue() {
        MultiSelect select = multiSelect().withSelectedTexts("foo", "bar").build();
        SelectedTexts cut = new SelectedTexts("foo", "bar");
        assertThat(cut.test(select)).isTrue();
    }

    @Test
    public void orderOfExpectedTextsDoesNotMatter() {
        MultiSelect select = multiSelect().withSelectedTexts("bar", "foo").build();
        SelectedTexts cut = new SelectedTexts("foo", "bar");
        assertThat(cut.test(select)).isTrue();
    }

    @Test
    public void oneMissingSelectionEvaluatesToFalse() {
        MultiSelect select = multiSelect().withSelectedTexts("foo").build();
        SelectedTexts cut = new SelectedTexts("foo", "bar");
        assertThat(cut.test(select)).isFalse();
    }

    @Test
    public void oneExtraSelectionEvaluatesToFalse() {
        MultiSelect select = multiSelect().withSelectedTexts("foo", "bar", "xur").build();
        SelectedTexts cut = new SelectedTexts("foo", "bar");
        assertThat(cut.test(select)).isFalse();
    }

    @Test
    public void differentSelectionEvaluatesToFalse() {
        MultiSelect select = multiSelect().withSelectedTexts("xur").build();
        SelectedTexts cut = new SelectedTexts("foo");
        assertThat(cut.test(select)).isFalse();
    }

    @Test
    public void noSelectionEvaluatesToFalse() {
        MultiSelect select = multiSelect().withoutSelectedTexts().build();
        SelectedTexts cut = new SelectedTexts("foo");
        assertThat(cut.test(select)).isFalse();
    }

}
