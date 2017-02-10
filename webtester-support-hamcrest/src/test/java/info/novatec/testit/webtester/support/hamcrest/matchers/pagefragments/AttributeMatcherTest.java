package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.attribute;
import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.has;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import utils.unit.MockFactory;

import info.novatec.testit.webtester.pagefragments.PageFragment;


public class AttributeMatcherTest {

    @Test
    public void attributeExistsMatches() {
        PageFragment fragment = MockFactory.fragment().withAttribute("foo").build();
        assertThat(fragment, has(attribute("foo")));
    }

    @Test(expected = AssertionError.class)
    public void nonExistentAttributeDoesNotMatch() {
        PageFragment fragment = MockFactory.fragment().withoutAttribute("foo").build();
        assertThat(fragment, has(attribute("foo")));
    }

}
