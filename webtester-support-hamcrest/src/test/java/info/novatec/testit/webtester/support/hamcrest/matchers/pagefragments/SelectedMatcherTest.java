package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.selected;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import utils.unit.MockFactory;

import info.novatec.testit.webtester.pagefragments.traits.Selectable;


public class SelectedMatcherTest {

    @Test
    public void selectedFragmentMatches() {
        Selectable<?> fragment = MockFactory.selectable().isSelected().build();
        assertThat(fragment, is(selected()));
    }

    @Test(expected = AssertionError.class)
    public void nonSelectedFragmentDoesNotMatch() {
        Selectable<?> fragment = MockFactory.selectable().isNotSelected().build();
        assertThat(fragment, is(selected()));
    }

}
