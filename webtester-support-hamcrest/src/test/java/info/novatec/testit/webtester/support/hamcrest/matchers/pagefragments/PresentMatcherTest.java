package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.present;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import utils.unit.MockFactory;

import info.novatec.testit.webtester.pagefragments.PageFragment;


class PresentMatcherTest {

    @Test
    void presentFragmentMatches() {
        PageFragment fragment = MockFactory.fragment().present().build();
        assertThat(fragment, is(present()));
    }

    @Test
    void nonPresentFragmentDoesNotMatch() {
        assertThrows(AssertionError.class, () -> {
            PageFragment fragment = MockFactory.fragment().notPresent().build();
            assertThat(fragment, is(present()));
        });
    }

}
