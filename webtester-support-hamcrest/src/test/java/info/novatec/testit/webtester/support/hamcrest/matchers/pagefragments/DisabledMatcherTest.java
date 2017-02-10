package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.disabled;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import utils.unit.MockFactory;

import info.novatec.testit.webtester.pagefragments.PageFragment;


public class DisabledMatcherTest {

    @Test
    public void enabledFragmentMatches() {
        PageFragment fragment = MockFactory.fragment().disabled().build();
        assertThat(fragment, is(disabled()));
    }

    @Test(expected = AssertionError.class)
    public void disabledFragmentDoesNotMatch() {
        PageFragment fragment = MockFactory.fragment().enabled().build();
        assertThat(fragment, is(disabled()));
    }

}
