package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.has;
import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.noSelectedOptions;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import utils.MockFactory;

import info.novatec.testit.webtester.pagefragments.MultiSelect;


public class NoSelectedOptionsMatcherTest {

    @Test
    public void noSelectionMatches() {
        MultiSelect fragment = MockFactory.multiSelect().withNumberOfSelectedOptions(0).build();
        assertThat(fragment, has(noSelectedOptions()));
    }

    @Test(expected = AssertionError.class)
    public void selectionDoesNotMatch() {
        MultiSelect fragment = MockFactory.multiSelect().withNumberOfSelectedOptions(1).build();
        assertThat(fragment, has(noSelectedOptions()));
    }

}
