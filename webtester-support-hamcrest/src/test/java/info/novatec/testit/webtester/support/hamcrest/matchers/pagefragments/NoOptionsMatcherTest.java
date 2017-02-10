package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.has;
import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.noOptions;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import utils.unit.MockFactory;

import info.novatec.testit.webtester.pagefragments.MultiSelect;


public class NoOptionsMatcherTest {

    @Test
    public void noOptionsMatches() {
        MultiSelect fragment = MockFactory.multiSelect().withNumberOfOptions(0).build();
        assertThat(fragment, has(noOptions()));
    }

    @Test(expected = AssertionError.class)
    public void optionsDoesNotMatch() {
        MultiSelect fragment = MockFactory.multiSelect().withNumberOfOptions(1).build();
        assertThat(fragment, has(noOptions()));
    }

}
