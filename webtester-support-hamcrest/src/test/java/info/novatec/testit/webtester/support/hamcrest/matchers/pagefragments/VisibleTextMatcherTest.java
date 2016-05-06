package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.has;
import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.visibleText;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import utils.MockFactory;

import info.novatec.testit.webtester.pagefragments.PageFragment;


public class VisibleTextMatcherTest {

    @Test
    public void equalTextMatches() {
        PageFragment fragment = MockFactory.fragment().withVisibleText("foo").build();
        assertThat(fragment, has(visibleText("foo")));
    }

    @Test(expected = AssertionError.class)
    public void wrongTextDoesNotMatch() {
        PageFragment fragment = MockFactory.fragment().withVisibleText("foo").build();
        assertThat(fragment, has(visibleText("bar")));
    }

}
