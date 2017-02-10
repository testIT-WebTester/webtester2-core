package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.has;
import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.selectionWithIndices;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import utils.unit.MockFactory;

import info.novatec.testit.webtester.pagefragments.MultiSelect;


class SelectionIndicesMatcherTest {

    @Test
    void selectionInOrderMatches() {
        MultiSelect select = MockFactory.multiSelect().withSelectedIndices(1, 2).build();
        assertThat(select, has(selectionWithIndices(1, 2)));
    }

    @Test
    void selectionOutOfOrderDoesNotMatch() {
        assertThrows(AssertionError.class, () -> {
            MultiSelect select = MockFactory.multiSelect().withSelectedIndices(1, 2).build();
            assertThat(select, has(selectionWithIndices(2, 1)));
        });
    }

    @Test
    void wrongSelectionDoesNotMatch() {
        assertThrows(AssertionError.class, () -> {
            MultiSelect select = MockFactory.multiSelect().withSelectedIndices(1).build();
            assertThat(select, has(selectionWithIndices(2)));
        });
    }

    @Test
    void oneToManySelectionDoesNotMatch() {
        assertThrows(AssertionError.class, () -> {
            MultiSelect select = MockFactory.multiSelect().withSelectedIndices(1, 2).build();
            assertThat(select, has(selectionWithIndices(1)));
        });
    }

    @Test
    void oneToFewSelectionDoesNotMatch() {
        assertThrows(AssertionError.class, () -> {
            MultiSelect select = MockFactory.multiSelect().withSelectedIndices(1).build();
            assertThat(select, has(selectionWithIndices(1, 2)));
        });
    }

}
