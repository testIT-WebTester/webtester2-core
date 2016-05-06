package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.has;
import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.optionsWithTexts;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import utils.MockFactory;

import info.novatec.testit.webtester.pagefragments.MultiSelect;


public class OptionsTextsMatcherTest {

    @Test
    public void optionsInOrderMatches() {
        MultiSelect select = MockFactory.multiSelect().withOptionTexts("foo", "bar").build();
        assertThat(select, has(optionsWithTexts("foo", "bar")));
    }

    @Test(expected = AssertionError.class)
    public void optionsOutOfOrderDoesNotMatch() {
        MultiSelect select = MockFactory.multiSelect().withOptionTexts("foo", "bar").build();
        assertThat(select, has(optionsWithTexts("bar", "foo")));
    }

    @Test(expected = AssertionError.class)
    public void wrongOptionsDoesNotMatch() {
        MultiSelect select = MockFactory.multiSelect().withOptionTexts("foo").build();
        assertThat(select, has(optionsWithTexts("bar")));
    }

    @Test(expected = AssertionError.class)
    public void oneToManyOptionsDoesNotMatch() {
        MultiSelect select = MockFactory.multiSelect().withOptionTexts("foo", "bar").build();
        assertThat(select, has(optionsWithTexts("foo")));
    }

    @Test(expected = AssertionError.class)
    public void oneToFewOptionsDoesNotMatch() {
        MultiSelect select = MockFactory.multiSelect().withOptionTexts("foo").build();
        assertThat(select, has(optionsWithTexts("foo", "bar")));
    }

}
