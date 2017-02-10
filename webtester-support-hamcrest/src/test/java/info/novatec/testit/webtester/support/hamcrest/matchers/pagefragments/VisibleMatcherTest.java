package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.visible;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import utils.unit.MockFactory;

import info.novatec.testit.webtester.pagefragments.PageFragment;


class VisibleMatcherTest {

    @Test
    void visibleFragmentMatches() {
        PageFragment fragment = MockFactory.fragment().visible().build();
        assertThat(fragment, is(visible()));
    }

    @Test
    void invisibleFragmentDoesNotMatch() {
        assertThrows(AssertionError.class, () -> {
            PageFragment fragment = MockFactory.fragment().invisible().build();
            assertThat(fragment, is(visible()));
        });
    }

}
