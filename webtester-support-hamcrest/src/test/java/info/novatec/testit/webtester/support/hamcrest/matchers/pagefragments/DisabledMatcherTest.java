package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.disabled;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import utils.unit.MockFactory;

import info.novatec.testit.webtester.pagefragments.PageFragment;


class DisabledMatcherTest {

    @Test
    void enabledFragmentMatches() {
        PageFragment fragment = MockFactory.fragment().disabled().build();
        assertThat(fragment, is(disabled()));
    }

    @Test
    void disabledFragmentDoesNotMatch() {
        assertThrows(AssertionError.class, () -> {
            PageFragment fragment = MockFactory.fragment().enabled().build();
            assertThat(fragment, is(disabled()));
        });
    }

}
