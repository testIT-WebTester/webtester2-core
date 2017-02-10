package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.has;
import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.selectedOptions;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import utils.unit.MockFactory;

import info.novatec.testit.webtester.pagefragments.MultiSelect;


public class NumberOfSelectedOptionsMatcherTest {

    @Test
    public void matchingNumberOfSelectedOptionsMatches() {
        MultiSelect fragment = MockFactory.multiSelect().withNumberOfSelectedOptions(5).build();
        assertThat(fragment, has(selectedOptions(5)));
    }

    @Test(expected = AssertionError.class)
    public void mismatchingNumberOfSelectedOptionsDoesNotMatch() {
        MultiSelect fragment = MockFactory.multiSelect().withNumberOfSelectedOptions(4).build();
        assertThat(fragment, has(selectedOptions(5)));
    }

}
