package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.has;
import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.optionsWithValues;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import utils.unit.MockFactory;

import info.novatec.testit.webtester.pagefragments.MultiSelect;


class OptionsValuesMatcherTest {

    @Test
    void optionsInOrderMatches() {
        MultiSelect select = MockFactory.multiSelect().withOptionValues("foo", "bar").build();
        assertThat(select, has(optionsWithValues("foo", "bar")));
    }

    @Test
    void optionsOutOfOrderDoesNotMatch() {
        assertThrows(AssertionError.class, () -> {
            MultiSelect select = MockFactory.multiSelect().withOptionValues("foo", "bar").build();
            assertThat(select, has(optionsWithValues("bar", "foo")));
        });
    }

    @Test
    void wrongOptionsDoesNotMatch() {
        assertThrows(AssertionError.class, () -> {
            MultiSelect select = MockFactory.multiSelect().withOptionValues("foo").build();
            assertThat(select, has(optionsWithValues("bar")));
        });
    }

    @Test
    void oneToManyOptionsDoesNotMatch() {
        assertThrows(AssertionError.class, () -> {
            MultiSelect select = MockFactory.multiSelect().withOptionValues("foo", "bar").build();
            assertThat(select, has(optionsWithValues("foo")));
        });
    }

    @Test
    void oneToFewOptionsDoesNotMatch() {
        assertThrows(AssertionError.class, () -> {
            MultiSelect select = MockFactory.multiSelect().withOptionValues("foo").build();
            assertThat(select, has(optionsWithValues("foo", "bar")));
        });
    }

}
