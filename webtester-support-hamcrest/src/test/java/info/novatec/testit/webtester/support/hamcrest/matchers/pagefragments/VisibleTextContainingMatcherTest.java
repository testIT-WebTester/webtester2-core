package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.has;
import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.visibleTextContaining;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import utils.unit.MockFactory;

import info.novatec.testit.webtester.pagefragments.PageFragment;


class VisibleTextContainingMatcherTest {

    @Test
    void textContainingFragmentMatches() {
        PageFragment fragment = MockFactory.fragment().withVisibleText("foo bar foo").build();
        assertThat(fragment, has(visibleTextContaining("foo")));
    }

    @Test
    void textNotContainingFragmentDoesNotMatch() {
        assertThrows(AssertionError.class, () -> {
            PageFragment fragment = MockFactory.fragment().withVisibleText("foo foo").build();
            assertThat(fragment, has(visibleTextContaining("bar")));
        });
    }

}
