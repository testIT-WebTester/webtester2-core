package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.has;
import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.selectionWithIndex;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import utils.unit.MockFactory;

import info.novatec.testit.webtester.pagefragments.SingleSelect;


class SelectionIndexMatcherTest {

    @Test
    void selectionMatches() {
        SingleSelect select = MockFactory.singleSelect().withSelectedIndex(1).build();
        assertThat(select, has(selectionWithIndex(1)));
    }

    @Test
    void selectionDoesNotMatch() {
        assertThrows(AssertionError.class, () -> {
            SingleSelect select = MockFactory.singleSelect().withSelectedIndex(1).build();
            assertThat(select, has(selectionWithIndex(2)));
        });
    }

}
