package info.novatec.testit.webtester.conditions.pagefragments;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.unit.MockFactory.selectable;

import org.junit.Test;

import info.novatec.testit.webtester.pagefragments.traits.Selectable;


public class SelectedTest {

    Selected cut = new Selected();

    @Test
    public void selectedSelectable() {
        Selectable selectable = selectable().isSelected().build();
        assertThat(cut.test(selectable)).isTrue();
    }

    @Test
    public void notSelectedSelectable() {
        Selectable selectable = selectable().isNotSelected().build();
        assertThat(cut.test(selectable)).isFalse();
    }

    @Test
    public void toStringIsGeneratedCorrectly() {
        assertThat(cut).hasToString("selected");
    }

}
