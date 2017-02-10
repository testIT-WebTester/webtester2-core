package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.has;
import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.visibleText;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import utils.unit.MockFactory;

import info.novatec.testit.webtester.pagefragments.PageFragment;


class VisibleTextMatcherTest {

    @Test
    void equalTextMatches() {
        PageFragment fragment = MockFactory.fragment().withVisibleText("foo").build();
        assertThat(fragment, has(visibleText("foo")));
    }

    @Test
    void wrongTextDoesNotMatch() {
        assertThrows(AssertionError.class, () -> {
            PageFragment fragment = MockFactory.fragment().withVisibleText("foo").build();
            assertThat(fragment, has(visibleText("bar")));
        });
    }

}
