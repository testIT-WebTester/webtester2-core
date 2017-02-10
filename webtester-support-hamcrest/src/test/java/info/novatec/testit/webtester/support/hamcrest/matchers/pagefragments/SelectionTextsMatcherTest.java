package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.has;
import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.selectionWithTexts;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import utils.unit.MockFactory;

import info.novatec.testit.webtester.pagefragments.MultiSelect;


class SelectionTextsMatcherTest {

    @Test
    void selectionInOrderMatches() {
        MultiSelect select = MockFactory.multiSelect().withSelectedTexts("foo", "bar").build();
        assertThat(select, has(selectionWithTexts("foo", "bar")));
    }

    @Test
    void selectionOutOfOrderDoesNotMatch() {
        assertThrows(AssertionError.class, () -> {
            MultiSelect select = MockFactory.multiSelect().withSelectedTexts("foo", "bar").build();
            assertThat(select, has(selectionWithTexts("bar", "foo")));
        });
    }

    @Test
    void wrongSelectionDoesNotMatch() {
        assertThrows(AssertionError.class, () -> {
            MultiSelect select = MockFactory.multiSelect().withSelectedTexts("foo").build();
            assertThat(select, has(selectionWithTexts("bar")));
        });
    }

    @Test
    void oneToManySelectionDoesNotMatch() {
        assertThrows(AssertionError.class, () -> {
            MultiSelect select = MockFactory.multiSelect().withSelectedTexts("foo", "bar").build();
            assertThat(select, has(selectionWithTexts("foo")));
        });
    }

    @Test
    void oneToFewSelectionDoesNotMatch() {
        assertThrows(AssertionError.class, () -> {
            MultiSelect select = MockFactory.multiSelect().withSelectedTexts("foo").build();
            assertThat(select, has(selectionWithTexts("foo", "bar")));
        });
    }

}
