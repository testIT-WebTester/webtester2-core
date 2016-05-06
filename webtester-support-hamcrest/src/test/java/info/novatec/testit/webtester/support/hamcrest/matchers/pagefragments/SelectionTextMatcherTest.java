package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.has;
import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.selectionWithText;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import utils.MockFactory;

import info.novatec.testit.webtester.pagefragments.SingleSelect;


public class SelectionTextMatcherTest {

    @Test
    public void selectionMatches() {
        SingleSelect select = MockFactory.singleSelect().withSelectedText("foo").build();
        assertThat(select, has(selectionWithText("foo")));
    }

    @Test(expected = AssertionError.class)
    public void selectionDoesNotMatch() {
        SingleSelect select = MockFactory.singleSelect().withSelectedText("foo").build();
        assertThat(select, has(selectionWithText("bar")));
    }

}
