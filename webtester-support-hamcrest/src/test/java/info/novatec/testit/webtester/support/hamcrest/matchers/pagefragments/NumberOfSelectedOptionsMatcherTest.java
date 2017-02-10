package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.has;
import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.selectedOptions;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import utils.unit.MockFactory;

import info.novatec.testit.webtester.pagefragments.MultiSelect;


class NumberOfSelectedOptionsMatcherTest {

    @Test
    void matchingNumberOfSelectedOptionsMatches() {
        MultiSelect fragment = MockFactory.multiSelect().withNumberOfSelectedOptions(5).build();
        assertThat(fragment, has(selectedOptions(5)));
    }

    @Test
    void mismatchingNumberOfSelectedOptionsDoesNotMatch() {
        assertThrows(AssertionError.class, () -> {
            MultiSelect fragment = MockFactory.multiSelect().withNumberOfSelectedOptions(4).build();
            assertThat(fragment, has(selectedOptions(5)));
        });
    }

}
