package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.has;
import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.selectionWithIndices;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import utils.unit.MockFactory;

import info.novatec.testit.webtester.pagefragments.MultiSelect;


public class SelectionIndicesMatcherTest {

    @Test
    public void selectionInOrderMatches() {
        MultiSelect select = MockFactory.multiSelect().withSelectedIndices(1, 2).build();
        assertThat(select, has(selectionWithIndices(1, 2)));
    }

    @Test(expected = AssertionError.class)
    public void selectionOutOfOrderDoesNotMatch() {
        MultiSelect select = MockFactory.multiSelect().withSelectedIndices(1, 2).build();
        assertThat(select, has(selectionWithIndices(2, 1)));
    }

    @Test(expected = AssertionError.class)
    public void wrongSelectionDoesNotMatch() {
        MultiSelect select = MockFactory.multiSelect().withSelectedIndices(1).build();
        assertThat(select, has(selectionWithIndices(2)));
    }

    @Test(expected = AssertionError.class)
    public void oneToManySelectionDoesNotMatch() {
        MultiSelect select = MockFactory.multiSelect().withSelectedIndices(1, 2).build();
        assertThat(select, has(selectionWithIndices(1)));
    }

    @Test(expected = AssertionError.class)
    public void oneToFewSelectionDoesNotMatch() {
        MultiSelect select = MockFactory.multiSelect().withSelectedIndices(1).build();
        assertThat(select, has(selectionWithIndices(1, 2)));
    }

}
