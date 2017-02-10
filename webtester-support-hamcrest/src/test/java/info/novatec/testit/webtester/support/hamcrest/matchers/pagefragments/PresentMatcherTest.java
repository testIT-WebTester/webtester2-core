package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.present;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import utils.unit.MockFactory;

import info.novatec.testit.webtester.pagefragments.PageFragment;


public class PresentMatcherTest {

    @Test
    public void presentFragmentMatches() {
        PageFragment fragment = MockFactory.fragment().present().build();
        assertThat(fragment, is(present()));
    }

    @Test(expected = AssertionError.class)
    public void nonPresentFragmentDoesNotMatch() {
        PageFragment fragment = MockFactory.fragment().notPresent().build();
        assertThat(fragment, is(present()));
    }

}
