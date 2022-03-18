package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.has;
import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.textContaining;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import info.novatec.testit.webtester.pagefragments.GenericTextField;


class TextContainingMatcherTest {

    @Test
    void textContainingFragmentMatches() {
        assertThat(fieldWithText("foo bar foo"), has(textContaining("foo")));
    }

    @Test
    void textNotContainingFragmentDoesNotMatch() {
        assertThrows(AssertionError.class, () -> {
            assertThat(fieldWithText("foo foo"), has(textContaining("bar")));
        });
    }

    private GenericTextField<?> fieldWithText(String text) {
        GenericTextField<?> fragment = Mockito.mock(GenericTextField.class);
        doReturn(text).when(fragment).getText();
        return fragment;
    }

}
