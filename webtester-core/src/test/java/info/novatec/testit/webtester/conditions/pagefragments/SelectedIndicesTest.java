package info.novatec.testit.webtester.conditions.pagefragments;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.unit.MockFactory.multiSelect;

import java.util.Arrays;

import org.junit.Test;

import info.novatec.testit.webtester.pagefragments.MultiSelect;


public class SelectedIndicesTest {

    @Test
    public void matchingAllIndicesEvaluatesToTrue() {
        MultiSelect select = multiSelect().withSelectedIndices(0, 1).build();
        SelectedIndices cut = new SelectedIndices(0, 1);
        assertThat(cut.test(select)).isTrue();
    }

    @Test
    public void orderOfExpectedIndicesDoesNotMatter() {
        MultiSelect select = multiSelect().withSelectedIndices(0, 1).build();
        SelectedIndices cut = new SelectedIndices(1, 0);
        assertThat(cut.test(select)).isTrue();
    }

    @Test
    public void oneMissingSelectionEvaluatesToFalse() {
        MultiSelect select = multiSelect().withSelectedIndices(0).build();
        SelectedIndices cut = new SelectedIndices(0, 1);
        assertThat(cut.test(select)).isFalse();
    }

    @Test
    public void oneExtraSelectionEvaluatesToFalse() {
        MultiSelect select = multiSelect().withSelectedIndices(0, 1, 2).build();
        SelectedIndices cut = new SelectedIndices(0, 1);
        assertThat(cut.test(select)).isFalse();
    }

    @Test
    public void differentSelectionEvaluatesToFalse() {
        MultiSelect select = multiSelect().withSelectedIndices(2).build();
        SelectedIndices cut = new SelectedIndices(0);
        assertThat(cut.test(select)).isFalse();
    }

    @Test
    public void noSelectionEvaluatesToFalse() {
        MultiSelect select = multiSelect().withoutSelectedIndices().build();
        SelectedIndices cut = new SelectedIndices(0);
        assertThat(cut.test(select)).isFalse();
    }

    @Test
    public void collectionsOfIndicesCanBeEvaluated() {
        MultiSelect select = multiSelect().withSelectedIndices(0, 1, 2).build();
        SelectedIndices cut = new SelectedIndices(Arrays.asList(0, 1, 2));
        assertThat(cut.test(select)).isTrue();
    }

    @Test
    public void toStringIsGeneratedCorrectly() {
        SelectedIndices cut = new SelectedIndices(0, 1);
        assertThat(cut).hasToString("selected indices: 0, 1");
    }

}
