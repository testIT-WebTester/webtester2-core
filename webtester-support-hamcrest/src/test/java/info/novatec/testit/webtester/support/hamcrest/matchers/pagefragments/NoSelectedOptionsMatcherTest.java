package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.has;
import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.noSelectedOptions;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import utils.unit.MockFactory;

import info.novatec.testit.webtester.pagefragments.MultiSelect;


class NoSelectedOptionsMatcherTest {

    @Test
    void noSelectionMatches() {
        MultiSelect fragment = MockFactory.multiSelect().withNumberOfSelectedOptions(0).build();
        assertThat(fragment, has(noSelectedOptions()));
    }

    @Test
    void selectionDoesNotMatch() {
        assertThrows(AssertionError.class, () -> {
            MultiSelect fragment = MockFactory.multiSelect().withNumberOfSelectedOptions(1).build();
            assertThat(fragment, has(noSelectedOptions()));
        });
    }

}
