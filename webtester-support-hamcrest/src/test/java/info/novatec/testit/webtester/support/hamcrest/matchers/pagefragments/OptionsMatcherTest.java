package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.has;
import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.options;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import utils.unit.MockFactory;

import info.novatec.testit.webtester.pagefragments.MultiSelect;


class OptionsMatcherTest {

    @Test
    void hasOptionsMatches() {
        MultiSelect fragment = MockFactory.multiSelect().withNumberOfOptions(1).build();
        assertThat(fragment, has(options()));
    }

    @Test
    void hasNoOptionsDoesNotMatch() {
        assertThrows(AssertionError.class, () -> {
            MultiSelect fragment = MockFactory.multiSelect().withNumberOfOptions(0).build();
            assertThat(fragment, has(options()));
        });
    }

}
