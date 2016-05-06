package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.has;
import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.tag;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import utils.MockFactory;

import info.novatec.testit.webtester.pagefragments.PageFragment;


public class TagMatcherTest {

    @Test
    public void equalTagMatches() {
        PageFragment fragment = MockFactory.fragment().withTagName("foo").build();
        assertThat(fragment, has(tag("foo")));
    }

    @Test(expected = AssertionError.class)
    public void wrongTagDoesNotMatch() {
        PageFragment fragment = MockFactory.fragment().withTagName("foo").build();
        assertThat(fragment, has(tag("bar")));
    }

}
