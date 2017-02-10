package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.has;
import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.options;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import utils.unit.MockFactory;

import info.novatec.testit.webtester.pagefragments.MultiSelect;


public class OptionsMatcherTest {

    @Test
    public void hasOptionsMatches() {
        MultiSelect fragment = MockFactory.multiSelect().withNumberOfOptions(1).build();
        assertThat(fragment, has(options()));
    }

    @Test(expected = AssertionError.class)
    public void hasNoOptionsDoesNotMatch() {
        MultiSelect fragment = MockFactory.multiSelect().withNumberOfOptions(0).build();
        assertThat(fragment, has(options()));
    }

}
