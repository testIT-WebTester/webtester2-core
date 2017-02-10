package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.selected;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import utils.unit.MockFactory;

import info.novatec.testit.webtester.pagefragments.traits.Selectable;


class SelectedMatcherTest {

    @Test
    void selectedFragmentMatches() {
        Selectable<?> fragment = MockFactory.selectable().isSelected().build();
        assertThat(fragment, is(selected()));
    }

    @Test
    void nonSelectedFragmentDoesNotMatch() {
        assertThrows(AssertionError.class, () -> {
            Selectable<?> fragment = MockFactory.selectable().isNotSelected().build();
            assertThat(fragment, is(selected()));
        });
    }

}
