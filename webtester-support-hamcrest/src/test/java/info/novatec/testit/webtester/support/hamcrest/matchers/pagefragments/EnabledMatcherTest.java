package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.enabled;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import utils.unit.MockFactory;

import info.novatec.testit.webtester.pagefragments.PageFragment;


class EnabledMatcherTest {

    @Test
    void enabledFragmentMatches() {
        PageFragment fragment = MockFactory.fragment().enabled().build();
        assertThat(fragment, is(enabled()));
    }

    @Test
    void disabledFragmentDoesNotMatch() {
        assertThrows(AssertionError.class, () -> {
            PageFragment fragment = MockFactory.fragment().disabled().build();
            assertThat(fragment, is(enabled()));
        });
    }

}
