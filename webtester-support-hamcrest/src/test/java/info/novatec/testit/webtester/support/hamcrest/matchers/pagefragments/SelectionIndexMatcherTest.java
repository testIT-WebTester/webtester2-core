package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.has;
import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.selectionWithIndex;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import utils.MockFactory;

import info.novatec.testit.webtester.pagefragments.SingleSelect;


public class SelectionIndexMatcherTest {

    @Test
    public void selectionMatches() {
        SingleSelect select = MockFactory.singleSelect().withSelectedIndex(1).build();
        assertThat(select, has(selectionWithIndex(1)));
    }

    @Test(expected = AssertionError.class)
    public void selectionDoesNotMatch() {
        SingleSelect select = MockFactory.singleSelect().withSelectedIndex(1).build();
        assertThat(select, has(selectionWithIndex(2)));
    }

}
