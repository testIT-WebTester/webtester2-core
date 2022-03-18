package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.has;
import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.options;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import utils.unit.MockFactory;

import info.novatec.testit.webtester.pagefragments.MultiSelect;


class NumberOfOptionsMatcherTest {

    @Test
    void matchingNumberOfOptionsMatches() {
        MultiSelect fragment = MockFactory.multiSelect().withNumberOfOptions(5).build();
        assertThat(fragment, has(options(5)));
    }

    @Test
    void mismatchingNumberOfOptionsDoesNotMatch() {
        assertThrows(AssertionError.class, () -> {
            MultiSelect fragment = MockFactory.multiSelect().withNumberOfOptions(4).build();
            assertThat(fragment, has(options(5)));
        });
    }

}
