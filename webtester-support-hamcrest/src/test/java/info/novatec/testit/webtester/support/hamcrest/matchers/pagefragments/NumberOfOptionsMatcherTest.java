package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.has;
import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.options;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import utils.unit.MockFactory;

import info.novatec.testit.webtester.pagefragments.MultiSelect;


public class NumberOfOptionsMatcherTest {

    @Test
    public void matchingNumberOfOptionsMatches() {
        MultiSelect fragment = MockFactory.multiSelect().withNumberOfOptions(5).build();
        assertThat(fragment, has(options(5)));
    }

    @Test(expected = AssertionError.class)
    public void mismatchingNumberOfOptionsDoesNotMatch() {
        MultiSelect fragment = MockFactory.multiSelect().withNumberOfOptions(4).build();
        assertThat(fragment, has(options(5)));
    }

}
