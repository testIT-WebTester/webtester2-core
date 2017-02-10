package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.attributeValue;
import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.has;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import utils.unit.MockFactory;

import info.novatec.testit.webtester.pagefragments.PageFragment;


class AttributeValueMatcherTest {

    @Test
    void attributeWithValueMatches() {
        PageFragment fragment = MockFactory.fragment().withAttribute("foo", "value").build();
        assertThat(fragment, has(attributeValue("foo", "value")));
    }

    @Test
    void nonExistentAttributeDoesNotMatch() {
        assertThrows(AssertionError.class, () -> {
            PageFragment fragment = MockFactory.fragment().withoutAttribute("foo").build();
            assertThat(fragment, has(attributeValue("foo", "value")));
        });
    }

    @Test
    void wrongValueDoesNotMatch() {
        assertThrows(AssertionError.class, () -> {
            PageFragment fragment = MockFactory.fragment().withAttribute("foo", "other value").build();
            assertThat(fragment, has(attributeValue("foo", "value")));
        });
    }

}
