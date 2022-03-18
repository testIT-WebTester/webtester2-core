package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.invisible;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import utils.unit.MockFactory;

import info.novatec.testit.webtester.pagefragments.PageFragment;


class InvisibleMatcherTest {

    @Test
    void invisibleFragmentMatches() {
        PageFragment fragment = MockFactory.fragment().invisible().build();
        assertThat(fragment, is(invisible()));
    }

    @Test
    void visibleFragmentDoesNotMatch() {
        assertThrows(AssertionError.class, () -> {
            PageFragment fragment = MockFactory.fragment().visible().build();
            assertThat(fragment, is(invisible()));
        });
    }

}
