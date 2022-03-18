package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.has;
import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.optionsWithTexts;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import utils.unit.MockFactory;

import info.novatec.testit.webtester.pagefragments.MultiSelect;


class OptionsTextsMatcherTest {

    @Test
    void optionsInOrderMatches() {
        MultiSelect select = MockFactory.multiSelect().withOptionTexts("foo", "bar").build();
        assertThat(select, has(optionsWithTexts("foo", "bar")));
    }

    @Test
    void optionsOutOfOrderDoesNotMatch() {
        assertThrows(AssertionError.class, () -> {
            MultiSelect select = MockFactory.multiSelect().withOptionTexts("foo", "bar").build();
            assertThat(select, has(optionsWithTexts("bar", "foo")));
        });
    }

    @Test
    void wrongOptionsDoesNotMatch() {
        assertThrows(AssertionError.class, () -> {
            MultiSelect select = MockFactory.multiSelect().withOptionTexts("foo").build();
            assertThat(select, has(optionsWithTexts("bar")));
        });
    }

    @Test
    void oneToManyOptionsDoesNotMatch() {
        assertThrows(AssertionError.class, () -> {
            MultiSelect select = MockFactory.multiSelect().withOptionTexts("foo", "bar").build();
            assertThat(select, has(optionsWithTexts("foo")));
        });
    }

    @Test
    void oneToFewOptionsDoesNotMatch() {
        assertThrows(AssertionError.class, () -> {
            MultiSelect select = MockFactory.multiSelect().withOptionTexts("foo").build();
            assertThat(select, has(optionsWithTexts("foo", "bar")));
        });
    }

}
