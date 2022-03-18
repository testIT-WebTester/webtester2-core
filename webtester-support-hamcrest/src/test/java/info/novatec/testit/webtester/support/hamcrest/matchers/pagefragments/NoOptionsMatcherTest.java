package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.has;
import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.noOptions;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import utils.unit.MockFactory;

import info.novatec.testit.webtester.pagefragments.MultiSelect;


class NoOptionsMatcherTest {

    @Test
    void noOptionsMatches() {
        MultiSelect fragment = MockFactory.multiSelect().withNumberOfOptions(0).build();
        assertThat(fragment, has(noOptions()));
    }

    @Test
    void optionsDoesNotMatch() {
        assertThrows(AssertionError.class, () -> {
            MultiSelect fragment = MockFactory.multiSelect().withNumberOfOptions(1).build();
            assertThat(fragment, has(noOptions()));
        });
    }

}
