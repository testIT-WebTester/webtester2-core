package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.has;
import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.selectionWithTexts;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import utils.MockFactory;

import info.novatec.testit.webtester.pagefragments.MultiSelect;


public class SelectionTextsMatcherTest {

    @Test
    public void selectionInOrderMatches() {
        MultiSelect select = MockFactory.multiSelect().withSelectedTexts("foo", "bar").build();
        assertThat(select, has(selectionWithTexts("foo", "bar")));
    }

    @Test(expected = AssertionError.class)
    public void selectionOutOfOrderDoesNotMatch() {
        MultiSelect select = MockFactory.multiSelect().withSelectedTexts("foo", "bar").build();
        assertThat(select, has(selectionWithTexts("bar", "foo")));
    }

    @Test(expected = AssertionError.class)
    public void wrongSelectionDoesNotMatch() {
        MultiSelect select = MockFactory.multiSelect().withSelectedTexts("foo").build();
        assertThat(select, has(selectionWithTexts("bar")));
    }

    @Test(expected = AssertionError.class)
    public void oneToManySelectionDoesNotMatch() {
        MultiSelect select = MockFactory.multiSelect().withSelectedTexts("foo", "bar").build();
        assertThat(select, has(selectionWithTexts("foo")));
    }

    @Test(expected = AssertionError.class)
    public void oneToFewSelectionDoesNotMatch() {
        MultiSelect select = MockFactory.multiSelect().withSelectedTexts("foo").build();
        assertThat(select, has(selectionWithTexts("foo", "bar")));
    }

}
