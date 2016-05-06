package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.attributeValue;
import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.has;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import utils.MockFactory;

import info.novatec.testit.webtester.pagefragments.PageFragment;


public class AttributeValueMatcherTest {

    @Test
    public void attributeWithValueMatches() {
        PageFragment fragment = MockFactory.fragment().withAttribute("foo", "value").build();
        assertThat(fragment, has(attributeValue("foo", "value")));
    }

    @Test(expected = AssertionError.class)
    public void nonExistentAttributeDoesNotMatch() {
        PageFragment fragment = MockFactory.fragment().withoutAttribute("foo").build();
        assertThat(fragment, has(attributeValue("foo", "value")));
    }

    @Test(expected = AssertionError.class)
    public void wrongValueDoesNotMatch() {
        PageFragment fragment = MockFactory.fragment().withAttribute("foo", "other value").build();
        assertThat(fragment, has(attributeValue("foo", "value")));
    }

}
