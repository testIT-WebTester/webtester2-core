package info.novatec.testit.webtester.conditions.pagefragments;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.MockFactory.multiSelect;

import java.util.Arrays;

import org.junit.Test;

import info.novatec.testit.webtester.pagefragments.MultiSelect;


public class SelectedValuesTest {

    @Test
    public void matchingAllValuesEvaluatesToTrue() {
        MultiSelect select = multiSelect().withSelectedValues("foo", "bar").build();
        SelectedValues cut = new SelectedValues("foo", "bar");
        assertThat(cut.test(select)).isTrue();
    }

    @Test
    public void orderOfExpectedValuesDoesNotMatter() {
        MultiSelect select = multiSelect().withSelectedValues("bar", "foo").build();
        SelectedValues cut = new SelectedValues("foo", "bar");
        assertThat(cut.test(select)).isTrue();
    }

    @Test
    public void oneMissingSelectionEvaluatesToFalse() {
        MultiSelect select = multiSelect().withSelectedValues("foo").build();
        SelectedValues cut = new SelectedValues("foo", "bar");
        assertThat(cut.test(select)).isFalse();
    }

    @Test
    public void oneExtraSelectionEvaluatesToFalse() {
        MultiSelect select = multiSelect().withSelectedValues("foo", "bar", "xur").build();
        SelectedValues cut = new SelectedValues("foo", "bar");
        assertThat(cut.test(select)).isFalse();
    }

    @Test
    public void differentSelectionEvaluatesToFalse() {
        MultiSelect select = multiSelect().withSelectedValues("xur").build();
        SelectedValues cut = new SelectedValues("foo");
        assertThat(cut.test(select)).isFalse();
    }

    @Test
    public void noSelectionEvaluatesToFalse() {
        MultiSelect select = multiSelect().withoutSelectedValues().build();
        SelectedValues cut = new SelectedValues("foo");
        assertThat(cut.test(select)).isFalse();
    }

    @Test
    public void collectionsOfValuesCanBeEvaluated() {
        MultiSelect select = multiSelect().withSelectedValues("a", "b", "c").build();
        SelectedValues cut = new SelectedValues(Arrays.asList("a", "b", "c"));
        assertThat(cut.test(select)).isTrue();
    }

    @Test
    public void toStringIsGeneratedCorrectly() {
        SelectedValues cut = new SelectedValues("foo", "bar");
        assertThat(cut).hasToString("selected values: foo, bar");
    }

}
