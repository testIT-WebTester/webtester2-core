package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.has;
import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.textContaining;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;

import org.junit.Test;
import org.mockito.Mockito;

import info.novatec.testit.webtester.pagefragments.GenericTextField;


public class TextContainingMatcherTest {

    @Test
    public void textContainingFragmentMatches() {
        assertThat(fieldWithText("foo bar foo"), has(textContaining("foo")));
    }

    @Test(expected = AssertionError.class)
    public void textNotContainingFragmentDoesNotMatch() {
        assertThat(fieldWithText("foo foo"), has(textContaining("bar")));
    }

    private GenericTextField<?> fieldWithText(String text) {
        GenericTextField<?> fragment = Mockito.mock(GenericTextField.class);
        doReturn(text).when(fragment).getText();
        return fragment;
    }

}
