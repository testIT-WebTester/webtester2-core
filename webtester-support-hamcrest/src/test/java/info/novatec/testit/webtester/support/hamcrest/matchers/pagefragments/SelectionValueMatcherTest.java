package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.has;
import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.selectionWithValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import utils.MockFactory;

import info.novatec.testit.webtester.pagefragments.SingleSelect;


public class SelectionValueMatcherTest {

    @Test
    public void selectionMatches() {
        SingleSelect select = MockFactory.singleSelect().withSelectedValue("foo").build();
        assertThat(select, has(selectionWithValue("foo")));
    }

    @Test(expected = AssertionError.class)
    public void selectionDoesNotMatch() {
        SingleSelect select = MockFactory.singleSelect().withSelectedValue("foo").build();
        assertThat(select, has(selectionWithValue("bar")));
    }

}
