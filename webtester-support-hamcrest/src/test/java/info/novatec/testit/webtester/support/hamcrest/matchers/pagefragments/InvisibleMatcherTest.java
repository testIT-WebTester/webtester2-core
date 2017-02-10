package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.invisible;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import utils.unit.MockFactory;

import info.novatec.testit.webtester.pagefragments.PageFragment;


public class InvisibleMatcherTest {

    @Test
    public void invisibleFragmentMatches() {
        PageFragment fragment = MockFactory.fragment().invisible().build();
        assertThat(fragment, is(invisible()));
    }

    @Test(expected = AssertionError.class)
    public void visibleFragmentDoesNotMatch() {
        PageFragment fragment = MockFactory.fragment().visible().build();
        assertThat(fragment, is(invisible()));
    }

}
