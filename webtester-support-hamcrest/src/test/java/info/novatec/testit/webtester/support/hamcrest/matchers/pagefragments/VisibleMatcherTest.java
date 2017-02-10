package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.visible;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import utils.unit.MockFactory;

import info.novatec.testit.webtester.pagefragments.PageFragment;


public class VisibleMatcherTest {

    @Test
    public void visibleFragmentMatches() {
        PageFragment fragment = MockFactory.fragment().visible().build();
        assertThat(fragment, is(visible()));
    }

    @Test(expected = AssertionError.class)
    public void invisibleFragmentDoesNotMatch() {
        PageFragment fragment = MockFactory.fragment().invisible().build();
        assertThat(fragment, is(visible()));
    }

}
