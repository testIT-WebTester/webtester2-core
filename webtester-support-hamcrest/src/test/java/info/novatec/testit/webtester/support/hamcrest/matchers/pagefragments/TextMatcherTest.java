package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.has;
import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.text;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import info.novatec.testit.webtester.pagefragments.GenericTextField;


class TextMatcherTest {

    @Test
    void equalTextMatches() {
        assertThat(fieldWithText("foo"), has(text("foo")));
    }

    @Test
    void wrongTextDoesNotMatch() {
        assertThrows(AssertionError.class, () -> {
            assertThat(fieldWithText("foo"), has(text("bar")));
        });
    }

    private GenericTextField<?> fieldWithText(String text) {
        GenericTextField<?> fragment = Mockito.mock(GenericTextField.class);
        doReturn(text).when(fragment).getText();
        return fragment;
    }

}
