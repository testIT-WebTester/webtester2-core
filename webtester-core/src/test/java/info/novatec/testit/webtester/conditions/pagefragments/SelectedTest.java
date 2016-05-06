package info.novatec.testit.webtester.conditions.pagefragments;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.MockFactory.selectable;

import org.junit.Test;

import info.novatec.testit.webtester.pagefragments.traits.Selectable;


public class SelectedTest {

    Selected cut = new Selected();

    @Test
    public void selectedSelectable() {
        Selectable selectable = selectable().isSelected().build();
        assertThat(isSelected(selectable)).isTrue();
    }

    @Test
    public void notSelectedSelectable() {
        Selectable selectable = selectable().isNotSelected().build();
        assertThat(isSelected(selectable)).isFalse();
    }

    boolean isSelected(Selectable selectable) {
        return cut.test(selectable);
    }

}
