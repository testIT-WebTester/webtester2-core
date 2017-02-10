package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.has;
import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.selectionWithText;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import utils.unit.MockFactory;

import info.novatec.testit.webtester.pagefragments.SingleSelect;


class SelectionTextMatcherTest {

    @Test
    void selectionMatches() {
        SingleSelect select = MockFactory.singleSelect().withSelectedText("foo").build();
        assertThat(select, has(selectionWithText("foo")));
    }

    @Test
    void selectionDoesNotMatch() {
        assertThrows(AssertionError.class, () -> {
            SingleSelect select = MockFactory.singleSelect().withSelectedText("foo").build();
            assertThat(select, has(selectionWithText("bar")));
        });
    }

}
