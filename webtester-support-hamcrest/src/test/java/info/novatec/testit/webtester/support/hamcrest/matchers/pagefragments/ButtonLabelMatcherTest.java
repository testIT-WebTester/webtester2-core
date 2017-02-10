package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.has;
import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.label;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import info.novatec.testit.webtester.pagefragments.Button;


class ButtonLabelMatcherTest {

    @Test
    void equalLabelMatches() {
        assertThat(buttonWithLabel("foo"), has(label("foo")));
    }

    @Test
    void wrongLabelDoesNotMatch() {
        assertThrows(AssertionError.class, () -> {
            assertThat(buttonWithLabel("foo"), has(label("bar")));
        });
    }

    Button buttonWithLabel(String label) {
        Button fragment = Mockito.mock(Button.class);
        doReturn(label).when(fragment).getLabel();
        return fragment;
    }

}
