package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.attribute;
import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.has;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import utils.unit.MockFactory;

import info.novatec.testit.webtester.pagefragments.PageFragment;


class AttributeMatcherTest {

    @Test
    void attributeExistsMatches() {
        PageFragment fragment = MockFactory.fragment().withAttribute("foo").build();
        assertThat(fragment, has(attribute("foo")));
    }

    @Test
    void nonExistentAttributeDoesNotMatch() {
        assertThrows(AssertionError.class, () -> {
            PageFragment fragment = MockFactory.fragment().withoutAttribute("foo").build();
            assertThat(fragment, has(attribute("foo")));
        });
    }

}
