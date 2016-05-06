package info.novatec.testit.webtester.support.assertj.assertions.pagefragments;

import static info.novatec.testit.webtester.support.assertj.WebTesterAssertions.assertThat;

import org.junit.Test;

import utils.MockFactory;

import info.novatec.testit.webtester.pagefragments.traits.Selectable;


public class SelectableAssertTest {

    @Test
    public void isSelectedPositive() {
        assertThat(selectedSelectable()).isSelected();
    }

    @Test(expected = AssertionError.class)
    public void isSelectedNegative() {
        assertThat(nonSelectedSelectable()).isSelected();
    }

    @Test
    public void isNotSelectedPositive() {
        assertThat(nonSelectedSelectable()).isNotSelected();
    }

    @Test(expected = AssertionError.class)
    public void isNotSelectedNegative() {
        assertThat(selectedSelectable()).isNotSelected();
    }

    private Selectable selectedSelectable() {
        return MockFactory.selectable().isSelected().build();
    }

    private Selectable nonSelectedSelectable() {
        return MockFactory.selectable().isNotSelected().build();
    }

}
