package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.has;
import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.visibleTextContaining;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import utils.unit.MockFactory;

import info.novatec.testit.webtester.pagefragments.PageFragment;


public class VisibleTextContainingMatcherTest {

    @Test
    public void textContainingFragmentMatches() {
        PageFragment fragment = MockFactory.fragment().withVisibleText("foo bar foo").build();
        assertThat(fragment, has(visibleTextContaining("foo")));
    }

    @Test(expected = AssertionError.class)
    public void textNotContainingFragmentDoesNotMatch() {
        PageFragment fragment = MockFactory.fragment().withVisibleText("foo foo").build();
        assertThat(fragment, has(visibleTextContaining("bar")));
    }

}
