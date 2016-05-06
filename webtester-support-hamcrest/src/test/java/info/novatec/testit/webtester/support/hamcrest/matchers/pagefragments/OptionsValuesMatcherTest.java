package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.has;
import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.optionsWithValues;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import utils.MockFactory;

import info.novatec.testit.webtester.pagefragments.MultiSelect;


public class OptionsValuesMatcherTest {

    @Test
    public void optionsInOrderMatches() {
        MultiSelect select = MockFactory.multiSelect().withOptionValues("foo", "bar").build();
        assertThat(select, has(optionsWithValues("foo", "bar")));
    }

    @Test(expected = AssertionError.class)
    public void optionsOutOfOrderDoesNotMatch() {
        MultiSelect select = MockFactory.multiSelect().withOptionValues("foo", "bar").build();
        assertThat(select, has(optionsWithValues("bar", "foo")));
    }

    @Test(expected = AssertionError.class)
    public void wrongOptionsDoesNotMatch() {
        MultiSelect select = MockFactory.multiSelect().withOptionValues("foo").build();
        assertThat(select, has(optionsWithValues("bar")));
    }

    @Test(expected = AssertionError.class)
    public void oneToManyOptionsDoesNotMatch() {
        MultiSelect select = MockFactory.multiSelect().withOptionValues("foo", "bar").build();
        assertThat(select, has(optionsWithValues("foo")));
    }

    @Test(expected = AssertionError.class)
    public void oneToFewOptionsDoesNotMatch() {
        MultiSelect select = MockFactory.multiSelect().withOptionValues("foo").build();
        assertThat(select, has(optionsWithValues("foo", "bar")));
    }

}
