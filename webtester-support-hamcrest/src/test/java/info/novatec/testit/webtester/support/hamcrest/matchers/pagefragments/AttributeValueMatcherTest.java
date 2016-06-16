package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.attributeValue;
import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.has;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import utils.MockFactory;

import info.novatec.testit.webtester.pagefragments.PageFragment;


public class AttributeValueMatcherTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void attributeWithValueMatches() {
        PageFragment fragment = MockFactory.fragment().withAttribute("foo", "value").build();
        assertThat(fragment, has(attributeValue("foo", "value")));
    }

    @Test
    public void nonExistentAttributeDoesNotMatch() {

        exception.expect(AssertionError.class);
        exception.expectMessage(containsString("attribute was not present"));

        PageFragment fragment = MockFactory.fragment().withoutAttribute("foo").build();
        assertThat(fragment, has(attributeValue("foo", "value")));

    }

    @Test
    public void wrongValueDoesNotMatch() {

        exception.expect(AssertionError.class);
        exception.expectMessage(containsString("value was <other value>"));

        PageFragment fragment = MockFactory.fragment().withAttribute("foo", "other value").build();
        assertThat(fragment, has(attributeValue("foo", "value")));

    }

}
