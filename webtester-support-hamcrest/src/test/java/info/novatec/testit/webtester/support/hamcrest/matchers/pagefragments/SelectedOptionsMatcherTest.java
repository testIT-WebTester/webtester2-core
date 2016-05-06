package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.has;
import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.selectedOptions;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import utils.MockFactory;

import info.novatec.testit.webtester.pagefragments.MultiSelect;


public class SelectedOptionsMatcherTest {

    @Test
    public void hasSelectionMatches() {
        MultiSelect fragment = MockFactory.multiSelect().withNumberOfSelectedOptions(1).build();
        assertThat(fragment, has(selectedOptions()));
    }

    @Test(expected = AssertionError.class)
    public void hasNoSelectionDoesNotMatch() {
        MultiSelect fragment = MockFactory.multiSelect().withNumberOfSelectedOptions(0).build();
        assertThat(fragment, has(selectedOptions()));
    }

}
