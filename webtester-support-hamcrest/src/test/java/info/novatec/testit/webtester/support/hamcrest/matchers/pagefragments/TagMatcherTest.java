package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.has;
import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.tag;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import utils.unit.MockFactory;

import info.novatec.testit.webtester.pagefragments.PageFragment;


class TagMatcherTest {

    @Test
    void equalTagMatches() {
        PageFragment fragment = MockFactory.fragment().withTagName("foo").build();
        assertThat(fragment, has(tag("foo")));
    }

    @Test
    void wrongTagDoesNotMatch() {
        assertThrows(AssertionError.class, () -> {
            PageFragment fragment = MockFactory.fragment().withTagName("foo").build();
            assertThat(fragment, has(tag("bar")));
        });
    }

}
